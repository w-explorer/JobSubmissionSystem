package com.cdtu.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cdtu.model.Role;
import com.cdtu.service.PublishSignInService;
import com.cdtu.util.MyExceptionResolver;

@Controller
@RequestMapping(value = "signIn")
public class SignInController {
	private @Resource(name = "psService") PublishSignInService psService;

	/**
	 * 老师开始签到
	 *
	 * @author 李红兵
	 */
	@ResponseBody
	@RequiresRoles(value = { "teacher" })
	@RequestMapping(value = "startSignIn.do")
	public Map<String, Object> doStartSignIn(@RequestBody Map<String, Object> paramsMap) {
		Map<String, Object> map = new HashMap<>();
		try {
			String cId = (String) paramsMap.get("cId");
			String time = (String) paramsMap.get("time");
			String tId = ((Role) SecurityUtils.getSubject().getPrincipal()).getUsername();
			psService.startSignIn(tId, cId, time);
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					System.out.println("stop");
					cancel();
				}
			}, 1000 * 60 * 5);
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
//			String cId = (String) paramsMap.get("cId");
//			String time = (String) paramsMap.get("time");
//			String tId = ((Role) SecurityUtils.getSubject().getPrincipal()).getUsername();
//			psService.stoptSignIn(tId, cId, time);
		} catch (Exception e) {
			MyExceptionResolver.handlException(map, e);
		}
		return map;
	}
}
