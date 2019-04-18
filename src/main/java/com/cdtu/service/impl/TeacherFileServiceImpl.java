package com.cdtu.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cdtu.mapper.TeacherFileMapper;
import com.cdtu.service.TeacherFileService;

@Service(value = "tFileService")
public class TeacherFileServiceImpl implements TeacherFileService {
	private @Resource TeacherFileMapper tFileMapper;

	/**
	 * 老师上传资源文件
	 *
	 * @author 李红兵
	 */
	@Override
	public void uploadFile(String tId, String cId, String pathName, String fileName, boolean onlineReadAble,
			String fileType) {
		tFileMapper.insert(tId, cId, pathName, fileName, onlineReadAble, fileType);
	}

	/**
	 * 老师删除资源文件
	 *
	 * @author 李红兵
	 */
	@Override
	public void deleteFile(String pathName, String fileName) {
		tFileMapper.delete(pathName, fileName);
	}

}
