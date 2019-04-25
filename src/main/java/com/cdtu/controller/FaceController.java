package com.cdtu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * 页面跳转
 * @author 小帅丶
 *
 */
@Controller
@RequestMapping("/")
public class FaceController {
	//人脸模块对象
	@RequestMapping(method=RequestMethod.GET)
	public String index(){
		return "face";
	}
	@RequestMapping(value="/storage",method=RequestMethod.GET)
	public String storage(){
		return "storage";
	}
}
