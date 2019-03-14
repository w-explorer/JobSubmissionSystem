package com.cdtu.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.cdtu.util.MaxPage;
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
	private WorkMapper workMapper;
	@Resource
	private StudentSelectCourseMapper studentSelectCourseMapper;

	@Override
	public Student getStudentBysIdAndsPassword(Role role) {
		return studentMapper.getStudentBysIdAndsPassword(role);
	}

	@Override
	public void updatasPasswordBysId(Role role) {
		studentMapper.updatasPasswordBysId(role);
	}

	@Override
	public String getPasswordById(String id) {
		return studentMapper.selectPasswordById(id);
	}

	@Override
	public List<PublishEstimate> selectPublishEstimate(StudentSelectCourse studentSelectCourse) {
		if (studentSelectCourse.getTscId() != null)
			return publishEstimateMapper.selectPublishEstimateBytscId(studentSelectCourse.getsId(),
					studentSelectCourse.getTscId());
		else {
			studentSelectCourse.setCtId(studentSelectCourse.getId());
			return publishEstimateMapper.selectPublishEstimateByctId(studentSelectCourse.getsId(),
					studentSelectCourse.getCtId());
		}
	}

	@Override
	public Integer submitEvaluation(Estimate estimate) {
		if (estimate != null) {
			estimate.seteId(OAUtil.getId());
			estimateMapper.insertEstimate(estimate);
			return 1;
		} else
			return -1;
	}

	@Override
	public Map<String, Object> demonPublishWork(StudentSelectCourse studentSelectCourse, String sId) {
		List<PublishWork> publishWorkLs = new ArrayList<>();
		Map<String, Object> publishWorks = new HashMap<>();
		if (studentSelectCourse != null) {
			if ("2".equals(studentSelectCourse.getState())) {
				publishWorkLs = publishWorkMapper.selectStudentPublishWorkBycId(sId, studentSelectCourse.getcId(), true,
						(studentSelectCourse.getPage() - 1) * 5, 5);// 进行
				publishWorks.put("max", MaxPage.getMaxPage(
						publishWorkMapper.selectStudentPublishWorkCount(sId, studentSelectCourse.getcId(), true), 5));// 最大页数
			}
			if ("3".equals(studentSelectCourse.getState())) {
				publishWorkLs = publishWorkMapper.selectStudentPublishWorkBycId(sId, studentSelectCourse.getcId(),
						false, (studentSelectCourse.getPage() - 1) * 5, 5);// 结束
				publishWorks.put("max", MaxPage.getMaxPage(
						publishWorkMapper.selectStudentPublishWorkCount(sId, studentSelectCourse.getcId(), false), 5));// 最大页数
			}
			if ("1".equals(studentSelectCourse.getState())) {
				publishWorkLs = publishWorkMapper.selectStudentPublishWorkBycId(sId, studentSelectCourse.getcId(), null,
						(studentSelectCourse.getPage() - 1) * 5, 5);// 全部
				publishWorks.put("max", MaxPage.getMaxPage(
						publishWorkMapper.selectStudentPublishWorkCount(sId, studentSelectCourse.getcId(), null), 5));// 最大页数
			}

			publishWorks.put("countprocess",
					publishWorkMapper.selectStudentPublishWorkCount(sId, studentSelectCourse.getcId(), true));
			publishWorks.put("countover",
					publishWorkMapper.selectStudentPublishWorkCount(sId, studentSelectCourse.getcId(), false));
			publishWorks.put("countall",
					publishWorkMapper.selectStudentPublishWorkCount(sId, studentSelectCourse.getcId(), null));
			for (PublishWork publishWork : publishWorkLs) {
				if (workMapper.selectWorkCount(studentSelectCourse.getsId(), publishWork.getPwId()) != 0) {
					publishWork.setwStringState("已参与");
					publishWork.setwBooleanState(true);
				} else {
					publishWork.setwStringState("未参与");
					publishWork.setwBooleanState(false);
				}
				if (publishWork.getPwState() == true) {
					publishWork.setPwStringState("进行中");
					publishWork.setPwBooleanState(publishWork.getPwState());
				} else {
					publishWork.setPwStringState("已结束");
					publishWork.setPwBooleanState(publishWork.getPwState());
				}
			}
			publishWorks.put("publishWorks", publishWorkLs);
			return publishWorks;
		} else
			return null;

	}

	/*
	 * @Override public Map<String, Object> selectPublishWork(StudentSelectCourse
	 * studentSelectCourse) { List<PublishWork> publishWorkLs = new
	 * ArrayList<PublishWork>(); Map<String, Object> publishWorks = new
	 * HashMap<String, Object>(); if (studentSelectCourse.getTscId() != null) {
	 * 
	 * if ("2".equals(studentSelectCourse.getState())) { publishWorkLs =
	 * this.publishWorkMapper.selectStudentPublishWorkBytscId(studentSelectCourse.
	 * getsId(), studentSelectCourse.getTscId(), true,
	 * (studentSelectCourse.getPage() - 1) * 5, 5);// 进行 publishWorks.put("max",
	 * MaxPage.getMaxPage(
	 * this.publishWorkMapper.selectCountBypwStateBytscId(studentSelectCourse.
	 * getTscId(), true),5));// 最大页数 } if
	 * ("3".equals(studentSelectCourse.getState())) { publishWorkLs =
	 * this.publishWorkMapper.selectStudentPublishWorkBytscId(studentSelectCourse.
	 * getsId(), studentSelectCourse.getTscId(), false,
	 * (studentSelectCourse.getPage() - 1) * 5, 5);// 结束 publishWorks.put("max",
	 * MaxPage.getMaxPage(
	 * this.publishWorkMapper.selectCountBypwStateBytscId(studentSelectCourse.
	 * getTscId(), false),5));// 最大页数 } if
	 * ("1".equals(studentSelectCourse.getState())) { publishWorkLs =
	 * this.publishWorkMapper.selectStudentPublishWorkBytscId(studentSelectCourse.
	 * getsId(), studentSelectCourse.getTscId(), null,
	 * (studentSelectCourse.getPage() - 1) * 5, 5);// 全部 publishWorks.put("max",
	 * MaxPage.getMaxPage(
	 * this.publishWorkMapper.selectCountBypwStateBytscId(studentSelectCourse.
	 * getTscId(), null),5));// 最大页数 }
	 * 
	 * publishWorks.put("countprocess",
	 * this.publishWorkMapper.selectCountBypwStateBytscId(studentSelectCourse.
	 * getTscId(), true)); publishWorks.put("countover",
	 * this.publishWorkMapper.selectCountBypwStateBytscId(studentSelectCourse.
	 * getTscId(), false)); publishWorks.put("countall",
	 * this.publishWorkMapper.selectCountBypwStateBytscId(studentSelectCourse.
	 * getTscId(), null)); for (PublishWork publishWork : publishWorkLs) { if
	 * (this.workMapper.selectWorkCount(studentSelectCourse.getsId(),
	 * publishWork.getPwId()) != 0) { publishWork.setwStringState("已参与");
	 * publishWork.setwBooleanState(true); } else {
	 * publishWork.setwStringState("未参与"); publishWork.setwBooleanState(false); } if
	 * (publishWork.getPwState() == true) { publishWork.setPwStringState("进行中");
	 * publishWork.setPwBooleanState(publishWork.getPwState()); } else {
	 * publishWork.setPwStringState("已结束");
	 * publishWork.setPwBooleanState(publishWork.getPwState()); } }
	 * publishWorks.put("publishWorks", publishWorkLs); return publishWorks; } else
	 * { studentSelectCourse.setCtId(studentSelectCourse.getId()); if
	 * ("2".equals(studentSelectCourse.getState())) { publishWorkLs =
	 * this.publishWorkMapper.selectStudentPublishWorkByctId(studentSelectCourse.
	 * getsId(), studentSelectCourse.getCtId(), true, (studentSelectCourse.getPage()
	 * - 1) * 5, 5);// 进行 publishWorks.put("max", MaxPage.getMaxPage(
	 * this.publishWorkMapper.selectCountBypwStateBytscId(studentSelectCourse.
	 * getCtId(), true),5));// 最大页数 } if
	 * ("3".equals(studentSelectCourse.getState())) { publishWorkLs =
	 * this.publishWorkMapper.selectStudentPublishWorkByctId(studentSelectCourse.
	 * getsId(), studentSelectCourse.getCtId(), false,
	 * (studentSelectCourse.getPage() - 1) * 5, 5);// 结束 publishWorks.put("max",
	 * MaxPage.getMaxPage(
	 * this.publishWorkMapper.selectCountBypwStateBytscId(studentSelectCourse.
	 * getCtId(), false),5));// 最大页数 } if
	 * ("1".equals(studentSelectCourse.getState())) { publishWorkLs =
	 * this.publishWorkMapper.selectStudentPublishWorkByctId(studentSelectCourse.
	 * getsId(), studentSelectCourse.getCtId(), null, (studentSelectCourse.getPage()
	 * - 1) * 5, 5);// 全部 publishWorks.put("max", MaxPage.getMaxPage(
	 * this.publishWorkMapper.selectCountBypwStateBytscId(studentSelectCourse.
	 * getCtId(), null),5));// 最大页数 }
	 * 
	 * publishWorks.put("countprocess",
	 * this.publishWorkMapper.selectCountBypwStateBytscId(studentSelectCourse.
	 * getCtId(), true)); publishWorks.put("countover",
	 * this.publishWorkMapper.selectCountBypwStateBytscId(studentSelectCourse.
	 * getCtId(), false)); publishWorks.put("countall",
	 * this.publishWorkMapper.selectCountBypwStateBytscId(studentSelectCourse.
	 * getCtId(), null)); for (PublishWork publishWork : publishWorkLs) { if
	 * (this.workMapper.selectWorkCount(studentSelectCourse.getsId(),
	 * publishWork.getPwId()) != 0) { publishWork.setwStringState("已参与");
	 * publishWork.setwBooleanState(true); } else {
	 * publishWork.setwStringState("未参与"); publishWork.setwBooleanState(false); } if
	 * (publishWork.getPwState() == true) { publishWork.setPwStringState("进行中");
	 * publishWork.setPwBooleanState(publishWork.getPwState()); } else {
	 * publishWork.setPwStringState("已结束");
	 * publishWork.setPwBooleanState(publishWork.getPwState()); } }
	 * publishWorks.put("publishWorks", publishWorkLs); return publishWorks; } }
	 */
	@Override
	public Integer submitWork(Work work) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		if (work != null) {
				String nowDate = dateFormat.format(date);
				work.setsWState(true);
				work.setwTime(nowDate);
				workMapper.studentUpdateWork(work);
				return 1;
		} else
			return -1;
	}

	@Override
	public Work showStudentWork(String sId, String pwId) {
		if (sId != null && pwId != null)
			return workMapper.selectWork(sId, pwId);
		else
			return null;

	}

	@Override
	public Integer updateWorkwAddr(String sId, String pwId, String wAddr) {
		workMapper.updateWorkFlieStudent(sId, pwId, wAddr);
		return 1;
	}

	@Override
	public String selectWorkwAddr(String sId, String pwId) {
		return workMapper.selectWorkFlieStudent(sId, pwId);
	}

	@Override
	public String selectStudentName(String id) {
		return studentMapper.selectStudentName(id);
	}

	@Override
	public List<CourseStudent> selectCourseStudentService(CourseWapper coursewapper) {

		return com.cdtu.util.OrderByUtil.OrderASC(studentSelectCourseMapper.selectCourseStudent(coursewapper));
	}

	@Override
	public void updataAvatar(String path, String username) {
		studentMapper.updataAvatar(path, username);

	}

	@Override
	public List<Map<String, Object>> fuzzySearchStudentByNameOrId(String nameOrId,String cId) {
		// TODO Auto-generated method stub
		return studentMapper.fuzzySearchStudentByNameOrId(nameOrId,cId);
	}

	@Override
	public List<Map<String, Object>> SearchStudentById(String sId) {
		// TODO Auto-generated method stub
		return studentMapper.SearchStudentById(sId);
	}

	@Override
	public void CreatStudentTableDescRank(String cId,String tId) {
		// TODO Auto-generated method stub
		studentMapper.CreatStudentTableDescRank(cId,tId);
	}

	@Override
	public List<Map<String, Object>> selectStudents() {
		// TODO Auto-generated method stub
		return studentMapper.selectStudents();
	}

}
