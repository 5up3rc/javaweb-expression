package org.javaweb.exp.config;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * Servlet初始化
 */
public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(JavaWebExpApplication.class);
	}

	@Override
	public void onStartup(ServletContext sc) throws ServletException {
		System.setProperty("file.encoding", "UTF-8");// 设置文件系统编码
		super.onStartup(sc);
	}

}