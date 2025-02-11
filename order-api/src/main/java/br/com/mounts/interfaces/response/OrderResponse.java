package br.com.mounts.interfaces.response;

import br.com.mounts.domain.Order;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record OrderResponse(
    UUID orderIdentify,
    UUID clientIdentify,
    BigDecimal totalAmount,
    LocalDateTime datOrder,
    int numItems)
    implements Serializable {

  public static OrderResponse ofOrder(final Order order) {
    return new OrderResponse(
        order.getOrderIdentify(),
        order.getClientIdentify(),
        order.getTotalAmount(),
        order.getDatOrder(),
        order.getItems().size());
  }
}
