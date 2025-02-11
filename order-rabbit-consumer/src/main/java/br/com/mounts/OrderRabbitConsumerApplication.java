package br.com.mounts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "br.com.mounts.domain")
public class OrderRabbitConsumerApplication {

  public static void main(final String[] args) {
    SpringApplication.run(OrderRabbitConsumerApplication.class, args);
  }
}
