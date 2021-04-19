package com.lenovo.wy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("com.lenovo.wy")
public class Wy77t1Application {

	public static void main(String[] args) {
//		SpringApplication.run(Wy77t1Application.class, args);
		ConfigurableApplicationContext applicationContext = SpringApplication.run(Wy77t1Application.class, args);
        String userName = applicationContext.getEnvironment().getProperty("user.name");
        String userAge = applicationContext.getEnvironment().getProperty("user.age");
        String daniel = applicationContext.getEnvironment().getProperty("daniel");
        System.err.println(daniel+"@@@@@@@@@@@@@@@@@@@@@@@@@@user name :"+userName+"; age: "+userAge);
	}

}
