package com.revature;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



/**
 * ApplicationConfig to setup the controller without any XML configuration. This
 * will register a dispatch servlet in order to communicate with the front end.
 * 
 * @author Jose Rivera
 *
 */
@EnableWebMvc
@ComponentScan
@Configuration
@EnableTransactionManagement
public class ApplicationConfig implements WebMvcConfigurer, WebApplicationInitializer {

	//use this logger later for debugging if needed. REMOVE in production, or implement AOP logging.  
	Logger log = Logger.getLogger(ApplicationConfig.class);
	
	/**
	 * Spring MVC configuration without using XML configuration. Uses
	 * WebApplicationInitializer to configure the dispatcher servlet
	 * 
	 * @param servletContext
	 * @throws ServletException
	 */
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		log.info( "\n +-----------------------------------------------------------------------------------------+"
				+ "\n | In onStartup() before instantiation/ registry of AnnotationConfigWevApplicationContext. |"
				+ "\n +-----------------------------------------------------------------------------------------+");
		AnnotationConfigWebApplicationContext container = new AnnotationConfigWebApplicationContext();
		container.register(ApplicationConfig.class);
		
		servletContext.addListener(new ContextLoaderListener(container));

		ServletRegistration.Dynamic dispatcher = servletContext.addServlet("DispatcherServlet",
				new DispatcherServlet(container));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/");
	}

	/**
	 * Method creates a BasicDataSource Object, configured by reading the
	 * app.properties file to set up the properties to the data source. Annotated
	 * with @Bean to let Spring know to instantiate, configure, and initialize the
	 * new object to be managed by the Spring container
	 * 
	 * @return A BasicDataSource object used to configure the session
	 */
	@Bean
	public BasicDataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream dbPropertiesStream = classLoader.getResourceAsStream("app.properties");
		Properties dbProperties = new Properties();

		try {
			dbProperties.load(dbPropertiesStream);
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		dataSource.setDriverClassName(dbProperties.getProperty("driver"));
		dataSource.setUrl(dbProperties.getProperty("url"));
		dataSource.setUsername(dbProperties.getProperty("usr"));
		dataSource.setPassword(dbProperties.getProperty("pw"));

		return dataSource;
	}

	/**
	 * Method to create the session factory bean. Configures the session using the
	 * app.properties and also configures hibernate properties. Annotated with @Bean
	 * to let Spring know to instantiate, configure, and initialize the new object
	 * to be managed by the Spring container
	 * 
	 * @return A valid sessionFactory
	 */
	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setPackagesToScan("com.revature");
		sessionFactory.setHibernateProperties(hibernateProperties());
		return sessionFactory;
	}

	/**
	 * Method to create the transaction manager for hibernate. Annotated with @Bean
	 * to let Spring know to instantiate, configure, and initialize the new object
	 * to be managed by the Spring container
	 * 
	 * @return A PlatformTransactionManager for hibernate to use
	 */
	@Bean
	public PlatformTransactionManager hibernateTransactionManager() {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory().getObject());
		return transactionManager;
	}

	/**
	 * Method to set up the hibernate properties without the need for a separate
	 * hibernate.cfg.xml file
	 * 
	 * @return A Properties object to be used to set the hibernate properties for
	 *         the session
	 */
	private final Properties hibernateProperties() {
		Properties hibernateProperties = new Properties();
		hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
		hibernateProperties.setProperty("hibernate.show_sql", "true");
		hibernateProperties.setProperty("hibernate.format_sql", "true");
		hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "validate");
		return hibernateProperties;
	}
}
