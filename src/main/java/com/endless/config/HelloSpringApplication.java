package com.endless.config;

import java.nio.charset.StandardCharsets;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

 

/**
 * 
 * 在 Servlet 3.0 环境下，Servlet 容器会在 classpath 下搜索实现了 javax.servlet
 * .ServletContainerInitializer 接口的任何类，找到之后用它来初始化 Servlet 容器。 Spring
 * 实现了以上接口，实现类叫做 SpringServletContainerInitializer， 它会依次搜寻实现了
 * WebApplicationInitializer的任何类，并委派这个类实现配置。 之后，Spring 3.2 开始引入一个简易的
 * WebApplicationInitializer 实现类，这就是
 * AbstractAnnotationConfigDispatcherServletInitializer。 所以继承
 * AbstractAnnotationConfigDispatcherServletInitializer之后，也就是间接实现了
 * WebApplicationInitializer,在 Servlet 3.0 容器中，它会被自动搜索到，被用来配置 servlet 上下文。
 * 
 * 
 */
@Configuration
@EnableAspectJAutoProxy // 相当于 xml 中的 <aop:aspectj-autoproxy/>
@EnableTransactionManagement // 开启注解事务
@ComponentScan(basePackages = { "com.endless" } )
@MapperScan(basePackages = { "com.endless.dao" })
@EnableWebMvc
@EnableAutoConfiguration 
public class HelloSpringApplication extends AbstractAnnotationConfigDispatcherServletInitializer {
	
    @Override
    protected Class<?>[] getRootConfigClasses() {

	System.out.println("加载根配置类");
	return new Class<?>[] { RootConfig.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
	System.out.println("加载服务配置类");
	return new Class<?>[] { WebConfig.class };
    }

    @Override
    protected String[] getServletMappings() {
	String url = "/";
	System.out.println("指定前置控制器入口:[ " + url + " ]");
	return new String[] { url };
    }
 
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
	System.out.println("配置字符过滤器");
	FilterRegistration.Dynamic encodingFilter = servletContext.addFilter("encodingFilter",
		CharacterEncodingFilter.class);
	encodingFilter.setInitParameter("encoding", String.valueOf(StandardCharsets.UTF_8));
	encodingFilter.setInitParameter("forceEncoding", "true");
	encodingFilter.addMappingForUrlPatterns(null, false, "/*");
	super.onStartup(servletContext);
    }

}
