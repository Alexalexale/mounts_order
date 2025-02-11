package br.com.mounts.infrascruture.exception;

import java.util.UUID;

public class OrderNotFoundException extends RuntimeException {

  private static final long serialVersionUID = 3522918322401998261L;

  private static final String ERROR_MSG = "Order not found [orderIdentify=%s]";

  public OrderNotFoundException(final UUID orderIdentify) {
    super(String.format(ERROR_MSG, orderIdentify));
  }
}
