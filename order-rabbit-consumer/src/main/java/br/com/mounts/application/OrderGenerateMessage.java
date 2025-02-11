package br.com.mounts.application;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OrderGenerateMessage(
    UUID orderIdentify,
    UUID clientIdentify,
    LocalDateTime datOrder,
    List<ItemOrderGenerateMessage> items)
    implements OrderGenerate {

  @Override
  public UUID getOrderIdentify() {
    return orderIdentify;
  }

  @Override
  public UUID getClientIdentify() {
    return clientIdentify;
  }

  @Override
  public LocalDateTime getDatOrder() {
    return datOrder;
  }

  @Override
  public List<? extends ItemOrderGenerate> getItems() {
    return items;
  }
}
