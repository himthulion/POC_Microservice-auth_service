package com.poc.auth;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
//import org.springframework.cloud.client.loadbalancer.LoadBalanced;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.client.RestTemplate;

import com.poc.auth.dao.UserDao;
import com.poc.auth.entity.User;

@SpringBootApplication
//@EnableEurekaClient
@ComponentScan(basePackages = { "com.poc.base", "com.poc.auth" }, excludeFilters = {
		@ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.poc.base.test.domain.*") })
public class AuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}

	@Bean
	//@LoadBalanced
	public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
		return restTemplateBuilder.build();
	}

	@Bean
	public ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames("classpath:authMessage","classpath:baseMessage");
		return messageSource;
	}

	@Bean
	public CommandLineRunner initialUserData(UserDao userDao) {
		return (args) -> {
			User user = new User("sakchai.pr@yipintsoi.com", "Sakchai Promsawat", "password", "ADMIN");
			userDao.save(user);
			user = new User("akkarapol.wu@yipintsoi.com", "Akkrapol Wuttikrangkraipol", "password", "EMP");
			userDao.save(user);
			user = new User("nattapol.ka@yipintsoi.com", "Nattapol Kapoldee", "password", "EMP");
			userDao.save(user);
			user = new User("60500000001", "Sakchai Promsawat", "password", "STD");
			userDao.save(user);
			user = new User("60500000002", "Akkrapol Wuttikrangkraipol", "password", "STD");
			userDao.save(user);
			user = new User("60500000003", "Nattapol Kapoldee", "password", "STD");
			userDao.save(user);
		};
	}

}
