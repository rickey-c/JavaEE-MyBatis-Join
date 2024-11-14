package cn.edu.xmu.javaee.productdemoaop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"cn.edu.xmu.javaee.core",
		"cn.edu.xmu.javaee.productdemoaop"})
@MapperScan("cn.edu.xmu.javaee.productdemoaop.mapper")
@EntityScan("cn.edu.xmu.javaee.productdemoaop.mapper.jpa.entity")
@EnableJpaRepositories("cn.edu.xmu.javaee.productdemoaop.repository")
public class ProductDemoAOPApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductDemoAOPApplication.class, args);
	}

}
