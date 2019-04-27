package com.cdtu.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baidu.aip.face.AipFace;
import com.cdtu.common.AIConstant;
import com.cdtu.common.FactoryUtil;
import com.cdtu.model.Role;
import com.cdtu.service.AdminstratorService;
import com.cdtu.service.StudentService;
import com.cdtu.service.TeacherService;
import com.cdtu.util.Jwt;
import com.cdtu.vo.FacePageBean;
import com.cdtu.vo.FacePageResponse;
import com.cdtu.vo.response.FaceSerachResponse;
/**
 * 人脸照片注册方法
 * @author 小帅丶
 *
 */
@Controller
@RequestMapping("/facemanager")
public class FaceManagerController {
	private @Resource(name = "studentService") StudentService studentService;
	private @Resource(name = "teacherService") TeacherService teacherService;
	private @Resource(name = "adminstratorService") AdminstratorService adminService;
	//人脸模块对象
	AipFace aipFace = FactoryUtil.getAipFace();
	private static Logger log = LoggerFactory.getLogger(FaceManagerController.class);
	/**
	 * 人脸注册 需要输入账号和密码 再去注册人脸
	 * @param facePageBean 请求的参数对象
	 * @param request  
	 * @param response
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public  Map<String,Object> addFace(@RequestBody Map<String,Object> paramMaps,HttpServletRequest request, HttpServletResponse response){
		log.info("发送过来的参数{}",JSONObject.toJSONString(paramMaps));
		Subject subject = SecurityUtils.getSubject();
		Map<String, Object> map = new HashMap<>();
		Role role = (Role) subject.getPrincipal();
		String username = role.getUsername();
		String roleName = role.getRole();
		String imgdata =(String) paramMaps.get("imgdata");
		String userId = username+"x"+roleName;//用户id（由数字、字母、下划线组成），长度限制128B
		HashMap<String, String> options = new HashMap<String, String>();
		options.put("user_info","小帅丶");
		org.json.JSONObject resultObject = aipFace.addUser(imgdata, "BASE64", AIConstant.GROUP_ID, userId, options);
		FaceSerachResponse faceSerachResponse = JSON.parseObject(resultObject.toString(), FaceSerachResponse.class);
		System.out.println(faceSerachResponse.toString());
		if("0".equals(faceSerachResponse.getError_code())&&"SUCCESS".equals(faceSerachResponse.getError_msg())){
			map.put("status", 200);
			map.put("msg", "注册成功！");
		}
		else if("0".equals(faceSerachResponse.getError_code())&&"face is already exist".equals(faceSerachResponse.getError_msg())){
			map.put("status", 222);
			map.put("msg", "人脸信息已存在");
		}else{
			map.put("status", 500);
			map.put("msg", "图片识别失败，请重新注册!");
		}
		log.info("注册返回的数据{}",resultObject.toString(2));
		return map;	
	}
	/**
	 * 人脸信息删除
	 * @param request
	 * @param response
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public void deleteFace(HttpServletRequest request, HttpServletResponse response){
		 // 传入可选参数调用接口
	    HashMap<String, String> options = new HashMap<String, String>();
	    Subject subject = SecurityUtils.getSubject();
		Role role = (Role) subject.getPrincipal();
		String username = role.getUsername();
		String roleName = role.getRole();
		String uid  = username+"x"+roleName;//用户id（由数字、字母、下划线组成），长度限制128B
	    options.put("group_id", AIConstant.GROUP_ID);
	    // 人脸删除
	    org.json.JSONObject res = aipFace.deleteUser(AIConstant.GROUP_ID, uid, options);
	    System.out.println(res.toString(2));
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
	public Map<String,Object> searchFace(@RequestBody FacePageBean facePageBean,HttpServletRequest request, HttpServletResponse response){
		FacePageResponse facePageResponse = new FacePageResponse();
		Role role = new Role();
		Map<String, Object> map = new HashMap<>();
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
				String userInfo = faceSerachResponse.getResult().getUser_list().get(0).getUser_id();//获取用户账号#用户角色信息
				String[] strings = userInfo.split("x");
				String userName = strings[0];
				String roleName = strings[1];
				String password =null;
				if ("teacher".equals(roleName)) {
					password = teacherService.getPasswordById(userName);
				} else if ("student".equals(roleName)) {
					password = studentService.getPasswordById(userName);
				} else if ("admin".equals(roleName)) {
					password = adminService.getPasswordById(userName);
				}
				
				// 获得当前用户对象
				Subject subject = SecurityUtils.getSubject();// 状态为“未认证”
				// 构造一个用户名密码令牌 ,是否记住我
				UsernamePasswordToken token1 = new UsernamePasswordToken(userName, password,
						roleName);
				try {
					subject.login(token1);
				} catch (Exception e) {
					map.put("msg", "账号或密码错误！");
					return map;
				}
				if (subject.isAuthenticated()) {
					System.out.println("认证成功");
					String checkcodePath = (String) request.getSession().getAttribute("checkcodePath");
					try {
						request.getSession().removeAttribute("checkcodePath");
						File file = new File(checkcodePath);
						file.delete();
					} catch (Exception e) {
					}
					request.getSession().setAttribute("role", role);
					// 给用户jwt加密生成token
					String token = Jwt.sign(role, 60L * 1000L * 30L);
					int timeOut = 1;
					if (role.isRememberMe() != false) {
						timeOut = 30;
					}
					map.put("token", token);
					map.put("status", 200);
					map.put("timeOut", timeOut);
				}
				return map;
				
			}else{
				map.put("code", "555");//facePageResponse.setError_code("555");
				map.put("msg", "人脸搜索失败，请重试或请先注册");//facePageResponse.setError_msg("人脸搜索失败，请重试或请先注册");
			}	
		}else{
			map.put("code", "555");//facePageResponse.setError_code("555");
			map.put("msg", "人脸搜索失败，请重试或请先注册");//facePageResponse.setError_msg("人脸搜索失败，请重试或请先注册");
		}
		log.info("搜索返回的数据{}",resultObject.toString(2));
		return map;
	}
}
