package io.kimmking.dubbo.demo.consumer;

import io.kimmking.dubbo.demo.api.AccountService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;

@SpringBootApplication
public class DubboClientApplication {

//	@DubboReference(version = "1.0.0") //, url = "dubbo://127.0.0.1:12345")
//	private UserService userService;
//
//	@DubboReference(version = "1.0.0") //, url = "dubbo://127.0.0.1:12345")
//	private OrderService orderService;

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:dubbo-client.xml");
		AccountService accountService = context.getBean(AccountService.class);
		accountService.transfer(2L,1L,new BigDecimal(30));

//		SpringApplication.run(DubboClientApplication.class).close();

		// UserService service = new xxx();
		// service.findById

//		UserService userService = Rpcfx.create(UserService.class, "http://localhost:8080/");
//		User user = userService.findById(1);
//		System.out.println("find user id=1 from server: " + user.getName());
//
//		OrderService orderService = Rpcfx.create(OrderService.class, "http://localhost:8080/");
//		Order order = orderService.findOrderById(1992129);
//		System.out.println(String.format("find order name=%s, amount=%f",order.getName(),order.getAmount()));

	}

//	@Bean
//	public ApplicationRunner runner() {
//		return args -> {
//			User user = userService.findById(1);
//			System.out.println("find user id=1 from server: " + user.getName());
//			Order order = orderService.findOrderById(1992129);
//			System.out.println(String.format("find order name=%s, amount=%f",order.getName(),order.getAmount()));
//		};
//	}

}
