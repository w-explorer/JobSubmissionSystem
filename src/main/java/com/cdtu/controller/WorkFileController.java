package com.cdtu.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

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

import com.cdtu.model.PublishWork;
import com.cdtu.model.Role;
import com.cdtu.service.WorkService;
import com.cdtu.util.DownloadFile;

@Controller
@RequestMapping("work")
public class WorkFileController {
	static String Id;
	
	


	public static String getId() {
		return Id;
	}


	public static void setId(String id) {
		Id = id;
	}


	@Resource(name="workService")
	private WorkService workService;
	
	
	@RequestMapping("uploadFiles")
	@RequiresRoles(value = {"teacher"})
	public @ResponseBody Map<String, Object> upFiles(@RequestParam("files") CommonsMultipartFile[] files) {
		System.out.println("uploadFiles-multipartResolver:" + files.length);
        // 判断file数组不能为空并且长度大于0
		Subject subject = SecurityUtils.getSubject();
		Role role = (Role) subject.getPrincipal();
		Map<String, Object> map=new HashMap<String, Object>();
        if (files != null && files.length > 0) {
            // 循环获取file数组中得文件
            try {
                for (int i = 0; i < files.length; i++) {
                    MultipartFile file = files[i];
                    // 保存文件
                    if (!file.isEmpty()) {
                    	if(role.getRole()=="teacher"){
                    		 String savePath = "D:\\"+ File.separator  + "uploadFile"+ File.separator +"works"+ File.separator +Id+File.separator +role.getRole()+ File.separator + role.getUsername();
                             String filename = file.getOriginalFilename();
                             filename = filename.substring(filename.lastIndexOf("\\") + 1);
                             // 得到上传文件的扩展名
                             String filePath = savePath + File.separator + filename;
                             System.out.println("uploadFiles-filePath:" + filePath);
                             // 转存文件
                             File storeDirectory = new File(filePath);// 即代表文件又代表目录
                  			if (!storeDirectory.exists()) {
                  				storeDirectory.mkdirs();// 创建一个指定的目录
                  			}
                             file.transferTo(new File(filePath));
                             System.out.println(Id);
                             String wAddr=File.separator +"workfile"+ File.separator +Id+ File.separator +role.getRole()+ File.separator + role.getUsername()+ File.separator +filename;
                             System.out.println(wAddr);
                             workService.insertTeacherFilewAddr(Id,wAddr,filename);
                    	}
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
    
         
		return null;

	}
	
	@RequestMapping("deleteFiles")
	@RequiresRoles(value = {"student", "teacher"}, logical = Logical.OR)
	public @ResponseBody Map<String, Object> deleteFiles(@RequestBody Map<String, Object> maps) {
		Subject subject = SecurityUtils.getSubject();
		Role role = (Role) subject.getPrincipal();
		Map<String, Object> map=new HashMap<String, Object>();
		if(role.getRole()=="teacher"){
			Integer tfId= (Integer) maps.get("tfId");
			String tfAdd=(String) maps.get("tfAdd");
			String workFile ="D:" + File.separator + "uploadFile" + File.separator + "works";
			String filePath=workFile+tfAdd.substring(9);
			File file=new File(filePath);
			file.delete();
			workService.deleteTeacherFile(tfId);
		}else{
			Integer sfId= (Integer) maps.get("sfId");
			String sfAdd=(String) map.get("sfAdd");
			String workFile ="D:" + File.separator + "uploadFile" + File.separator + "works";
			String filePath=workFile+sfAdd.substring(9);
			File file=new File(filePath);
			file.delete();
			workService.deleteStudentFile(sfId);
		}
		map.put("status", 200);
		return map;
	}
	
	@RequestMapping("downloadFile")
	@RequiresRoles(value = {"student", "teacher"}, logical = Logical.OR)
	public @ResponseBody Map<String, Object> downloadFiles(@RequestBody Map<String, Object> maps, HttpServletResponse response) {
		Map<String, Object> map=new HashMap<String, Object>();
	       try {
	    	   if(maps.get("tfAdd")!=null){
					String tfAdd=(String) maps.get("tfAdd");
					System.out.println(tfAdd);
					String workFile ="D:" + File.separator + "uploadFile" + File.separator + "works";
					System.out.println(workFile);
					String filePath=workFile+tfAdd.substring(9);
					System.out.println(filePath);
					File file=new File(filePath);
					String filename =tfAdd.substring(tfAdd.lastIndexOf("\\") + 1);	
					System.out.println(filename);
					DownloadFile.downloadFile(file, filename, response);
				}else{
					String sfAdd=(String) map.get("sfAdd");
					String workFile ="D:" + File.separator + "uploadFile" + File.separator + "works";
					String filePath=workFile+sfAdd.substring(9);
					File file=new File(filePath);
					String filename =sfAdd.substring(sfAdd.lastIndexOf("\\") + 1);
					DownloadFile.downloadFile(file, filename, response);
				}
		} catch (Exception e) {
		map.put("status", 0);
		map.put("msg", "下载失败");
		}
	     map.put("status", 200);
		return map;
	}
	
	
}
