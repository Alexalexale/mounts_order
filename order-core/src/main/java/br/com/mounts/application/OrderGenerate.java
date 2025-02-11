package br.com.mounts.application;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface OrderGenerate {

  UUID getOrderIdentify();

  UUID getClientIdentify();

  LocalDateTime getDatOrder();

  List<? extends ItemOrderGenerate> getItems();
}
