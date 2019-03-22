package com.revature;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@ComponentScan
@Configuration

/**
 * 
 * @author B
 * <p>
 *	This class allows us to avoid the xml configuration for spring. 
 * </p>
 */
public class ApplicationConfig implements WebMvcConfigurer, WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		AnnotationConfigWebApplicationContext container = new AnnotationConfigWebApplicationContext();
		container.register(ApplicationConfig.class);
		
		servletContext.addListener(new ContextLoaderListener(container));
		ServletRegistration.Dynamic dispatcher = servletContext.addServlet("DispatcherServlet", new DispatcherServlet(container));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/");
	}
	
}
