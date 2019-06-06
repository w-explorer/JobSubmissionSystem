package com.cdtu.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cdtu.mapper.TeacherFileMapper;
import com.cdtu.service.TeacherFileService;

@Service(value = "tFileService")
public class TeacherFileServiceImpl implements TeacherFileService {
	private @Resource TeacherFileMapper tFileMapper;

	/**
	 * 上传资源文件
	 *
	 * @author 李红兵
	 */
	@Override
	public void uploadFile(String tId, String cId, String pathName, String fileName, boolean onlineReadAble,
			String fileType) {
		tFileMapper.insert(tId, cId, pathName, fileName, onlineReadAble, fileType);
	}

	/**
	 * 删除资源文件
	 *
	 * @author 李红兵
	 */
	@Override
	public void deleteFile(String pathName, String fileName) {
		tFileMapper.delete(pathName, fileName);
	}

	/**
	 * 获取资源文件
	 *
	 * @author 李红兵
	 */
	@Override
	public List<Map<String, Object>> getResource(String rId, String cId) {
		List<Map<String, Object>> maps = tFileMapper.select(rId, cId);
		maps.forEach(map -> {
			map.put("onlineReadAble", "1".equals(map.get("onlineReadAble").toString()));
		});
		return maps;
	}

}
