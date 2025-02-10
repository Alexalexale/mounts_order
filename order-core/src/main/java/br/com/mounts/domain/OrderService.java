package br.com.mounts.domain;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import br.com.mounts.application.OrderGenerate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(makeFinal = true, level = PRIVATE)
@RequiredArgsConstructor(access = PACKAGE, onConstructor_ = @Autowired)
public class OrderService {

  OrderRepository orderRepository;

  @Transactional
  public void generateOrder(final OrderGenerate orderRequest) {
    final var order =
        new Order(
            orderRequest.getOrderIdentify(),
            orderRequest.getClientIdentify(),
            orderRequest.getDatOrder());

    orderRequest
        .getItems()
        .forEach(
            item -> {
              final var itemOrder =
                  new ItemOrder(item.getItemIdentify(), order, item.getAmount(), item.getQtde());
              order.addItem(itemOrder);
            });

    order.calculateTotalAmount();

    orderRepository.save(order);
  }
}
