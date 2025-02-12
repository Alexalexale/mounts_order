package br.com.mounts.application;

import static br.com.mounts.application.OrderGenerateRabbitConfig.EXCHANGE;
import static br.com.mounts.application.OrderGenerateRabbitConfig.MAIN_QUEUE;
import static org.junit.jupiter.api.Assertions.assertEquals;

import br.com.mounts.domain.OrderRepository;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.OracleContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OrderGenerateConsumerTest {

  private static final String VHOST = "/order";

  private static final RabbitMQContainer RABBITMQ_CONTAINER =
      new RabbitMQContainer(DockerImageName.parse("rabbitmq:3.11-management"))
          .withExposedPorts(5672, 15672)
          .withVhost(VHOST)
          .withUser("testuser", "testpass")
          .withPermission(VHOST, "testuser", ".*", ".*", ".*");

  private static final OracleContainer ORACLE_CONTAINER =
      new OracleContainer("gvenzl/oracle-xe:21-slim")
          .withDatabaseName("testdb")
          .withUsername("testuser")
          .withPassword("testpass");

  static {
    RABBITMQ_CONTAINER.start();
    ORACLE_CONTAINER.start();
  }

  @DynamicPropertySource
  static void setProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", ORACLE_CONTAINER::getJdbcUrl);
    registry.add("spring.datasource.username", ORACLE_CONTAINER::getUsername);
    registry.add("spring.datasource.password", ORACLE_CONTAINER::getPassword);
    registry.add("spring.jpa.hibernate.ddl-auto", () -> "update");
    registry.add("spring.rabbitmq.host", RABBITMQ_CONTAINER::getHost);
    registry.add("spring.rabbitmq.port", () -> RABBITMQ_CONTAINER.getMappedPort(5672).toString());
    registry.add("spring.rabbitmq.username", () -> "testuser");
    registry.add("spring.rabbitmq.password", () -> "testpass");
  }

  @Autowired private RabbitTemplate rabbitTemplate;

  @Autowired private OrderRepository orderRepository;

  @Test
  void deveProcessarMensagemEInserirNoBanco() {
    UUID orderIdentify = UUID.randomUUID();

    final var message =
        new OrderGenerateMessage(
            orderIdentify,
            UUID.randomUUID(),
            LocalDateTime.now(),
            List.of(
                new ItemOrderGenerateMessage(UUID.randomUUID(), BigDecimal.valueOf(2.3), 5L),
                new ItemOrderGenerateMessage(UUID.randomUUID(), BigDecimal.valueOf(6.7), 3L)));

    rabbitTemplate.convertAndSend(EXCHANGE, MAIN_QUEUE, message);

    Awaitility.await()
        .atMost(Duration.ofSeconds(5))
        .until(() -> orderRepository.findByOrderIdentify(orderIdentify).isPresent());

    final var order = orderRepository.findByOrderIdentify(orderIdentify).orElseThrow();

    assertEquals(BigDecimal.valueOf(31, 6), order.getTotalAmount());
  }
}
