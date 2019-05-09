package com.cdtu.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cdtu.ai.util.FaceUtil;
import com.cdtu.model.Role;
import com.cdtu.service.SignInService;
import com.cdtu.util.MyDateUtil;
import com.cdtu.util.MyExceptionResolver;

@Controller
@RequestMapping(value = "signIn")
public class SignInController {
	private @Resource(name = "signInService") SignInService signInService;

	/**
	 * 老师进入签到功能，查询签到信息。<br/>
	 * 如果没有签到则返回签到历史记录，否则返回当前签到情况
	 *
	 * @author 李红兵
	 */
	@ResponseBody
	@RequiresRoles(value = { "teacher" })
	@RequestMapping(value = "querySignInInfo.do")
	public Map<String, Object> doQuerySignInInfo(@RequestBody Map<String, Object> paramsMap) {
		Map<String, Object> map = new HashMap<>();
		try {
			String cId = (String) paramsMap.get("cId");
			String tId = ((Role) SecurityUtils.getSubject().getPrincipal()).getUsername();
			String currPsId = signInService.getCurrPsId(tId, cId);// 获取当前发布的签到id
			if (currPsId == null) {
				List<Map<String, Object>> signIns = signInService.getPublishSignIns(tId, cId);
				signIns.forEach(signIn -> {
					String psId = (String) signIn.get("psId");
					mapTimeFormatter(signIn, "startTime");
					signIn.put("dueNum", signInService.getDueNum(psId));
					signIn.put("actualNum", signInService.getActualNum(psId));
				});
				map.put("signIns", signIns);
				map.put("isSignIning", false);
			} else {
				map.put("isSignIning", true);
				map.put("signInConditions", signInService.getSignInCondition(currPsId));
			}
			map.put("status", 200);
		} catch (Exception e) {
			MyExceptionResolver.handlException(map, e);
		}
		return map;
	}

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
			int signWay = Integer.parseInt((String) paramsMap.get("signWay"));
			int timeToLate = (int) paramsMap.get("timeToLate");// 多少分钟后算迟到
			int timeToStop = (int) paramsMap.get("timeToStop");// 多少分钟后结束签到
			String startTime = (String) paramsMap.get("startTime");// 签到开始时间
			String tId = ((Role) SecurityUtils.getSubject().getPrincipal()).getUsername();
			if (!signInService.isSignIning(tId, cId)) {
				String checkCode = null;
				if (signWay == 1) {
					checkCode = "";
					Random rander = new Random();
					for (int i = 0; i < 4; i++) {
						checkCode += rander.nextInt(10);
					}
					map.put("checkCode", checkCode);
				}
				String pattern = "yyyy-MM-dd HH:mm:ss";
				Date startDate = MyDateUtil.getFormattedDate(startTime, pattern);
				String psId = MyDateUtil.getFormattedTime(startDate, "yyyyMMddHHmmss");
				String lateTime = MyDateUtil.getIntervalTime(startDate, timeToLate, pattern);// 签到迟到时间
				String stopTime = MyDateUtil.getIntervalTime(startDate, timeToStop, pattern);// 签到结束时间
				signInService.startSignIn(psId, tId, cId, startTime, lateTime, stopTime, checkCode, signWay);
				map.put("psId", psId);
				map.put("status", 200);
			} else {
				map.put("msg", "有一个签到活动正在进行中，请结束后再试");
				map.put("status", 400);
			}
		} catch (Exception e) {
			MyExceptionResolver.handlException(map, e);
		}
		return map;
	}

	/**
	 * 老师进入历史签到记录详情，查看签到情况
	 *
	 * @author 李红兵
	 */
	@ResponseBody
	@RequiresRoles(value = { "teacher" })
	@RequestMapping(value = "querySignInCondition.do")
	public Map<String, Object> doQuerySignInCondition(@RequestBody Map<String, Object> paramsMap) {
		Map<String, Object> map = new HashMap<>();
		try {
			String psId = (String) paramsMap.get("psId");
			List<Map<String, Object>> signIned = signInService.getSignInByStatus(psId, true);
			mapsTimeFormatter(signIned, "signTime");// 未签到的时间为null，不用格式化
			map.put("signIned", signIned);
			map.put("unsignIned", signInService.getSignInByStatus(psId, false));
			map.put("status", 200);
		} catch (Exception e) {
			MyExceptionResolver.handlException(map, e);
		}
		return map;
	}

	/**
	 * 老师修改学生的签到标记
	 *
	 * @author 李红兵
	 */
	@ResponseBody
	@RequiresRoles(value = { "teacher" })
	@RequestMapping(value = "editSignMark.do")
	public Map<String, Object> doEditSignMark(@RequestBody Map<String, Object> paramsMap) {
		Map<String, Object> map = new HashMap<>();
		try {
			String ssId = (String) paramsMap.get("ssId");
			String mark = (String) paramsMap.get("mark");
			signInService.editSignMark(ssId, mark);
			map.put("status", 200);
		} catch (Exception e) {
			MyExceptionResolver.handlException(map, e);
		}
		return map;
	}

	/**
	 * 老师取消签到
	 *
	 * @author 李红兵
	 */
	@ResponseBody
	@RequiresRoles(value = { "teacher" })
	@RequestMapping(value = "cancelSignIn.do")
	public Map<String, Object> doCancelSignIn(@RequestBody Map<String, Object> paramsMap) {
		Map<String, Object> map = new HashMap<>();
		try {
			signInService.cancelSignIn((String) paramsMap.get("psId"));
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
			signInService.stopSignIn((String) paramsMap.get("psId"));
			map.put("status", 200);
		} catch (Exception e) {
			MyExceptionResolver.handlException(map, e);
		}
		return map;
	}

	/**
	 * 学生进入签到功能，查询签到信息。<br/>
	 * 如果当前没有签到活动，则返回返回该学生该门课的历史签到记录，<br/>
	 * 否则返回该签到活动的详情信息，和该学生该门课的历史签到记录。
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
			Map<String, Object> publishSignIn = signInService.getPublishSignIn(sId, cId);
			if (publishSignIn != null) {
				String psId = (String) publishSignIn.get("psId");
				mapTimeFormatter(publishSignIn, "startTime");
				mapTimeFormatter(publishSignIn, "lateTime");
				mapTimeFormatter(publishSignIn, "stopTime");
				map.put("isSignIned", signInService.isSignIned(psId, sId));
				map.put("publishSignIn", publishSignIn);
				map.put("isSignIning", true);
			} else {
				map.put("isSignIning", false);
			}
			List<Map<String, Object>> signIns = signInService.getStudentSignIns(sId, cId);
			signIns.forEach(signIn -> {
				mapTimeFormatter(signIn, "startTime");
			});
			map.put("signIns", signIns);
			map.put("status", 200);

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
			int signWay = Integer.parseInt((String) paramsMap.get("signWay"));
			String nowTime = (String) paramsMap.get("nowTime");
			String sId = ((Role) SecurityUtils.getSubject().getPrincipal()).getUsername();
			if (!signInService.isSignIned(psId, sId)) {
				if (signWay == 1) {
					String code = (String) paramsMap.get("chekCode");
					if (code.equals(signInService.getCheckCode(psId))) {
						signInService.signIn(psId, sId, nowTime);
						map.put("status", 200);
					} else {
						map.put("msg", "验证码不正确");
						map.put("status", 0);
					}
				} else if (signWay == 2) {
					String tId = signInService.getTId(psId);
					if (FaceUtil.validateFace((String) paramsMap.get("imgdata"), tId)) {
						signInService.signIn(psId, sId, nowTime);
						map.put("status", 200);
					} else {
						map.put("msg", "人脸信息不匹配");
						map.put("status", 0);
					}
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

	/**
	 * 学生统计自己的签到数据
	 *
	 * @author 李红兵
	 */
	@ResponseBody
	@RequiresRoles(value = { "student" })
	@RequestMapping(value = "staSignInfo.do")
	public Map<String, Object> doStaSignInfo(@RequestBody Map<String, Object> paramsMap) {
		Map<String, Object> map = new HashMap<>();
		try {
			String cId = (String) paramsMap.get("cId");
			String sId = ((Role) SecurityUtils.getSubject().getPrincipal()).getUsername();
			map.put("leaveEarlyNum", signInService.getSignInNumByMark(sId, cId, "早退"));
			map.put("signInedNum", signInService.getSignInNumByMark(sId, cId, "出勤"));
			map.put("absentNum", signInService.getSignInNumByMark(sId, cId, "缺勤"));
			map.put("leaveNum", signInService.getSignInNumByMark(sId, cId, "请假"));
			map.put("lateNum", signInService.getSignInNumByMark(sId, cId, "迟到"));
			map.put("totalNum", signInService.getPublishSignInNum(sId, cId));
			map.put("status", 200);
		} catch (Exception e) {
			MyExceptionResolver.handlException(map, e);
		}
		return map;
	}

	/**
	 * 格式化map里面的时间
	 *
	 * @author 李红兵
	 */
	private void mapTimeFormatter(Map<String, Object> map, String key) {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		String value = MyDateUtil.getFormattedTime(map.get(key), pattern);
		map.put(key, value);
	}

	/**
	 * 批量格式化map里面的时间
	 *
	 * @author 李红兵
	 */
	private void mapsTimeFormatter(List<Map<String, Object>> maps, String key) {
		maps.forEach(map -> {
			mapTimeFormatter(map, key);
		});
	}
}
