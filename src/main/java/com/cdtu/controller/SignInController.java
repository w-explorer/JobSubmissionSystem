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

@Controller
@RequestMapping(value = "signIn")
public class SignInController {
	private @Resource(name = "ssService") StudentSignInService ssService;
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
			int timeToLate = (int) paramsMap.get("timeToLate");// 多少分钟后算迟到
			int timeToStop = (int) paramsMap.get("timeToStop");// 多少分钟后结束签到
			String tId = ((Role) SecurityUtils.getSubject().getPrincipal()).getUsername();
			if (!psService.isSignIning(tId, cId)) {
				String checkCode = "";
				Random rander = new Random();
				for (int i = 0; i < 4; i++) {
					checkCode += rander.nextInt(10);
				}
				Date now = new Date();
				String pattern = "yyyy-MM-dd HH:mm:ss";
				String psId = MyDateUtil.getFormattedTime(now, "yyyyMMddHHmmss");
				String startTime = MyDateUtil.getFormattedTime(now, pattern);// 签到开始时间
				String lateTime = MyDateUtil.getIntervalTime(now, timeToLate, pattern);// 签到迟到时间
				String stopTime = MyDateUtil.getIntervalTime(now, timeToStop, pattern);// 签到结束时间
				psService.startSignIn(psId, tId, cId, startTime, lateTime, stopTime, checkCode);
				ssService.initDatabase(psId, cId);
				map.put("psId", psId);
				map.put("checkCode", checkCode);
				map.put("status", 200);
			} else {
				map.put("msg", "签到未结束，不能签到");
				map.put("status", 400);
			}
		} catch (Exception e) {
			MyExceptionResolver.handlException(map, e);
		}
		return map;
	}

	/**
	 * 老师查看签到情况
	 *
	 * @author 李红兵
	 */
	@ResponseBody
	@RequiresRoles(value = { "teacher" })
	@RequestMapping(value = "querySignInCon.do")
	public Map<String, Object> doQuerySignInCon(@RequestBody Map<String, Object> paramsMap) {
		Map<String, Object> map = new HashMap<>();
		try {
			String psId = (String) paramsMap.get("psId");
			map.put("signInOfStudents", ssService.getSignInCondition(psId));
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
			map.put("status", 200);
		} catch (Exception e) {
			MyExceptionResolver.handlException(map, e);
		}
		return map;
	}

	/**
	 * 学生查看当前是否有签到
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
				psMap.put("isLate", "1".equals(psMap.get("isLate").toString()));
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
				if (code.equals(psService.getCheckCode(psId, sId))) {
					ssService.signIn(psId, sId);
					map.put("status", 200);
				} else {
					map.put("msg", "验证码不正确");
					map.put("status", 0);
				}
			} else {
				map.put("msg", "请勿重复签到");
				map.put("status", 400);
			}
		} catch (Exception e) {
			MyExceptionResolver.handlException(map, e);
		}
		return map;
	}
}
