package br.com.mounts.infrascruture.exception;

import java.util.UUID;

public class OrderAlreadyProcessedException extends RuntimeException {

  private static final long serialVersionUID = 3522918322401998261L;

  private static final String ERROR_MSG = "Order already processed [orderIdentify=%s]";

  public OrderAlreadyProcessedException(final UUID orderIdentify) {
    super(String.format(ERROR_MSG, orderIdentify));
  }
}
