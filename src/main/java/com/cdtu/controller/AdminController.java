package com.cdtu.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping(value="admin")
public class AdminController {
@RequestMapping(value="selectSchool")
public Map<String, Object>  selectSchool(){
	
	return null;
	
}
@RequestMapping(value="selectCourse")
public Map<String, Object>  selectCourse(){
	
	return null;
	
}
}
