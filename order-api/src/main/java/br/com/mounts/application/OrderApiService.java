package br.com.mounts.application;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import br.com.mounts.domain.OrderRepository;
import br.com.mounts.interfaces.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(makeFinal = true, level = PRIVATE)
@RequiredArgsConstructor(access = PACKAGE, onConstructor_ = @Autowired)
public class OrderApiService {

  OrderRepository orderRepository;

  public Page<OrderResponse> findPageOrders(int page, int size) {
    final var pageRequest = PageRequest.of(page, size);
    final var pageOrders = orderRepository.findAllByOrderByDatOrderDesc(pageRequest);
    return pageOrders.map(
        order ->
            new OrderResponse(
                order.getOrderIdentify(),
                order.getClientIdentify(),
                order.getTotalAmount(),
                order.getDatOrder(),
                order.getItems().size()));
  }
}
