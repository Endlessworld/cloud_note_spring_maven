package com.endless.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
    @Bean
    public ViewResolver viewResolver() {
	System.out.println("配置视图解析器");
	InternalResourceViewResolver resolver = new InternalResourceViewResolver();
	resolver.setPrefix("/");
	resolver.setSuffix(".jsp");
	resolver.setExposeContextBeansAsAttributes(true);
	return resolver;
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
	 System.out.println("允许访问静态资源");
	configurer.enable(); 

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
	System.out.println("映射静态资源");
	registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
	registry.addResourceHandler("/app/**").addResourceLocations("classpath:/static/WebApp/");
	super.addResourceHandlers(registry);
    }
   
   
  //文件上传，bean必须写name属性且必须为multipartResolver，不然取不到文件对象，别问我为什么，我也唔知
    @Bean(name="multipartResolver")
    protected CommonsMultipartResolver MultipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        try {
			multipartResolver.setUploadTempDir(new FileSystemResource("/tmp"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//可不设置
        multipartResolver.setMaxUploadSize(2097152);//2M
        multipartResolver.setMaxInMemorySize(0);
        multipartResolver.setDefaultEncoding("UTF-8");
        return multipartResolver;
    }

 
}
