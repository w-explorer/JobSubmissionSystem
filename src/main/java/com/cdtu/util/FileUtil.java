package com.cdtu.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class FileUtil {
	/**
	 * 获取Tomcat的ROOT目录的绝对路径，将上传的文件都保存在ROOT目录下的uploadFile文件夹内
	 *
	 * @author 李红兵
	 */
	public static String getAbsolutePath(HttpServletRequest request) {
		String absolutePath = (String) request.getSession().getServletContext().getAttribute("path");// ROOT绝对路径
		absolutePath = absolutePath.substring(absolutePath.indexOf(':') + 1).replace('\\', '/');// Windows去掉盘符，替换\
		return absolutePath;
	}

	/**
	 * 获取文件类型
	 *
	 * @author 李红兵
	 */
	public static String getFileType(File file) {
		String fileName = file.getName();
		int suffixPos = fileName.lastIndexOf('.');
		return suffixPos == -1 ? "file" : fileName.substring(suffixPos + 1);
	}

	/**
	 * 获取文件是否能够在线阅读
	 *
	 * @author 李红兵
	 */
	public static boolean canOnlineRead(File file) {
		String[] onlineReadTypes = { "jpg", "png", "gif", "psd", "webp", "txt", "doc", "docx", "XLS", "XLSX", "ppt",
				"pptx", "pdf" };
		return Arrays.asList(onlineReadTypes).contains(getFileType(file));
	}

	/**
	 * 上传文件，将上传的文件转换到新创建的文件，并写入磁盘
	 *
	 * @param upFile  上传的文件
	 * @param newFile 新创建的文件（内存对象）
	 * @param request Http请求
	 * @return true 文件不存在，上传文件成功
	 * @return false 文件已存在
	 * @author 李红兵
	 */
	public static boolean uploadFile(MultipartFile upFile, File newFile, HttpServletRequest request) throws Exception {
		if (!newFile.exists()) {
			newFile.mkdirs();
			upFile.transferTo(newFile);
			return true;
		}
		return false;
	}

	/**
	 * 下载文件
	 *
	 * @param file     读取到内存中的磁盘文件
	 * @param response Http响应
	 * @author 李红兵
	 */
	public static boolean downloadFile(File file, HttpServletResponse response) throws Exception {
		String fileName = URLEncoder.encode(file.getName(), "UTF-8");
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Length", "" + file.length());
		response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
		if (file.exists()) {
			BufferedInputStream inStream = new BufferedInputStream(new FileInputStream(file));
			BufferedOutputStream outStream = new BufferedOutputStream(response.getOutputStream());
			byte[] buffer = new byte[1024 * 1024 * 50];// 50兆缓冲区，防止内存溢出
			for (int currBytes = inStream.read(buffer); currBytes != -1; currBytes = inStream.read(buffer)) {
				outStream.write(buffer, 0, currBytes);
				outStream.flush();
			}
			inStream.close();
			outStream.close();
			return true;
		}
		return false;
	}

	/**
	 * 删除文件
	 *
	 * @param file 要删除的文件（内存对象）
	 * @return true 文件存在且删除成功
	 * @return false 文件不存在，删除失败
	 * @author 李红兵
	 */
	public static boolean deleteFile(File file) throws Exception {
		return file.delete();
	}

	/**
	 * 文件上传
	 *
	 * @author LR
	 * @param file
	 * @param path
	 * @param id
	 * @return
	 * @throws IOException
	 */
	public static String updateFile(CommonsMultipartFile file, String path, String id, String pwId) throws IOException {
		if (path == null) {
			return addMutiparFile(file, id, pwId);
		} else {
			File newfile = new File(path);
			newfile.delete();
			return addMutiparFile(file, id, pwId);

		}
	}

	/**
	 * 分文件类型上传
	 *
	 * @author LR
	 * @param file
	 * @param id
	 * @return
	 * @throws IOException
	 */
	public static String addMutiparFile(CommonsMultipartFile file, String id, String pwId) throws IOException {
		if (file.isEmpty()) {
			return null;
		} else {
			String fileName = file.getOriginalFilename();
			String type = fileName.substring(fileName.lastIndexOf(".") + 1);
			String realPath = "D:" + File.separator + "uploadFile" + File.separator + pwId + File.separator + id
					+ File.separator;
			if (type.equals("png") || type.equals("jpg") || type.equals("gif") || type.equals("jpeg")) {
				String path = realPath + "image" + File.separator + fileName;
				file.transferTo(createFile(path));
				path = File.separator + "workfile" + File.separator + pwId + File.separator + id + File.separator
						+ "image" + File.separator + fileName;
				return path;
			}
			if (type.equals("rar") || type.equals("zip") || type.equals("7z")) {
				String path = realPath + "package" + File.separator + fileName;
				file.transferTo(createFile(path));
				path = File.separator + "workfile" + File.separator + pwId + File.separator + id + File.separator
						+ "package" + File.separator + fileName;
				return path;

			}
			if (type.equals("doc") || type.equals("docx") || type.equals("txt")) {
				String path = realPath + "file" + File.separator + fileName;
				file.transferTo(createFile(path));
				path = File.separator + "workfile" + File.separator + pwId + File.separator + id + File.separator
						+ "file" + File.separator + fileName;
				return path;
			}
			return "-1";

		}

	}

	/**
	 * 文件夹创建
	 *
	 * @author LR
	 * @param realPath
	 * @param fileName
	 * @return
	 */
	public static File createFile(String path) throws IOException {
		File newfile = new File(path);
		if (!newfile.exists()) {
			newfile.getParentFile().mkdirs();
		}
		return newfile;

	}

	public static String updateFile(CommonsMultipartFile file) {
		return null;
	}
}
