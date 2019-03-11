package com.cdtu.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sun.misc.BASE64Encoder;

public class DownloadFile {
	public static void downloadFile(File file, String fileName, HttpServletResponse response,
			HttpServletRequest request) {
		// response.setContentType("application/octet-stream");
		// 获得请求头中的User-Agent
		String agent = request.getHeader("User-Agent");
		// 根据不同浏览器进行不同的编码
		String fileNameEncoder = "";
		try {
			if (agent.contains("MSIE")) {
				// IE浏览器
				fileNameEncoder = URLEncoder.encode(fileName, "utf-8");
				fileNameEncoder = fileNameEncoder.replace("+", " ");
			} else if (agent.contains("Firefox")) {
				// 火狐浏览器
				BASE64Encoder base64Encoder = new BASE64Encoder();
				fileNameEncoder = "=?utf-8?B?" + base64Encoder.encode(fileName.getBytes("utf-8")) + "?=";
			} else
				// 其它浏览器
				fileNameEncoder = URLEncoder.encode(fileName, "utf-8");

			// 以流的形式下载文件。
			BufferedInputStream fis = new BufferedInputStream(new FileInputStream(file.getPath()));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			// 清空response
			response.reset();
			OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
			response.setContentType("application/octet-stream");
			// 告诉浏览器不解析文件
			response.setHeader("Content-Disposition", "attachment;filename=" + fileNameEncoder);
			toClient.write(buffer);
			toClient.flush();
			toClient.close();

		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

}
