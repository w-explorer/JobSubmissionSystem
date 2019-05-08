package com.cdtu.ai.util;

import org.json.JSONObject;
import com.alibaba.fastjson.JSON;
import com.baidu.aip.face.AipFace;
import com.cdtu.ai.model.FacePageResponse;
import com.cdtu.ai.response.FaceSerachResponse;

public class FaceUtil {
	// 人脸模块对象
	private static AipFace aipFace = FactoryUtil.getAipFace();
	/**
	 * 验证老师人脸
	 * @param imgdata
	 * @param rId
	 * @author wenc 
	 */
	public static boolean validateFace(String imgdata, String rId) {
		FacePageResponse facePageResponse = new FacePageResponse();
		String groupIdList = "xsdemo";// 用户组id（由数字、字母、下划线组成），长度限制128B
		JSONObject resultObject = aipFace.search(imgdata, "BASE64", groupIdList, null);
		// 使用fastjson处理返回的内容 直接用javabean接收 方便取值
		FaceSerachResponse faceSerachResponse = JSON.parseObject(resultObject.toString(), FaceSerachResponse.class);
		if ("0".equals(faceSerachResponse.getError_code()) && "SUCCESS".equals(faceSerachResponse.getError_msg())) {
			if (faceSerachResponse.getResult().getUser_list().get(0).getScore() > 80f) {
				facePageResponse.setError_code(faceSerachResponse.getError_code());
				facePageResponse.setError_msg(faceSerachResponse.getError_msg());
				facePageResponse.setUser_info(faceSerachResponse.getResult().getUser_list().get(0).getUser_info());
				String userInfo = faceSerachResponse.getResult().getUser_list().get(0).getUser_id();// 获取用户账号#用户角色信息
				String[] strings = userInfo.split("x");
				String userName = strings[0]; //查到当前老师的工号
				return rId.equals(userName);
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}
