package com.cdtu.controller;

import java.util.Date;
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
import com.cdtu.util.MyDateUtil;
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
			Date now = new Date();
			String cId = (String) paramsMap.get("cId");
			String psId = MyDateUtil.getFormattedTime(now, "yyyyMMddHHmmss");
			String time = MyDateUtil.getFormattedTime(now, "yyyy-MM-dd HH:mm:ss");
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

	/**
	 * 学生查看签到状态
	 *
	 * @author 李红兵
	 */
	@ResponseBody
	@RequiresRoles(value = { "student" })
	@RequestMapping(value = "querySignIn.do")
	public Map<String, Object> doQuerySignIn(@RequestBody Map<String, Object> paramsMap) {
		Map<String, Object> map = new HashMap<>();
		try {
			String cId = (String) paramsMap.get("cId");
			String sId = ((Role) SecurityUtils.getSubject().getPrincipal()).getUsername();
			Map<String, Object> psMap = psService.getPublishSignIn(sId, cId);
			if (psMap != null) {
				map.putAll(psMap);
				map.putAll(ssService.getStudentSignIn((String) psMap.get("psId"), sId));
				map.put("status", 200);
			} else {
				map.put("msg", "老师暂未开始签到");
				map.put("status", 404);
			}
		} catch (Exception e) {
			MyExceptionResolver.handlException(map, e);
		}
		return map;
	}

	/**
	 * 学生签到
	 *
	 * @author 李红兵
	 */
	@ResponseBody
	@RequiresRoles(value = { "student" })
	@RequestMapping(value = "signIn.do")
	public Map<String, Object> doSignIn(@RequestBody Map<String, Object> paramsMap) {
		Map<String, Object> map = new HashMap<>();
		try {
			String psId = (String) paramsMap.get("psId");
			String code = (String) paramsMap.get("chekCode");
			String sId = ((Role) SecurityUtils.getSubject().getPrincipal()).getUsername();
			if (!ssService.isSigned(psId, sId)) {
				String time = MyDateUtil.getFormattedTime(new Date(), "yyyy-MM-dd HH:mm:ss");
				Map<String, Object> ssMap = psService.getTimeCodeStatus(psId, sId);
				if (ssMap.get("checkCode").equals(code)) {
					String mark = "";
					String startTime = MyDateUtil.getFormattedTime(ssMap.get("startTime"), "yyyy-MM-dd HH:mm:ss");
					int timeInterval = MyDateUtil.getTimeInterval(startTime, time);
					if (timeInterval <= 5) {
						mark = (boolean) ssMap.get("sStatus") ? "已签" : "迟到";
					} else if (timeInterval <= 40) {
						mark = "迟到";
					} else {
						mark = "旷课";
					}
					ssService.signIn(psId, sId, time, mark);
					map.put("status", 200);
				} else {
					map.put("msg", "验证码不正确");
					map.put("status", 0);
				}
			} else {
				map.put("msg", "请勿重复签到");
				map.put("status", 400);
			}
			map.put("status", 200);
		} catch (Exception e) {
			MyExceptionResolver.handlException(map, e);
		}
		return map;
	}
}
