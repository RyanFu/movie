package com.wuxianmeihao.controller;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.umeng.core.utils.Page;
import com.umeng.core.utils.StringUtil;
import com.wuxianmeihao.core.domain.Movie;
import com.wuxianmeihao.core.domain.MovieGroup;
import com.wuxianmeihao.service.MovieGroupService;

@Controller
@RequestMapping(value = "/movieGroup")
public class MovieGroupController extends MultiActionController {
    
    @Resource
    private MovieGroupService movieGroupService;
    
    private List<String> list = new ArrayList<String>();
    
    @RequestMapping(value = "/index")
    @ResponseBody
    public Object index(HttpServletRequest request, HttpServletResponse response, Model model) {
    	Map<String,Object> queryParma = new HashMap<String,Object>();
    	String pageNo = request.getParameter("pageNo");
    	if(pageNo!=null){
    		queryParma.put("pageNo", pageNo);
    	}
    	
		try {
			String category = request.getParameter("category");
	    	if(!StringUtil.isEmpty(category)){
	    		queryParma.put("category", new String(category.getBytes("ISO-8859-1"),"gbk"));
	    	}
			String	name = request.getParameter("name");
			if(!StringUtil.isEmpty(name)){
	    		queryParma.put("name","%" +  new String(name.getBytes("ISO-8859-1"),"gbk") + "%");
	    	}
		} catch (UnsupportedEncodingException e) {
			System.out.println("ת������");
		}
    	
    	String releaseTime = request.getParameter("releaseTime");
    	if(releaseTime!=null){
    		queryParma.put("releaseTime","%" + releaseTime + "%");
    	}
    	
    	
    	Page<Map<String, Object>> page = new Page<Map<String,Object>>();
    	page.setParam(queryParma);
    	page.setPageSize(10);
    	page.setPageNo(Integer.parseInt(queryParma.get("pageNo").toString()));
    	movieGroupService.getMovieGroupListByPage(page);
    	return page;
    }  
    
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object getMovieList(HttpServletRequest request, HttpServletResponse response, Model model) {
    	try{
    		int movieGroupId = Integer.parseInt(request.getParameter("id"));
    		Map<String,Object> params = new HashMap<String,Object>();
    		params.put("id", movieGroupId);
    		List<Map<String,Object>> movieList = movieGroupService.getMovieListe(params);
    		return movieList;
    	}catch(Exception e){
    		e.printStackTrace();
    		return "";
    	}
    }
        
    
    @RequestMapping(value = "/save")
    @ResponseBody
    public String save(HttpServletRequest request, HttpServletResponse response, Model model) {
    	getResource("http://www.bdzy.cc/list/?0.html",1);
    	for(int i = 2;i<350;i++){
    		if(getResource("http://www.bdzy.cc/list/?0-"+i+".html",i)){
    			break;
    		}
    	}
    	for(String info :list){
    		System.out.println(info);
    	}
    	return "sucess";
    }
    
    private boolean getResource(String listUrl,int i){
         int count = 0;
         String info = "";
         String detailInfo = "";
     	 try {
// 			URL uri = new URL("http://www.bdzy.cc/");
     		URL uri = new URL(listUrl);
 	        BufferedReader x = new BufferedReader(new InputStreamReader(uri.openStream(),"gbk"));
 	        String s = "";
 	        StringBuffer sb = new StringBuffer();
 	        while((s = x.readLine()) != null){
 	        	sb.append(s);
 	        }
 	        if(!sb.toString().split("ҳ��:")[1].startsWith(""+i+"/")){
 	        	return true;
 	        }
 	        String[] movieUrlArray = sb.toString().split("<a href=\"/");
 	        List<String> movieUrlList = new ArrayList<String>();
 	        for(String movieUrl : movieUrlArray){
 	        	if(movieUrl.startsWith("detail") && movieUrl.contains("�������")){
 	        		movieUrlList.add(movieUrl.substring(0, movieUrl.indexOf("\"")));
 	        	}
 	        }
 	        
 	        for(String detailUrl:movieUrlList ){
 	        	try{
		 	        URL url = new URL("http://www.bdzy.cc/"+detailUrl);
		 	        
		 	        x = new BufferedReader(new InputStreamReader(url.openStream(),"gbk"));
		 	        StringBuffer sbDetail = new StringBuffer();
		 	        while((s = x.readLine()) != null){
		 	        	sbDetail.append(s);
		 	        }
		 	        String fullHtml = sbDetail.toString();
		 	        
		 	        
		 	        MovieGroup mg = new MovieGroup();
		 	        
		 	        System.out.println(fullHtml);
		 	        
			        mg.setPicUrl(getLastUrlValue(fullHtml,"<!--ӰƬͼƬ��ʼ����-->","<!--ӰƬͼƬ��������-->"));
			        
			        mg.setName(getLastValueWithMarks(fullHtml,"<!--ӰƬ���ƿ�ʼ����-->","<!--ӰƬ���ƽ�������-->"));
			        mg.setActorList(getLastValueWithMarks(fullHtml,"<!--ӰƬ��Ա��ʼ����-->","<!--ӰƬ��Ա��������-->"));
			        mg.setDirector(getLastValueWithMarks(fullHtml,"<!--ӰƬ���ݿ�ʼ-->","<!--ӰƬ���ݽ���-->"));
			        mg.setRemark(getLastValueWithMarks(fullHtml,"<!--ӰƬ��ע��ʼ����-->","<!--ӰƬ��ע��������-->"));
			        mg.setCategory(getLastValueWithMarks(fullHtml,"<!--ӰƬ���Ϳ�ʼ����-->","<!--ӰƬ���ͽ�������-->"));
			        mg.setArea(getLastValueWithMarks(fullHtml,"<!--ӰƬ������ʼ����-->","<!--ӰƬ������������-->"));
			        mg.setLastUpdateTime(getLastValueWithMarks(fullHtml,"<!--ӰƬ����ʱ�俪ʼ����-->","<!--ӰƬ����ʱ���������-->"));
			        mg.setStatus(getLastValueWithMarks(fullHtml,"<!--ӰƬ״̬��ʼ����-->","<!--ӰƬ״̬��������-->"));
			        mg.setLanguage(getLastValueWithMarks(fullHtml,"<!--ӰƬ���Կ�ʼ����-->","<!--ӰƬ���Խ�������-->"));
			        mg.setReleaseTime(getLastValueWithMarks(fullHtml,"<!--��ӳ���ڿ�ʼ����-->","<!--��ӳ���ڽ�������-->"));
			        
			        mg.setDescription(getLastValueWithMarks(fullHtml,"<!--ӰƬ���ܿ�ʼ����-->","<!--ӰƬ���ܽ�������-->"));
			        
			        String moviesHtml = getLastValueWithOutMarks(fullHtml,"<!--�����б�ʼ����-->","<!--�����б��������-->");
		 	        String[] moviesArray = moviesHtml.split("value=");
		 	        Movie movie;
		 	        List<Movie> movieList = new ArrayList<Movie>();
		 	        for(String strMovie : moviesArray){
		 	        	if(strMovie.contains("bdhd:")){
		 	        		strMovie = strMovie.replaceAll("'", "").replaceAll("\"", "").replaceAll("\\[", "|");
		 		        	movie = new Movie();
		 		        	movie.setUrl(strMovie.substring(0, strMovie.indexOf("checked")).trim());
		 		        	movie.setName(movie.getUrl().substring(movie.getUrl().lastIndexOf("|"), movie.getUrl().lastIndexOf(".")).replace("|", ""));
		 		        	movieList.add(movie);
		 	        	}
		 	        }
		 	       detailInfo = mg.getUrl();
		 	        System.out.println("-----start--------"+mg.getName());
		 	        movieGroupService.saveOrUpdate(mg, movieList);
		 	        System.out.println("-----end--------"+mg.getName());
		 	        count ++;
		 	        if(count%100 == 0 && count/100 > 0){
		 	        	System.out.println("�Ѿ�������"+count+"����Ӱ");
		 	        }
 	        	}catch (Exception e) {
 	        		list.add(detailInfo);
 	        		continue;
					// TODO: handle exception
				}
 	        }
 	        
 		} catch (Exception e) {
 			// TODO Auto-generated catch block
 			list.add(info);
 		}  
 		return false;
    }
    
    private String getLastUrlValue(String fullHtml,String start,String end){
		return fullHtml.substring(fullHtml.indexOf(start), fullHtml.indexOf(end)).replaceAll(start, "");
	}


	private String getLastValueWithMarks(String fullHtml,String start,String end){
		return fullHtml.substring(fullHtml.indexOf(start), fullHtml.indexOf(end))
		.replaceAll(start, "")
		.replaceAll("\"", "")
		.replaceAll("<div class=intro>", "")
		.replaceAll("</div>", "");
		
	}
	
	private String getLastValueWithOutMarks(String fullHtml,String start,String end){
		return fullHtml.substring(fullHtml.indexOf(start), fullHtml.indexOf(end))
		.replaceAll(start, "");
		
		
	}
}
