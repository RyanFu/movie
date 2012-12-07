package com.wuxianmeihao.controller;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.wuxianmeihao.utils.FileConfig;
import com.wuxianmeihao.utils.FileUtil;
import com.wuxianmeihao.utils.PictureUtil;
import com.wuxianmeihao.utils.StringUtil;

@Controller
// ��������Ϊ��������
@RequestMapping(value = "/upload")
public class FileUploadController extends MultiActionController {

	
	/**
	 * 
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("uploadType",request.getAttribute("uploadType"));
		return "upload";
	}

	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	// ���ļ��ϴ�����ӳ�䵽�÷���
	public String handleFormUpload(
			HttpServletRequest request,
			HttpServletResponse response,
		    Model model) { // �������һ��Ҫ��form�еĲ�������Ӧ
		
		String uploadType = "pic";
		String fileObject = "banner_File";
	    MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

	    CommonsMultipartFile mFile = (CommonsMultipartFile) multipartRequest.getFile(fileObject);
	    model.addAttribute("uploadType", uploadType);
	    model.addAttribute("feedback", multipartRequest.getParameter("feedback"));
	    
		// ���ϴ������г�������ʱ���ؿͻ��˵���Ϣ
		String message = "";

		if (!((mFile == null) || mFile.isEmpty())) {
		    String fileName = mFile.getFileItem().getName();

            // ����ļ�����
            message = checkType(fileName, uploadType);
            if (StringUtil.isNotEmpty(message)) {
                return uploadAssert(model, message);
            }

            //String localPath = fileConfig.getFilePath();//���ر���·��
            String localPath = this.getClass().getClassLoader().getResource("/").getPath()+"../../"+"uploadFloder";
            System.out.println(localPath);

            // �����ļ���ͬʱ���������ڵ�Ŀ¼
            String fileRealName = getRealFileName(fileName, localPath);

            // �����ļ�
            File file = new File(localPath + "/" + fileRealName);

            try {
                // ���ϴ����ļ�д���½����ļ���
                mFile.getFileItem().write(file);
                
                // ���ͼƬ�ļ��ĸ߶ȺͿ��
//                message = this.checkPicSize(uploadType, file, width, height);
                
               
            } catch (Exception e) {
                e.printStackTrace();
                message = "�ڷ������ϴ����ļ�ʧ��";
                return uploadAssert(model, message);
            }
            String url = ("" + fileRealName);
            model.addAttribute("url", url.replace("\\\\", "/"));
        } else {
            message = "�ļ�����Ϊ��";
        }
        return uploadAssert(model, message);
    }

    /**
     * ���ļ��������޸�
     * 
     * @param fileName
     * @return
     */
    private String getRealFileName(String fileName, String dir) {
        String[] fileNameArray = fileName.split("\\.");
        if (fileNameArray.length < 2) {
            return null;
        }
        return FileUtil.generateFileName(
                "."+fileNameArray[fileNameArray.length - 1],
                "/", dir, dir);
    }

    /**
     * ����ϴ��ļ�����
     * 
     * @param fileName
     * @param type
     * @return
     */
    private String checkType(String fileName, String type) {
        String[] fileNameArray = fileName.split("\\.");
        if (fileNameArray.length < 2) {
            return "�ϴ��ļ����ʹ���������ѡ���ļ��ϴ�";
        }
        String fileExt = "."+fileNameArray[fileNameArray.length-1];
        if (type.equals("pic")) {
            try {
                if (!FileUtil.checkPicType(fileExt)) {
                    return "�ϴ��ļ�����ͼƬ���ͣ�������ѡ���ļ��ϴ�!";
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else if (type.equals("apk")) {
            if (!FileUtil.checkPicType(fileExt, new String[] { ".apk" })) {
                return "�ϴ��ļ�����apk���ͣ�������ѡ���ļ��ϴ�!";
            }
        }
        return "";
    }

    /**
     * ������תҳ��
     * 
     * @param model
     * @param message
     * @return
     */
    private String uploadAssert(Model model, String message) {
        if (StringUtils.isNotEmpty(message)) {
            model.addAttribute("message", message);
            model.addAttribute("submitFlag", "false");
        }else{
            model.addAttribute("message", "�ϴ��ɹ�!");
            model.addAttribute("submitFlag", "true");
        }
        return "/ad/frame";
    }

    /**
     * ���ͼƬ�ĸߺͿ�,���������Ҫ���򽫸�ͼƬ���ô�С
     * 
     * @param type
     * @param file
     * @param width
     * @param height
     * @throws IOException
     */
    private String checkPicSize(String type, File file, int width, int height)
            throws IOException {
        if (type.equals("pic")) {
            BufferedImage bi = javax.imageio.ImageIO.read(file);
            if (width != bi.getWidth() && height != bi.getHeight()) {
                if(!file.getName().endsWith(".gif")){
                    PictureUtil.resizePicture(file.getPath().replace("\\", "\\\\"),
                            file.getPath().replace("\\", "\\\\"), width, height,
                            true);
                }
                return "ͼƬ�Ŀ�Ȼ�߶Ȳ�����Ҫ��";
            }
        }
        return "";
    }
}
