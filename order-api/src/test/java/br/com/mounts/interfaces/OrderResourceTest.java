package br.com.mounts.interfaces;

import static br.com.mounts.infrascruture.OrderMediaType.APPLICATION_V1_PLUS_JSON_UTF8_VALUE;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_CLASS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.OracleContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
@Sql(scripts = "/test-data.sql", executionPhase = BEFORE_TEST_CLASS)
class OrderResourceTest {

  @Container
  private static final OracleContainer ORACLE_CONTAINER =
      new OracleContainer("gvenzl/oracle-xe:21-slim")
          .withDatabaseName("testdb")
          .withUsername("testuser")
          .withPassword("testpass");

  @Autowired private MockMvc mockMvc;

  @DynamicPropertySource
  static void setProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", ORACLE_CONTAINER::getJdbcUrl);
    registry.add("spring.datasource.username", ORACLE_CONTAINER::getUsername);
    registry.add("spring.datasource.password", ORACLE_CONTAINER::getPassword);
    registry.add("spring.jpa.hibernate.ddl-auto", () -> "update");
  }

  @Test
  @DisplayName("Deve retornar todos os pedidos paginados")
  void testGetAllOrder() throws Exception {
    mockMvc
        .perform(get("/api/orders?page=0&size=5").contentType(APPLICATION_V1_PLUS_JSON_UTF8_VALUE))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.page.totalElements").value(8))
        .andExpect(MockMvcResultMatchers.jsonPath("$.page.totalPages").value(2));
  }

  @Test
  @DisplayName("Deve retornar uma pagina com 2 elementos")
  void testGetOrderSearch() throws Exception {
    mockMvc
        .perform(
            get("/api/orders/search?page=0&size=3&minTotalAmount=160&maxTotalAmount=240")
                .contentType(APPLICATION_V1_PLUS_JSON_UTF8_VALUE))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.page.totalElements").value(2))
        .andExpect(MockMvcResultMatchers.jsonPath("$.page.totalPages").value(1));
  }

  @Test
  @DisplayName("Deve retornar 404 quando o pedido não existe")
  void testGetOrderNotFound() throws Exception {
    mockMvc
        .perform(
            get("/api/orders/{orderIdentify}", UUID.randomUUID().toString())
                .contentType(APPLICATION_V1_PLUS_JSON_UTF8_VALUE))
        .andExpect(status().isNotFound())
        .andExpect(MockMvcResultMatchers.jsonPath("$.detail").isNotEmpty());
  }

  @Test
  @DisplayName("Deve retornar todos os items do pedido paginado")
  void testGetItemsOfOrder() throws Exception {
    mockMvc
        .perform(
            get("/api/orders/a1b2c3d5-e5f6-7890-1234-56789abcdef0/items?page=0&size=10")
                .contentType(APPLICATION_V1_PLUS_JSON_UTF8_VALUE))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.page.totalElements").value(24))
        .andExpect(MockMvcResultMatchers.jsonPath("$.page.totalPages").value(3));
  }
}
