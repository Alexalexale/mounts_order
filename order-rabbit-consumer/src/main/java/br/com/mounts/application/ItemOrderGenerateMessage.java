package br.com.mounts.application;

import java.math.BigDecimal;
import java.util.UUID;

public record ItemOrderGenerateMessage(UUID itemIdentify, BigDecimal amount, Long qtde)
    implements ItemOrderGenerate {

  @Override
  public UUID getItemIdentify() {
    return itemIdentify;
  }

  @Override
  public BigDecimal getAmount() {
    return amount;
  }

  @Override
  public Long getQtde() {
    return qtde;
  }
}
