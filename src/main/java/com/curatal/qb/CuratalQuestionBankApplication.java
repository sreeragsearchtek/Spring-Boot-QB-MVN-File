package com.curatal.qb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
//public class CuratalQuestionBankApplication{

	//Deploy Application in Stand-Alone Tomcat Server
public class CuratalQuestionBankApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(CuratalQuestionBankApplication.class, args);
	}

}
