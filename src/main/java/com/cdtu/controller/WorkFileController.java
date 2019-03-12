package com.cdtu.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Param;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.cdtu.model.Role;
import com.cdtu.service.WorkService;
import com.cdtu.util.DownloadFile;
import com.cdtu.util.OAUtil;

@Controller
@RequestMapping(value = "work")
public class WorkFileController {

	
	static String[] fileType={"jpg","png","gif","psd","webp","txt","doc","docx","XLS","XLSX","ppt","pptx","pdf"};
	@Resource(name="workService")
	private WorkService workService;
	@RequestMapping("uploadFiles.do")
	@RequiresRoles(value = {"student", "teacher"}, logical = Logical.OR)
	public @ResponseBody Map<String, Object> upFiles(@RequestParam("file") CommonsMultipartFile[] file,@RequestParam("pwId") String pwId) {
		System.out.println("uploadFiles-multipartResolver:" + file.length);
		// 判断file数组不能为空并且长度大于0
		Subject subject = SecurityUtils.getSubject();
		Role role = (Role) subject.getPrincipal();
		Map<String, Object> map=new HashMap<String, Object>();
        if (file != null && file.length > 0) {
            // 循环获取file数组中得文件
            try {
                for (int i = 0; i < file.length; i++) {
                    MultipartFile file1 = file[i];
                    // 保存文件s
                    if (!file1.isEmpty()) { //w.txt   5    3 
                    	
                    		 String savePath = "D:\\"+ File.separator  + "uploadFile"+ File.separator +"works"+ File.separator +pwId+File.separator +role.getRole()+ File.separator + role.getUsername();
                             String filename = file1.getOriginalFilename();
                             String type = filename.substring(filename.lastIndexOf(".")+1);
                             Boolean state =false;
                             for (String fileType : fileType) {
								if(fileType.equalsIgnoreCase(type)){
									state=true;
								}
							}
                             filename = filename.substring(filename.lastIndexOf("\\") + 1);
                             String files=OAUtil.getId()+"."+type;
                             // 得到上传文件的扩展名
                             String filePath = savePath + File.separator +files;
                             System.out.println("uploadFiles-filePath:" + filePath);
                             // 转存文件
                             File storeDirectory = new File(filePath);// 即代表文件又代表目录
                  			if (!storeDirectory.exists()) {
                  				storeDirectory.mkdirs();// 创建一个指定的目录
                  			}
                             file1.transferTo(new File(filePath));
                             System.out.println(pwId);
                             String wAddr=File.separator +"workfile"+ File.separator +pwId+ File.separator +role.getRole()+ File.separator + role.getUsername()+ File.separator +files;
                             System.out.println(wAddr);
                             if(role.getRole()=="teacher"){
                             workService.insertTeacherFilewAddr(pwId,wAddr,filename,type,state);
                    	}else{
                    		String sId=role.getUsername();
                    		System.out.println(sId);
                    		System.out.println(pwId);
                    		String wId=workService.selectwId(sId,pwId);
                    		
                    		 workService.insertStudentFilewAddr(wId,wAddr,filename,type,state);
                    	}
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                map.put("status", 0);
                map.put("msg", "上传失败");
            }
        }
        map.put("status", 200);
		return map;
	}

	@RequestMapping("/deleteFiles.do")
	@RequiresRoles(value = { "student", "teacher" }, logical = Logical.OR)
	public @ResponseBody Map<String, Object> deleteFiles(@RequestBody Map<String, Object> maps) {
		Subject subject = SecurityUtils.getSubject();
		Role role = (Role) subject.getPrincipal();
		Map<String, Object> map = new HashMap<>();
		if (role.getRole() == "teacher") {
			Integer tfId = (Integer) maps.get("tfId");
			String tfAdd = (String) maps.get("tfAdd");
			String workFile = "D:" + File.separator + "uploadFile" + File.separator + "works";
			String filePath = workFile + tfAdd.substring(9);
			File file = new File(filePath);
			file.delete();
			workService.deleteTeacherFile(tfId);
		} else {
			Integer sfId = (Integer) maps.get("sfId");
			String sfAdd = (String) maps.get("sfAdd");
			String workFile = "D:" + File.separator + "uploadFile" + File.separator + "works";
			String filePath = workFile + sfAdd.substring(9);
			File file = new File(filePath);
			file.delete();
			workService.deleteStudentFile(sfId);
		}
		map.put("status", 200);
		return map;
	}

	@ResponseBody
	@RequestMapping(value = "/downloadFile.do")
	@RequiresRoles(value = { "student", "teacher" }, logical = Logical.OR)
	public Map<String, Object> downloadFiles(@RequestBody Map<String, Object> maps, HttpServletResponse response,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		String Addr=(String) maps.get("tfAdd");
		try {
			String fileName = workService.selecttfNameService(Addr);
				String workFile = "D:" + File.separator + "uploadFile" + File.separator + "works";
				String filePath = workFile + Addr.substring(9);
				File file = new File(filePath);
				DownloadFile.downloadFile(file, fileName, response, request);
		} catch (Exception e) {
			map.put("status", 0);
			map.put("msg", "下载失败");
		}
		map.put("status", 200);
		return map;
	}
	/**
	 * @author weiyuhang
	 * @param work
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "downLoadAll.do")
	@RequiresRoles(value = { "student", "teacher" }, logical = Logical.OR)
	public @ResponseBody void downloadAll(@RequestBody Map<String, Object> maps, HttpServletResponse response,
			HttpServletRequest request) {

		// 需要压缩的文件
		// 压缩后的文件
		Subject subject = SecurityUtils.getSubject();
		Role role = (Role) subject.getPrincipal();
		List<String> Addrs=(List<String>) maps.get("Addrs");
		String workFile = "D:" + File.separator + "uploadFile" + File.separator + "works"+File.separator +role.getUsername();
		String resourcesName = workFile+ ".zip";
		String zipname=role.getUsername()+".zip";
		try{
			ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(resourcesName));
			InputStream input = null;
			for (String Addr : Addrs) {
				String works = "D:" + File.separator + "uploadFile" + File.separator + "works";
				String filePath = works + Addr.substring(9);
				File file = new File(filePath);
				input = new FileInputStream(file);
			    String	filename = Addr.substring(Addr.lastIndexOf("\\") + 1);
			    	zipOut.putNextEntry(new ZipEntry(workService.selecttfNameService(Addr)+filename));
				int temp = 0;
				while ((temp = input.read()) != -1)
					zipOut.write(temp);
				zipOut.closeEntry();
				input.close();
			}
			zipOut.close();
			File file = new File(resourcesName);
			DownloadFile.downloadFile(file, zipname,response, request);
		}catch (Exception e) {
			
		}
		
		
	}	
}
