package com.endless.demo;

import java.nio.charset.StandardCharsets;

 
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import com.endless.config.RootConfig;
import com.endless.config.TaskExecutorConfig;
import com.endless.config.WebConfig;
	 
	/* 
		SpringBoot默认开启了： 
		DispatcherServletAutoConfiguration 注册DispatcherServlet 
		EmbeddedServletContainerAutoConfiguration.EmbeddedTomcat 注册Tomcat容器 
		ErrorMvcAutoConfiguration 注册异常处理器 
		HttpEncodingAutoConfiguration 注册编码过滤器CharacterEncodingFilter 
		HttpMessageConvertersAutoConfiguration 注册json或者xml处理器 
		JacksonAutoConfiguration 注册json对象解析器 
		MultipartAutoConfiguration 注册文件传输处理器 
		TransactionAutoConfiguration 注册事物管理处理器 
		ValidationAutoConfiguration 注册数据校验处理器 
		WebMvcAutoConfiguration 注册SpringMvc相关处理器
	*/
 
/*
 * 
@ConditionalOnClass ： classpath中存在该类时起效 
@ConditionalOnMissingClass ： classpath中不存在该类时起效 
@ConditionalOnBean ： DI容器中存在该类型Bean时起效 
@ConditionalOnMissingBean ： DI容器中不存在该类型Bean时起效 
@ConditionalOnSingleCandidate ： DI容器中该类型Bean只有一个或@Primary的只有一个时起效 
@ConditionalOnExpression ： SpEL表达式结果为true时 
@ConditionalOnProperty ： 参数设置或者值一致时起效 
@ConditionalOnResource ： 指定的文件存在时起效 
@ConditionalOnJndi ： 指定的JNDI存在时起效 
@ConditionalOnJava ： 指定的Java版本存在时起效 
@ConditionalOnWebApplication ： Web应用环境下起效 
@ConditionalOnNotWebApplication ： 非Web应用环境下起效
*/
/*配置类加载顺序
 * @AutoConfigureAfter：在指定的配置类初始化后再加载 
@AutoConfigureBefore：在指定的配置类初始化前加载 
@AutoConfigureOrder：数越小越先初始化
*/
 
@SpringBootApplication//@SpringBootConfiguration+@EnableAutoConfiguration+@ComponentScan
@Import({RootConfig.class,WebConfig.class,TaskExecutorConfig.class})//导入其他配置类
@EnableAspectJAutoProxy // 相当于spring.xml中的 <aop:aspectj-autoproxy/>和 application.properties中的spring.aop.auto=true
@EnableTransactionManagement // 开启注解事务
@EnableScheduling //通过@EnableScheduling注解开启对计划任务的支持
@ComponentScan(basePackages = {"com.endless"})
@MapperScan(basePackages = { "com.endless.dao" })
@ImportAutoConfiguration
public class HelloSpringMvc1Application   {
	public static void main(String[] args) {
		SpringApplication.run(HelloSpringMvc1Application.class, args);
	}
 
}
