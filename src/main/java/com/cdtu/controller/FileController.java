package com.cdtu.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.cdtu.model.Role;
import com.cdtu.service.PublishWorkService;
import com.cdtu.service.TeacherFileService;
import com.cdtu.service.TeacherService;
import com.cdtu.service.WorkService;
import com.cdtu.util.DownloadFile;
import com.cdtu.util.ExportExcel;
import com.cdtu.util.ExportWord;
import com.cdtu.util.GetRootPath;
import com.cdtu.util.MyExceptionResolver;
import com.cdtu.util.OAUtil;

@Controller
@RequestMapping(value = { "work", "file" })
public class FileController {
	//可预览的office文件类型
	private static String[] fileType = { "jpg", "png", "gif", "psd", "webp", "docx","docm","dotm","dotx","xlsx","xlsb","xls","xlsm"
			,"pptx","ppsx","ppt","pps","pptm","potm","ppam","potx","ppsm"};
	private @Resource(name = "workService") WorkService workService;
	private @Resource(name = "tFileService") TeacherFileService tFileService;
	private @Resource(name = "teacherService") TeacherService teacherservice;
	private @Resource(name = "publishWorkService") PublishWorkService publishWorkService;

	/**
	 * 老师、学生查看资源
	 *
	 * @author 李红兵
	 */
	@RequestMapping(value = "queryResource.do")
	@RequiresRoles(value = { "student", "teacher" }, logical = Logical.OR)
	public @ResponseBody Map<String, Object> doQueryResource(@RequestBody Map<String, Object> paramsMap) {
		Map<String, Object> map = new HashMap<>();
		try {
			String cId = (String) paramsMap.get("cId");
			String rId = ((Role) SecurityUtils.getSubject().getPrincipal()).getUsername();
			map.put("resources", tFileService.getResource(rId, cId));
			map.put("status", 200);
		} catch (Exception e) {
			MyExceptionResolver.handlException(map, e);
		}
		return map;
	}

	@RequestMapping("uploadFiles")
	@RequiresRoles(value = { "student", "teacher" }, logical = Logical.OR)
	public @ResponseBody Map<String, Object> upFiles(@RequestParam("file") CommonsMultipartFile[] file,
			@RequestParam("pwId") String pwId, HttpServletRequest request) {
//		System.out.println("uploadFiles-multipartResolver:" + file.length);
		// 判断file数组不能为空并且长度大于0
		Subject subject = SecurityUtils.getSubject();
		Role role = (Role) subject.getPrincipal();
		Map<String, Object> map = new HashMap<>();
		if (file != null && file.length > 0) {
			// 循环获取file数组中得文件
			try {
				for (int i = 0; i < file.length; i++) {
					MultipartFile file1 = file[i];
					// 保存文件s
					if (!file1.isEmpty()) { // w.txt 5 3

						String savePath = GetRootPath.getRootPath(request) + "/" + "uploadFile"
								+ "/" + "works" + "/" + pwId + "/" + role.getRole()
								+ "/" + role.getUsername();
						String filename = file1.getOriginalFilename();
						String type = filename.substring(filename.lastIndexOf(".") + 1);
						Boolean state = false;
						for (String fileType : fileType) {
							if (fileType.equalsIgnoreCase(type)) {
								state = true;
							}
						}
						filename = filename.substring(filename.lastIndexOf("\\") + 1);
						String files = OAUtil.getId() + "." + type;
						// 得到上传文件的扩展名
						String filePath = savePath + "/" + files;
//						System.out.println("uploadFiles-filePath:" + filePath);
						// 转存文件
						File storeDirectory = new File(filePath);// 即代表文件又代表目录
						if (!storeDirectory.exists()) {
							storeDirectory.mkdirs();// 创建一个指定的目录
						}
						file1.transferTo(new File(filePath));
//						System.out.println(pwId);
						String wAddr = "/" + "workfile" + "/" + pwId + "/"
								+ role.getRole() + "/" + role.getUsername() + "/" + files;
//						System.out.println(wAddr);
						if (role.getRole() == "teacher") {
							workService.insertTeacherFilewAddr(pwId, wAddr, filename, type, state);
						} else {
							String sId = role.getUsername();
//							System.out.println(sId);
//							System.out.println(pwId);
							String wId = workService.selectwId(sId, pwId);

							workService.insertStudentFilewAddr(wId, wAddr, filename, type, state);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				map.put("status", 0);
				map.put("msg", "上传失败");
			}
		}
		map.put("status", 200);
		return map;
	}

	@RequestMapping("/deleteFiles")
	@RequiresRoles(value = { "student", "teacher" }, logical = Logical.OR)
	public @ResponseBody Map<String, Object> deleteFiles(@RequestBody Map<String, Object> paramsMap,
			HttpServletRequest request) {
		Subject subject = SecurityUtils.getSubject();
		Role role = (Role) subject.getPrincipal();
		Map<String, Object> map = new HashMap<>();
		if (role.getRole() == "teacher") {
			Integer tfId = (Integer) paramsMap.get("tfId");
			String tfAdd = (String) paramsMap.get("tfAdd");
			String workFile = GetRootPath.getRootPath(request) + "/" + "uploadFile" + "/"
					+ "works";
			String filePath = workFile + tfAdd.substring(9);
			File file = new File(filePath);
			file.delete();
			workService.deleteTeacherFile(tfId);
		} else {
			Integer sfId = (Integer) paramsMap.get("tfId");
			String sfAdd = (String) paramsMap.get("tfAdd");
			String workFile = GetRootPath.getRootPath(request) + "/" + "uploadFile" + "/"
					+ "works";
			String filePath = workFile + sfAdd.substring(9);
			File file = new File(filePath);
			file.delete();
			workService.deleteStudentFile(sfId);
		}
		map.put("status", 200);
		return map;
	}

	@ResponseBody
	@RequestMapping(value = "/downloadFile.do")
	@RequiresRoles(value = { "student", "teacher" }, logical = Logical.OR)
	public Map<String, Object> downloadFiles(@RequestBody Map<String, Object> paramsMap, HttpServletResponse response,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		String Addr = (String) paramsMap.get("tfAdd");
		try {
			String fileName = workService.selecttfNameService(Addr);

			String workFile = GetRootPath.getRootPath(request) + "/" + "uploadFile" + "/"
					+ "works";
			String filePath = workFile + Addr.substring(9);
			File file = new File(filePath);
			DownloadFile.downloadFile(file, fileName, response, request);
			map.put("status", 200);
		} catch (Exception e) {
			map.put("status", 0);
			map.put("msg", "下载失败");
		}

		return map;
	}

	/**
	 * @author weiyuhang
	 * @param work
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "downLoadAll.do")
	@RequiresRoles(value = { "student", "teacher" }, logical = Logical.OR)
	public @ResponseBody Map<String, Object> downloadAll(@RequestBody Map<String, Object> maps,
			HttpServletResponse response, HttpServletRequest request) {
		// 需要压缩的文件
		// 压缩后的文件
		Map<String, Object> map = new HashMap<>();
		Subject subject = SecurityUtils.getSubject();
		Role role = (Role) subject.getPrincipal();
		@SuppressWarnings("unchecked")
		List<String> Addrs = (List<String>) maps.get("Addrs");
		String workFile = GetRootPath.getRootPath(request) + "/" + "uploadFile" + "/" + "works"
				+ "/" + role.getUsername();
		String resourcesName = workFile + ".zip";
		String zipname = role.getUsername() + ".zip";
		try {
			ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(resourcesName));
			InputStream input = null;
			for (String Addr : Addrs) {
				String works = GetRootPath.getRootPath(request) + "/" + "uploadFile" + "/"
						+ "works";
				String filePath = works + Addr.substring(9);
				File file = new File(filePath);
				input = new FileInputStream(file);
				String filename = Addr.substring(Addr.lastIndexOf("\\") + 1);
				zipOut.putNextEntry(new ZipEntry(workService.selecttfNameService(Addr) + filename));
				int temp = 0;
				while ((temp = input.read()) != -1) {
					zipOut.write(temp);
				}
				zipOut.closeEntry();
				input.close();
			}
			zipOut.close();
			File file = new File(resourcesName);
			DownloadFile.downloadFile(file, zipname, response, request);
			map.put("status", 200);
		} catch (Exception e) {
			map.put("status", 0);
			map.put("msg", "下载失败");
		}

		return map;
	}

	@RequestMapping(value = "downloadFile")
	@RequiresRoles(value = { "student", "teacher" }, logical = Logical.OR)
	public ResponseEntity<byte[]> downloadFile(@RequestBody Map<String, Object> maps, HttpServletResponse response,
			HttpServletRequest request) throws IOException {
		String Addr = (String) maps.get("tfAdd");
		String fileName = workService.selecttfNameService(Addr);
		String workFile = GetRootPath.getRootPath(request) + "/" + "uploadFile" + "/" + "works";
		String filePath = workFile + Addr.substring(9);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", fileName);
		return new ResponseEntity<>(FileUtils.readFileToByteArray(new File(filePath)), headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "downloadFileWork.do")
	@RequiresRoles(value = { "student", "teacher" }, logical = Logical.OR)
	public @ResponseBody Map<String, Object> downloadFileWork(@RequestBody Map<String, Object> maps,
			HttpServletRequest request) throws IOException {
		String pwId = (String) maps.get("pwId");
		Map<String, Object> map = new HashMap<>();
		try {
			List<Map<String, Object>> Addrs = workService.selectWorkAllAddr(pwId);
			List<Map<String, Object>> wId = workService.selectWorkId(pwId);
			Map<String, Object> name = workService.selectcName(pwId);
			String workFile = GetRootPath.getRootPath(request) + "/" + "uploadFile" + "/"
					+ "works" + "/" + "zip" + "/" + (String) name.get("c_name")
					+ (String) name.get("pw_name");
			String resourcesName = GetRootPath.getRootPath(request) + "/" + "uploadFile" + "/"
					+ "works" + "/" + "zip" + "/" + (String) name.get("c_name")
					+ (String) name.get("pw_name") + ".zip";
			String resourcesNames = "/" + "workfile" + "/" + "zip" + "/"
					+ (String) name.get("c_name") + (String) name.get("pw_name") + ".zip";
			com.cdtu.util.DeleteFolder.delFolder(workFile);
			File file = new File(workFile);
			if (!file.exists()) {
				file.mkdirs();// 创建目录
			}
			ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(resourcesName));
			InputStream input = null;
			BufferedInputStream binput = null;
			BufferedOutputStream boutput = null;
			for (Map<String, Object> w : wId) {
				String studentworkFile = workFile + "/" + (String) w.get("s_id") + (String) w.get("s_name");
				File studentfile = new File(studentworkFile);
				if (!studentfile.exists()) {
					studentfile.mkdirs();// 创建目录
				}
				String moban = GetRootPath.getRootPath(request) + "/" + "uploadFile" + "/"
						+ "works";
				String workpath = studentworkFile + "/" + "作业详情" + ".docx";
				if ((String) w.get("w_context") == null) {
					w.put("w_context", com.cdtu.util.CleanLable.getTextFrom("未作答"));
				}
				w.put("w_context", com.cdtu.util.CleanLable.getTextFrom((String) w.get("w_context")));
				w.put("pw_content", com.cdtu.util.CleanLable.getTextFrom((String) w.get("pw_content")));
				
				ExportWord.createWord(w, moban, workpath);
				for (Map<String, Object> Addr : Addrs) {
					String ws = (String) Addr.get("w_id");
					if (w.get("w_id").equals(ws)) {
						String works = GetRootPath.getRootPath(request) + "/" + "uploadFile" + "/"
								+ "works";
						String Addra = (String) Addr.get("s_f_add");
						String filename = (String) Addr.get("s_f_add");
						String filen = filename.substring(filename.lastIndexOf("\\") + 1);
						String filenames = filen.substring(0, 5) + Addr.get("s_f_name");
						String filePath = works + Addra.substring(9);
						String filePaths = studentworkFile + "/" + filenames;
						File files = new File(filePath);
						input = new FileInputStream(files);
						FileOutputStream output = new FileOutputStream(filePaths);
						binput = new BufferedInputStream(input);
						boutput = new BufferedOutputStream(output);
						int temp = 0;
						byte[] b = new byte[1024];
						while ((temp = binput.read(b)) != -1) {
							boutput.write(b, 0, temp);
						}
						output.flush();
						binput.close();
						boutput.close();
						output.close();
						input.close();
					}
				}
			}
			File filed = new File(workFile + "/" + "任务详情");
			if (!filed.exists()) {
				filed.mkdirs();// 创建目录
			}
			String teacherworkfile = workFile + "/" + "任务详情";
			List<Map<String, Object>> teacherFile = publishWorkService.selectTeacherFile(pwId);
			for (Map<String, Object> teacherfile : teacherFile) {
				String teacherfilename = (String) teacherfile.get("tfName");
				String teacherfileAddr = (String) teacherfile.get("tfAdd");
				String Addr = teacherfileAddr.substring(teacherfileAddr.lastIndexOf("\\") + 1);
				String works = GetRootPath.getRootPath(request) + "/" + "uploadFile" + "/"
						+ "works";
				FileInputStream inputs = new FileInputStream(works + teacherfileAddr.substring(9));
				FileOutputStream outputn = new FileOutputStream(
						teacherworkfile + "/" + Addr.substring(0, 5) + teacherfilename);
				binput = new BufferedInputStream(inputs);
				boutput = new BufferedOutputStream(outputn);
				byte[] b = new byte[1024];
				int temp = 0;
				while ((temp = binput.read(b)) != -1) {
					boutput.write(b, 0, temp);
				}
				outputn.flush();
				binput.close();
				boutput.close();
				outputn.close();
				inputs.close();
			}
			List<Map<String, Object>> workScores = workService.selectScore(pwId);
			// excel标题
			String[] title = { "学号", "姓名", "成绩" };
			String fileName = "学生成绩表" + ".xlsx";
			String sheetName = "学生成绩表";
			String[][] content = new String[workScores.size()][3];
			// 创建HSSFWorkbook
			for (int i = 0; i < workScores.size(); i++) {
				Map<String, Object> workScore = workScores.get(i);
				content[i][0] = (String) workScore.get("sId");
				content[i][1] = (String) workScore.get("sName");
				if ((Boolean) workScore.get("sWstate") == false) {
					content[i][2] = "未提交";
				} else if ((Boolean) workScore.get("tWstate") == false) {
					content[i][2] = "未批改";
				} else {
					Integer a = (Integer) workScore.get("wScore");
					content[i][2] = Integer.toString(a);
				}
			}
			HSSFWorkbook wb = ExportExcel.getHSSFWorkbook(sheetName, title, content, null);
			FileOutputStream output = new FileOutputStream(workFile + "/" + fileName);
			wb.write(output);

			output.close();
			Map<String, Object> publishwork = publishWorkService.selectPublishwork(pwId);
			if (publishwork.get("pwName") == null) {
				publishwork.put("pwName", "null");
			}
			if (publishwork.get("pwContent") == null) {
				publishwork.put("pwContent", "null");
			}
			String moban = GetRootPath.getRootPath(request) + "/uploadFile";
			String filePath = workFile + "/" + "任务详情" + "/" + "作业详情" + ".docx";
			ExportWord.createWord(publishwork, moban, filePath);
			com.cdtu.util.Compresszip.compress(file, zipOut, (String) name.get("c_name") + (String) name.get("pw_name"),
					true);
			zipOut.close();
			com.cdtu.util.DeleteFolder.delFolder(workFile);
			map.put("status", 200);
			map.put("Addr", resourcesNames);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", 0);
			map.put("msg", "导出失败");
		}
		return map;
	}
}
