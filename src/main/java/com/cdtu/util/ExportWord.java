package com.cdtu.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import com.auth0.jwt.internal.com.fasterxml.jackson.core.Version;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class ExportWord {
	   public static void createWord(Map<String,String> dataMap, String moban,  
	            String filePath) {  
		   
	        try {
	            //编号
	           
	            //Configuration 用于读取ftl文件
	            @SuppressWarnings("deprecation")
	            Configuration configuration = new Configuration();
	            configuration.setDefaultEncoding("utf-8");
	            /**
	             * 以下是两种指定ftl文件所在目录路径的方式，注意这两种方式都是
	             * 指定ftl文件所在目录的路径，而不是ftl文件的路径
	             */
	            //指定路径的第一种方式（根据某个类的相对路径指定）
//	                configuration.setClassForTemplateLoading(this.getClass(), "");
	            //指定路径的第二种方式，我的路径是C：/a.ftl
	            configuration.setDirectoryForTemplateLoading(new File(moban));
	            //输出文档路径及名称
	            File outFile = new File(filePath);
	            //以utf-8的编码读取ftl文件
	            Template template = configuration.getTemplate("moban.ftl", "utf-8");
	            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8"), 10240);
	            template.process(dataMap, out);
	            out.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	  
	   }    
	      
}
