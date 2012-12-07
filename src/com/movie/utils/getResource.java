package com.movie.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.wuxianmeihao.core.domain.Movie;
import com.wuxianmeihao.core.domain.MovieGroup;



public class getResource {
	
	public static void main(String[] args){
		try {
			URL uri = new URL("http://www.bdzy.cc/list/?0-2.html");
	        BufferedReader x = new BufferedReader(new InputStreamReader(uri.openStream(),"gbk"));
	        String s = "";
	        StringBuffer sb = new StringBuffer();
	        while((s = x.readLine()) != null){
	        	sb.append(s);
	        }
	        System.out.println(sb.toString());
	        System.out.println(sb.toString().split("ҳ��:")[1]);
//	        String[] movieUrlArray = sb.toString().split("<a href=\"/");
//	        List<String> movieUrlList = new ArrayList<String>();
//	        int m = 0;
//	        for(String movieUrl : movieUrlArray){
//	        	if(movieUrl.startsWith("detail") && movieUrl.contains("�������")){
//	        		movieUrlList.add(movieUrl.substring(0, movieUrl.indexOf("\"")));
//	        		m++;
//	        	}
//	        }
//	        
//	        URL url = new URL("http://www.bdzy.cc/detail/?16546.html");
//	        x = new BufferedReader(new InputStreamReader(url.openStream(),"gbk"));
//	        StringBuffer sbDetail = new StringBuffer();
//	        while((s = x.readLine()) != null){
//	        	sbDetail.append(s);
//	        }
//	        String fullHtml = sbDetail.toString();
//	        System.out.println(fullHtml);
//	        
//	        MovieGroup mg = new MovieGroup();
//	        
//	        mg.setPicUrl(getLastUrlValue(fullHtml,"<!--ӰƬͼƬ��ʼ����-->","<!--ӰƬͼƬ��������-->"));
//	        
//	        mg.setName(getLastValueWithMarks(fullHtml,"<!--ӰƬ���ƿ�ʼ����-->","<!--ӰƬ���ƽ�������-->"));
//	        mg.setActorList(getLastValueWithMarks(fullHtml,"<!--ӰƬ��Ա��ʼ����-->","<!--ӰƬ��Ա��������-->"));
//	        mg.setDirector(getLastValueWithMarks(fullHtml,"<!--ӰƬ���ݿ�ʼ-->","<!--ӰƬ���ݽ���-->"));
//	        mg.setRemark(getLastValueWithMarks(fullHtml,"<!--ӰƬ��ע��ʼ����-->","<!--ӰƬ��ע��������-->"));
//	        mg.setCategory(getLastValueWithMarks(fullHtml,"<!--ӰƬ���Ϳ�ʼ����-->","<!--ӰƬ���ͽ�������-->"));
//	        mg.setArea(getLastValueWithMarks(fullHtml,"<!--ӰƬ������ʼ����-->","<!--ӰƬ������������-->"));
//	        mg.setLastUpdateTime(getLastValueWithMarks(fullHtml,"<!--ӰƬ����ʱ�俪ʼ����-->","<!--ӰƬ����ʱ���������-->"));
//	        mg.setStatus(getLastValueWithMarks(fullHtml,"<!--ӰƬ״̬��ʼ����-->","<!--ӰƬ״̬��������-->"));
//	        mg.setLanguage(getLastValueWithMarks(fullHtml,"<!--ӰƬ���Կ�ʼ����-->","<!--ӰƬ���Խ�������-->"));
//	        mg.setReleaseTime(getLastValueWithMarks(fullHtml,"<!--��ӳ���ڿ�ʼ����-->","<!--��ӳ���ڽ�������-->"));
//	        
//	        mg.setDescription(getLastValueWithMarks(fullHtml,"<!--ӰƬ���ܿ�ʼ����-->","<!--ӰƬ���ܽ�������-->"));
//	        
//	        String moviesHtml = getLastValueWithOutMarks(fullHtml,"<!--�����б�ʼ����-->","<!--�����б��������-->");
////	        System.out.println(moviesHtml);
//	        String[] moviesArray = moviesHtml.split("value=");
//	        Movie movie;
//	        
//	        for(String strMovie : moviesArray){
//	        	if(strMovie.contains("bdhd:")){
//	        		
//	        		strMovie = strMovie.replaceAll("'", "").replaceAll("\"", "").replaceAll("\\[", "|");
//		        	movie = new Movie();
//		        	movie.setUrl(strMovie.substring(0, strMovie.indexOf("checked")).trim());
//		        	movie.setName(movie.getUrl().substring(movie.getUrl().lastIndexOf("|"), movie.getUrl().lastIndexOf(".")).replace("|", ""));
//		        	System.out.println(movie.getName());
//		        	System.out.println(movie.getUrl());
//	        	}
//	        }
//	        
//	        System.out.println(mg.getPicUrl());
//	        System.out.println(mg.getName());
//	        System.out.println(mg.getActorList());
//	        System.out.println(mg.getDirector());
//	        System.out.println(mg.getRemark());
//	        System.out.println(mg.getCategory());
//	        System.out.println(mg.getArea());
//	        System.out.println(mg.getLastUpdateTime());
//	        System.out.println(mg.getStatus());
//	        System.out.println(mg.getLanguage());
//	        System.out.println(mg.getReleaseTime());
//	        System.out.println(mg.getDescription());
	        
	        
	        
		} catch (Exception e) {
			System.out.print("-----------error");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
         
	}
	
	public static String getLastUrlValue(String fullHtml,String start,String end){
		return fullHtml.substring(fullHtml.indexOf(start), fullHtml.indexOf(end)).replaceAll(start, "");
//		return with_prifex.substring(with_prifex.indexOf("http"));
	}
	
	public static String getLastValueWithMarks(String fullHtml,String start,String end){
		return fullHtml.substring(fullHtml.indexOf(start), fullHtml.indexOf(end))
		.replaceAll(start, "")
		.replaceAll("\"", "")
		.replaceAll("<div class=intro>", "")
		.replaceAll("</div>", "");
		
	}
	
	public static String getLastValueWithOutMarks(String fullHtml,String start,String end){
		return fullHtml.substring(fullHtml.indexOf(start), fullHtml.indexOf(end))
		.replaceAll(start, "");
		
		
	}
}
