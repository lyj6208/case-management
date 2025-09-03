package com.testing_company.case_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class CaseManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(CaseManagementApplication.class, args);
	}

}
