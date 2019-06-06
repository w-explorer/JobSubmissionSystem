package com.cdtu.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class ExportWord {
	public static void createWord(Map<String, Object> dataMap, String templatePath, String filePath) throws Exception {
		Configuration configuration = new Configuration(Configuration.VERSION_2_3_21);
		configuration.setDefaultEncoding("utf-8");// 设置编码
		configuration.setDirectoryForTemplateLoading(new File(templatePath));// 设置模板目录（不包括模板名称）
		Template template = configuration.getTemplate("moban.ftl", "utf-8");// 读取模板
		File file = new File(filePath);
		Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"), 10240);
		template.process(dataMap, writer);
		writer.close();
	}
}
