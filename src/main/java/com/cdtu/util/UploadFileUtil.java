package com.cdtu.util;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class UploadFileUtil {
	/**文件上传
	 * @author LR
	 * @param file
	 * @param path
	 * @param id
	 * @return
	 * @throws IOException
	 */
	public static String updateFile(CommonsMultipartFile file,String path,String id,String pwId)throws IOException{
		if(path==null){
			return addMutiparFile(file,id,pwId);
		}else{
			File newfile = new File(path);
			newfile.delete();
			return addMutiparFile(file,id,pwId);

		}		
	}
	/**
	 * 分文件类型上传
	 * @author LR
	 * @param file
	 * @param id
	 * @return
	 * @throws IOException
	 */
	public static String addMutiparFile(CommonsMultipartFile file,String id,String pwId) throws IOException {
		if (file.isEmpty()) {
			return null;
		} else {
			String fileName = file.getOriginalFilename();
	        String type = fileName.substring(fileName.lastIndexOf(".") + 1);
			String realPath="F:/uploadFile/"+pwId+"/"+id+"/";
			if(type.equals("png")||type.equals("jpg")||type.equals("gif")){
				String path=realPath+"image/"+fileName;
				file.transferTo(createFile(path));
				return path;
			}
			if(type.equals("rar")||type.equals("zip")||type.equals("7z")){
				String path=realPath+"package/"+fileName;
				file.transferTo(createFile(path));
				return path;
	
			}
			if(type.equals("doc")||type.equals("docx")||type.equals("txt")){
				String path=realPath+"file/"+fileName;
				file.transferTo(createFile(path));
				return path;
			}
			return "-1";

		}

	}
	/**
	 * 文件夹创建
	 * @author LR
	 * @param realPath
	 * @param fileName
	 * @return
	 */
	public static File createFile(String path) throws IOException {
		File newfile = new File(path);
		if(!newfile.exists()){
			newfile.getParentFile().mkdirs();
		}
		return newfile;
		
	}
}
