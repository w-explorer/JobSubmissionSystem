package com.cdtu.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cdtu.service.AdminstratorService;
import com.cdtu.service.CourseService;
@Controller
@RequestMapping(value="admin")
public class AdminController {
@Resource(name="adminstratorService") private AdminstratorService admin;
@Resource(name="") private CourseService courseService;
/**
 * 查询学院
 * @author weiyuhang
 */
@RequestMapping(value="selectSchool")
@RequiresRoles(value = { "student", "teacher" }, logical = Logical.OR)
public @ResponseBody Map<String, Object>  selectSchool(){
	Map<String, Object> map=new HashMap<String,Object>();
	try{
		map.put("Schools",admin.selectSchool());
		map.put("status",200);
	}catch(Exception e){
		map.put("status",0);
		map.put("msg", "服务器异常");
	}
	return map;
}
/**
 * 查询课程
 * @author weiyuhang
 */
@RequestMapping(value="selectCourse")
@RequiresRoles(value = { "student", "teacher" }, logical = Logical.OR)
public @ResponseBody Map<String, Object>  selectCourse(@RequestBody Map<String,Object> pmap){
	return pmap;
}
}
