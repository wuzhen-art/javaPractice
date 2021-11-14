package com.geektime.week8.sharding_sphere;

import com.geektime.week8.sharding_sphere.xa_worker2.batchXAService.XAService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SharingSphereApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(SharingSphereApplication.class, args);

		XAService orderService = run.getBean(XAService.class);
		orderService.process();
	}

}
