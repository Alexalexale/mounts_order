package br.com.mounts.application;

import java.math.BigDecimal;
import java.util.UUID;

public interface ItemOrderGenerate {

  UUID getItemIdentify();

  BigDecimal getAmount();

  Long getQtde();
}
