package br.com.mounts.interfaces.response;

import br.com.mounts.domain.ItemOrder;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

public record ItemOrderResponse(
    UUID orderIdentify, UUID itemIdentify, BigDecimal amount, Long qtde, BigDecimal totalAmount)
    implements Serializable {

  public static ItemOrderResponse ofOrder(final ItemOrder item) {
    return new ItemOrderResponse(
        item.getOrder().getOrderIdentify(),
        item.getItemIdentify(),
        item.getAmount(),
        item.getQtde(),
        item.getTotalAmount());
  }
}
