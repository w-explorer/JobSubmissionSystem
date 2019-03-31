package com.cdtu.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdtu.mapper.AdminstratorMapper;
import com.cdtu.mapper.CourseMapper;
import com.cdtu.mapper.SchoolMapper;
import com.cdtu.model.Role;
import com.cdtu.service.AdminstratorService;
@Transactional
@Service("adminstratorService")
public class AdminstratorServiceImpl implements AdminstratorService {
    public @Resource SchoolMapper schoolMapper;
	public @Resource AdminstratorMapper adminstratorMapper;
	public @Resource CourseMapper courseMapper;
	
	@Override
	public String getPasswordById(String userName) {
		return adminstratorMapper.getPasswordById(userName);
	}
	@Override
	public Map<String,Object> getAdminByaIdAndaPassword(Role role) {
		return adminstratorMapper.getAdminByaIdAndaPassword(role);
	}
	@Override
	public void updateRoleInfo(String email, String phone, String username) {
		// TODO Auto-generated method stub
		adminstratorMapper.updateRoleInfo(email,  phone,  username);
	}
	@Override
	public void updataAvatar(String path, String username) {
		adminstratorMapper.updataAvatar(path, username);
		
	}
	/**
	 * 查询学院
	 * @author weiyuhang
	 */
	@Override
	public List<String> selectSchool() {
	
		return schoolMapper.selectSchool();
	}
	/**
	 * 查询学院课程
	 * @author weiyuhang
	 */
	@Override
	public List<Map<String, Object>> selectSchoolCourse(String scId) {
		
		return  courseMapper.selectSchoolCourse(scId);
	}

	

}
