package com.cdtu.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Controller
@RequestMapping("work")
public class WorkFileController {
	@RequestMapping("uploadFile")
	public @ResponseBody Map<String, Object> upFiles(@RequestParam("file") CommonsMultipartFile file,
			@RequestParam("pwId") String pwId) {
		return null;
	}
}
