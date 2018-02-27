package com.endless.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.endless.config.RootConfig;
 
@Configuration
@SpringBootApplication
@Import({RootConfig.class})
@EnableAspectJAutoProxy // 相当于 xml 中的 <aop:aspectj-autoproxy/>
@EnableTransactionManagement // 开启注解事务
@ComponentScan(basePackages = {"com.endless"})
@MapperScan(basePackages = { "com.endless.dao" })
@ImportAutoConfiguration
public class HelloSpringMvc1Application  {
	public static void main(String[] args) {
		SpringApplication.run(HelloSpringMvc1Application.class, args);
	}
}
