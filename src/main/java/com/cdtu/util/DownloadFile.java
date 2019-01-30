package com.cdtu.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

public class DownloadFile {
	public static void downloadFile(File file,String fileName,HttpServletResponse response){  
	       response.setCharacterEncoding("utf-8");  
	      // response.setContentType("application/octet-stream");  
	 
	       try {
	           // 以流的形式下载文件。
	           BufferedInputStream fis = new BufferedInputStream(new FileInputStream(file.getPath()));
	           byte[] buffer = new byte[fis.available()];
	           fis.read(buffer);
	           fis.close();
	           // 清空response
	           response.reset();
	           OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
	           response.setContentType("application/octet-stream");
	           response.setHeader("Content-Disposition","attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
	           toClient.write(buffer);
	           toClient.flush();
	           toClient.close();
	     
	        } 
	        catch (IOException ex) {
	           ex.printStackTrace();
	       }
	   }

}
