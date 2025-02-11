package br.com.mounts.domain;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.TWO;
import static java.math.RoundingMode.HALF_UP;
import static org.mockito.Mockito.verify;

import br.com.mounts.application.ItemOrderGenerate;
import br.com.mounts.application.OrderGenerate;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

  @Mock OrderRepository orderRepository;

  OrderService orderService;

  @BeforeEach
  void beforeEach() {
    orderService = new OrderService(orderRepository);
  }

  @Test
  void should_success_generate_order() {

    final var items =
        List.of(itemOrderGenerate(TEN, TWO.longValue()), itemOrderGenerate(ONE, TEN.longValue()));

    final var order = orderGenerate(items);

    ArgumentCaptor<Order> captor = ArgumentCaptor.forClass(Order.class);

    orderService.generateOrder(order);

    verify(orderRepository).save(captor.capture());

    Assertions.assertEquals(
        BigDecimal.valueOf(30).setScale(2, HALF_UP), captor.getValue().getTotalAmount());
  }

  private OrderGenerate orderGenerate(List<ItemOrderGenerate> items) {
    return new OrderGenerate() {
      @Override
      public UUID getOrderIdentify() {
        return UUID.randomUUID();
      }

      @Override
      public UUID getClientIdentify() {
        return UUID.randomUUID();
      }

      @Override
      public LocalDateTime getDatOrder() {
        return LocalDateTime.now();
      }

      @Override
      public List<? extends ItemOrderGenerate> getItems() {
        return items;
      }
    };
  }

  private ItemOrderGenerate itemOrderGenerate(BigDecimal amount, Long qtde) {
    return new ItemOrderGenerate() {

      @Override
      public UUID getItemIdentify() {
        return UUID.randomUUID();
      }

      @Override
      public BigDecimal getAmount() {
        return amount;
      }

      @Override
      public Long getQtde() {
        return qtde;
      }
    };
  }
}
