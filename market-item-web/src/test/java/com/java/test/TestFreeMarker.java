package com.java.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.quartz.impl.calendar.DailyCalendar;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class TestFreeMarker {
	
	@Test
	public void testCreateFile() {
		Configuration configuration = new Configuration(Configuration.getVersion());
		try {
			//设置模版路径
			configuration.setDirectoryForTemplateLoading(new File("D:\\Perdio6\\Java\\market-item-web\\src\\main\\webapp\\WEB-INF\\ftl"));
			//设置字符集
			configuration.setDefaultEncoding("UTF-8");
			//加载模版，建立模版对象
			Template template = configuration.getTemplate("hello.ftl");
			//创建数据集
			Map map = new HashMap<>();
			
			map.put("hello", "Hello World");
			Writer writer = new FileWriter(new File("D:\\Perdio6\\hello.html"));
			template.process(map, writer);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testCreateFile2() {
		Configuration configuration = new Configuration(Configuration.getVersion());
		try {
			//设置模版路径
			configuration.setDirectoryForTemplateLoading(new File("D:\\Perdio6\\Java\\market-item-web\\src\\main\\webapp\\WEB-INF\\ftl"));
			//设置字符集
			configuration.setDefaultEncoding("UTF-8");
			//加载模版，建立模版对象
			Template template = configuration.getTemplate("student.ftl");
			//创建数据集
			Map map = new HashMap<>();
			List<Student> list = new ArrayList<>();
			list.add(new Student(1,"张三1",21,"南京1"));
			list.add(new Student(2,"张三2",22,"南京2"));
			list.add(new Student(3,"张三3",23,"南京3"));
			list.add(new Student(4,"张三4",24,"南京4"));
			list.add(new Student(5,"张三5",25,"南京5"));
			
			map.put("date", new Date());
			map.put("stuList", list);
			map.put("student", new Student(1,"张三1",21,"南京1"));
			map.put("aaaa", null);
			map.put("hello", "Hello World");
			Writer writer = new FileWriter(new File("D:\\Perdio6\\student.html"));
			template.process(map, writer);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
	}
}
