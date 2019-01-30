package com.cdtu.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cdtu.mapper.EstimateMapper;
import com.cdtu.mapper.PublishEstimateMapper;
import com.cdtu.mapper.PublishWorkMapper;
import com.cdtu.mapper.StudentMapper;
import com.cdtu.mapper.StudentSelectCourseMapper;
import com.cdtu.mapper.WorkMapper;
import com.cdtu.model.CourseStudent;
import com.cdtu.model.CourseWapper;
import com.cdtu.model.Estimate;
import com.cdtu.model.PublishEstimate;
import com.cdtu.model.PublishWork;
import com.cdtu.model.Role;
import com.cdtu.model.Student;
import com.cdtu.model.StudentSelectCourse;
import com.cdtu.model.Work;
import com.cdtu.service.StudentService;
import com.cdtu.util.OAUtil;
@Service("studentService")
public class StudentServiceImpl implements StudentService {
	@Resource
	private StudentMapper studentMapper;
	@Resource
	private PublishEstimateMapper publishEstimateMapper;
	@Resource
	private EstimateMapper estimateMapper;
	@Resource
	private PublishWorkMapper publishWorkMapper;
	@Resource
	private WorkMapper work; 
	@Resource
	private StudentSelectCourseMapper studentSelectCourseMapper;
	@Override
	public Student getStudentBysIdAndsPassword(Role role) {
		return this.studentMapper.getStudentBysIdAndsPassword(role);
	}
	@Override
	public void updatasPasswordBysId(Role role) {
		 this.studentMapper.updatasPasswordBysId(role);
	}
	@Override
	public String getPasswordById(String id) {
		return this.studentMapper.selectPasswordById(id);
	}
	
	
	
	
	
	@Override
	public List<PublishEstimate> selectPublishEstimate(StudentSelectCourse studentSelectCourse) {
		if(studentSelectCourse.getTscId()!=null){
			return this.publishEstimateMapper.selectPublishEstimateBytscId(studentSelectCourse.getsId(),studentSelectCourse.getTscId());
		}
		else{
			studentSelectCourse.setCtId(studentSelectCourse.getId());
			return this.publishEstimateMapper.selectPublishEstimateByctId(studentSelectCourse.getsId(),studentSelectCourse.getCtId());
		}
	}
	@Override
	public Integer submitEvaluation(Estimate estimate) {
		if(estimate!=null){
			estimate.seteId(OAUtil.getId());
			this.estimateMapper.insertEstimate(estimate);
			return 1;
		}else{
			return -1;
		}
	}
	@Override
	public Map<String, Object> selectPublishWork(StudentSelectCourse studentSelectCourse) {
		List<PublishWork> publishWorkLs=new ArrayList<PublishWork>();
		Map<String, Object> publishWorks=new HashMap<String, Object>();
		if(studentSelectCourse.getTscId()!=null){
			
			if("process".equals(studentSelectCourse.getState())){
				publishWorkLs=this.publishWorkMapper.selectStudentPublishWorkBytscId(studentSelectCourse.getsId(),studentSelectCourse.getTscId(),true);
			}
			if("over".equals(studentSelectCourse.getState())){
				publishWorkLs=this.publishWorkMapper.selectStudentPublishWorkBytscId(studentSelectCourse.getsId(),studentSelectCourse.getTscId(),false);
			}
			if("all".equals(studentSelectCourse.getState())){
				publishWorkLs=this.publishWorkMapper.selectStudentPublishWorkBytscId(studentSelectCourse.getsId(),studentSelectCourse.getTscId(),null);
			}
			
			publishWorks.put("countprocess", this.publishWorkMapper.selectCountBypwStateBytscId(studentSelectCourse.getTscId(), true));
			publishWorks.put("countover", this.publishWorkMapper.selectCountBypwStateBytscId(studentSelectCourse.getTscId(), false));
			publishWorks.put("countall", this.publishWorkMapper.selectCountBypwStateBytscId(studentSelectCourse.getTscId(), null));
			for(PublishWork publishWork:publishWorkLs){
				if(this.work.selectWorkCount(studentSelectCourse.getsId(), publishWork.getPwId())!=0){
					publishWork.setWstate("已参与");
				}else{
					publishWork.setWstate("未参与");
				}
			}
			publishWorks.put("publishWorks",publishWorkLs);
			return publishWorks;
		}
		else{
			studentSelectCourse.setCtId(studentSelectCourse.getId());
			if("process".equals(studentSelectCourse.getState())){
				publishWorkLs=this.publishWorkMapper.selectStudentPublishWorkByctId(studentSelectCourse.getsId(),studentSelectCourse.getCtId(),true);
			}
			if("over".equals(studentSelectCourse.getState())){
				publishWorkLs=this.publishWorkMapper.selectStudentPublishWorkByctId(studentSelectCourse.getsId(),studentSelectCourse.getCtId(),false);
			}
			if("all".equals(studentSelectCourse.getState())){
				publishWorkLs=this.publishWorkMapper.selectStudentPublishWorkByctId(studentSelectCourse.getsId(),studentSelectCourse.getCtId(),null);
			}
			
			publishWorks.put("countprocess", this.publishWorkMapper.selectCountBypwStateBytscId(studentSelectCourse.getCtId(), true));
			publishWorks.put("countover", this.publishWorkMapper.selectCountBypwStateBytscId(studentSelectCourse.getCtId(), false));
			publishWorks.put("countall", this.publishWorkMapper.selectCountBypwStateBytscId(studentSelectCourse.getCtId(), null));
			for(PublishWork publishWork:publishWorkLs){
				if(this.work.selectWorkCount(studentSelectCourse.getsId(), publishWork.getPwId())!=0){
					publishWork.setWstate("已参与");
				}else{
					publishWork.setWstate("未参与");
				}
			}
			publishWorks.put("publishWorks",publishWorkLs);
			return publishWorks;
		}	
	}
	@Override
	public Integer submitWork(Work work) {
		if(work!=null){
			if(this.work.selectWork(work.getsId(), work.getPwId())==null){
				work.setId(OAUtil.getId());
				this.work.insertWork(work);
				return 1;
			}
			if(this.work.selectWork(work.getsId(), work.getPwId())!=null){
				this.work.studentUpdateWork(work);
				return 1;
			}
			return -1;
		}else{
			return -1;
		}
	}
	@Override
	public Work showStudentWork(String sId,String pwId){
		if(sId!=null&&pwId!=null){
			return this.work.selectWork(sId, pwId);
		}else{
			return null;
		}
		
	}
	@Override
	public Integer updateWorkwAddr(String sId, String pwId, String wAddr) {
		this.work.updateWorkFlieStudent(sId, pwId, wAddr);
		return 1;
	}
	@Override
	public String selectWorkwAddr(String sId, String pwId) {
		return this.work.selectWorkFlieStudent(sId, pwId);
	}
	@Override
	public String selectStudentName(String id){
		return this.studentMapper.selectStudentName(id);
	}
	@Override
	public List<CourseStudent> selectCourseStudentService(CourseWapper coursewapper) {
		
		return this.studentSelectCourseMapper.selectCourseStudent(coursewapper);
	}



}
