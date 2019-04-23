package com.cdtu.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cdtu.model.Role;
import com.cdtu.service.PublishSignInService;
import com.cdtu.service.StudentSignInService;
import com.cdtu.util.MyExceptionResolver;
import com.cdtu.util.MyTimerTask;

@Controller
@RequestMapping(value = "signIn")
public class SignInController {
	private @Resource(name = "ssService") StudentSignInService ssService;
	private @Resource(name = "psService") PublishSignInService psService;

	/**
	 * 老师开始签到，5分钟后停止签到
	 *
	 * @author 李红兵
	 */
	@ResponseBody
	@RequiresRoles(value = { "teacher" })
	@RequestMapping(value = "startSignIn.do")
	public Map<String, Object> doStartSignIn(@RequestBody Map<String, Object> paramsMap) {
		Map<String, Object> map = new HashMap<>();
		try {
			String checkCode = "";
			Random rander = new Random();
			for (int i = 0; i < 4; i++) {
				checkCode += rander.nextInt(10);
			}
			String cId = (String) paramsMap.get("cId");
			String time = (String) paramsMap.get("time");
			String psId = time.replace("-", "").replace(" ", "").replace(":", "");
			String tId = ((Role) SecurityUtils.getSubject().getPrincipal()).getUsername();
			psService.startSignIn(psId, tId, cId, time, checkCode);
			ssService.initDatabase(psId, cId);
			MyTimerTask.start(psId, psService);
			map.put("psId", psId);
			map.put("checkCode", checkCode);
			map.put("status", 200);
		} catch (Exception e) {
			MyExceptionResolver.handlException(map, e);
		}
		return map;
	}

	/**
	 * 老师停止签到
	 *
	 * @author 李红兵
	 */
	@ResponseBody
	@RequiresRoles(value = { "teacher" })
	@RequestMapping(value = "stopSignIn.do")
	public Map<String, Object> doStopSignIn(@RequestBody Map<String, Object> paramsMap) {
		Map<String, Object> map = new HashMap<>();
		try {
			psService.stopSignIn((String) paramsMap.get("psId"));
			MyTimerTask.cancel();
			map.put("status", 200);
		} catch (Exception e) {
			MyExceptionResolver.handlException(map, e);
		}
		return map;
	}
}
