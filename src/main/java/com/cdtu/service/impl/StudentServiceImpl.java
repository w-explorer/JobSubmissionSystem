package com.cdtu.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdtu.mapper.EstimateMapper;
import com.cdtu.mapper.PublishEstimateMapper;
import com.cdtu.mapper.PublishWorkMapper;
import com.cdtu.mapper.StudentMapper;
import com.cdtu.mapper.StudentSelectCourseMapper;
import com.cdtu.mapper.WorkMapper;
import com.cdtu.model.CourseStudent;
import com.cdtu.model.CourseWapper;
import com.cdtu.model.Estimate;
import com.cdtu.model.PublishWork;
import com.cdtu.model.Role;
import com.cdtu.model.StudentSelectCourse;
import com.cdtu.model.Work;
import com.cdtu.service.StudentService;
import com.cdtu.util.FormatDateToString;
import com.cdtu.util.MaxPage;
import com.cdtu.util.OAUtil;

@Transactional
@Service("studentService")
public class StudentServiceImpl implements StudentService {
	private @Resource WorkMapper workMapper;
	private @Resource StudentMapper studentMapper;
	private @Resource EstimateMapper estimateMapper;
	private @Resource PublishWorkMapper publishWorkMapper;
	private @Resource PublishEstimateMapper publishEstimateMapper;
	private @Resource StudentSelectCourseMapper studentSelectCourseMapper;

	@Override
	public Map<String, Object> getStudentBysIdAndsPassword(Role role) {
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
	public List<Map<String, Object>> selectPublishEstimate(String cId, String sId, int start, int end) {
		return publishEstimateMapper.selectPublishEstimateBytscId(cId, sId, start, end);
	}

	@Override
	public Integer submitEvaluation(Estimate estimate) {
		if (estimate != null) {
			estimate.seteId(OAUtil.getId());
			estimate.setsEState(true);
			estimateMapper.insertEstimate(estimate);
			return 1;
		} else {
			return -1;
		}
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
				publishWork.setPwEnd(publishWork.getPwEnd().substring(0, 16));
				if (publishWork.getwState()) {
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
		} else {
			return null;
		}

	}

	@Override
	public Integer submitWork(Work work) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		if (work != null) {
			String nowDate = dateFormat.format(date);
			work.setsWState(true);
			work.setwTime(nowDate);
			workMapper.studentUpdateWork(work);
			return 1;
		} else {
			return -1;
		}
	}

	@Override
	public Work showStudentWork(String sId, String pwId) {
		if (sId != null && pwId != null) {
			return workMapper.selectWork(sId, pwId);
		} else {
			return null;
		}

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
	public List<Map<String, Object>> fuzzySearchStudentByNameOrId(String nameOrId) {
		return studentMapper.fuzzySearchStudentByNameOrId(nameOrId);
	}

	@Override
	public List<Map<String, Object>> SearchStudentById(String sId) {
		return studentMapper.SearchStudentById(sId);
	}

	@Override
	public void CreatStudentTableDescRank(String cId, String tId) {
		studentMapper.CreatStudentTableDescRank(cId, tId);
	}

	@Override
	public List<Map<String, Object>> selectStudents(int page) {
		return studentMapper.selectStudents((page - 1) * 30, page * 30);
	}

	@Override
	public void updateRoleInfo(String email, String phone, String username) {
		studentMapper.updateRoleInfo(email, phone, username);
	}
}
