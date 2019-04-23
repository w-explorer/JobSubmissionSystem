package com.cdtu.xsshome.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baidu.aip.face.AipFace;
import com.cdtu.xsshome.common.FactoryUtil;
import com.cdtu.xsshome.vo.FacePageBean;
import com.cdtu.xsshome.vo.FacePageResponse;
import com.cdtu.xsshome.vo.response.FaceSerachResponse;

/**
 * 人脸照片注册方法
 * @author 小帅丶
 *
 */
@Controller
@RequestMapping("/facemanager")
public class FaceManagerController {
	//人脸模块对象
	AipFace aipFace = FactoryUtil.getAipFace();
	private static Logger log = LoggerFactory.getLogger(FaceManagerController.class);
	/**
	 * 人脸注册
	 * @param facePageBean 请求的参数对象
	 * @param request  
	 * @param response
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public String addFace(FacePageBean facePageBean, HttpServletRequest request, HttpServletResponse response){
		log.info("发送过来的参数{}",JSONObject.toJSONString(facePageBean));
		FacePageResponse facePageResponse = new FacePageResponse();
		if(facePageBean.getUser_info().equals("")||null==facePageBean.getUser_info()){
			facePageResponse.setError_code("100");
			facePageResponse.setError_msg("用户名称为空 请填写后重试");
			return JSON.toJSONString(facePageResponse);
		}else{
			String groupId = "xsdemo";//用户组id（由数字、字母、下划线组成），长度限制128B
			String userId ="1"; //UUID.randomUUID().toString().replace("-", "").toUpperCase();//用户id（由数字、字母、下划线组成），长度限制128B
			HashMap<String, String> options = new HashMap<String, String>();
			options.put("user_info","小帅丶");
			org.json.JSONObject resultObject = aipFace.addUser(facePageBean.getImgdata(), "BASE64", groupId, userId, options);
			log.info("注册返回的数据{}",resultObject.toString(2));
			return resultObject.toString();	
		}
	}
	/**
	 * 人脸搜索
	 * @param facePageBean 请求的参数对象
	 * @param request  
	 * @param response
	 * @return
	 */
	@RequestMapping("/search")
	@ResponseBody
	public FacePageResponse searchFace(FacePageBean facePageBean, HttpServletRequest request, HttpServletResponse response){
		FacePageResponse facePageResponse = new FacePageResponse();
		log.info("发送过来的参数{}",JSONObject.toJSONString(facePageBean));
		String groupIdList = "xsdemo";//用户组id（由数字、字母、下划线组成），长度限制128B
		org.json.JSONObject resultObject = aipFace.search(facePageBean.getImgdata(), "BASE64", groupIdList, null);
		//使用fastjson处理返回的内容 直接用javabean接收 方便取值
		FaceSerachResponse faceSerachResponse = JSON.parseObject(resultObject.toString(), FaceSerachResponse.class);
		if("0".equals(faceSerachResponse.getError_code())&&"SUCCESS".equals(faceSerachResponse.getError_msg())){
			if(faceSerachResponse.getResult().getUser_list().get(0).getScore()>80f){
				facePageResponse.setError_code(faceSerachResponse.getError_code());
				facePageResponse.setError_msg(faceSerachResponse.getError_msg());
				facePageResponse.setUser_info(faceSerachResponse.getResult().getUser_list().get(0).getUser_info());
			}else{
				facePageResponse.setError_code("555");
				facePageResponse.setError_msg("人脸搜索失败，请重试或请先注册");
			}
		}else{
			facePageResponse.setError_code("500");
			facePageResponse.setError_msg(faceSerachResponse.getError_msg());
		}
		log.info("搜索返回的数据{}",resultObject.toString(2));
		return facePageResponse;
	}
}
