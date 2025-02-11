package br.com.mounts.application;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import br.com.mounts.domain.ItemOrderRepository;
import br.com.mounts.domain.OrderRepository;
import br.com.mounts.domain.OrderSpecification;
import br.com.mounts.infrascruture.UUIDFacilitator;
import br.com.mounts.infrascruture.exception.OrderNotFoundException;
import br.com.mounts.interfaces.request.OrderFilterDTO;
import br.com.mounts.interfaces.response.ItemOrderResponse;
import br.com.mounts.interfaces.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(makeFinal = true, level = PRIVATE)
@RequiredArgsConstructor(access = PACKAGE, onConstructor_ = @Autowired)
public class OrderApiService {

  OrderRepository orderRepository;
  ItemOrderRepository itemOrderRepository;

  public Page<OrderResponse> findPageOrders(Pageable pageable) {
    final var pageOrders = orderRepository.findAllByOrderByDatOrderDesc(pageable);
    return pageOrders.map(OrderResponse::ofOrder);
  }

  public Page<OrderResponse> searchOrders(OrderFilterDTO filterDTO, Pageable pageable) {
    final var spec =
        OrderSpecification.filterOrders(
            filterDTO.clientIdentify(),
            filterDTO.datOrder(),
            filterDTO.minTotalAmount(),
            filterDTO.maxTotalAmount());

    final var pageOrders = orderRepository.findAll(spec, pageable);

    return pageOrders.map(OrderResponse::ofOrder);
  }

  public Page<ItemOrderResponse> findPageItemsOrder(
      UUIDFacilitator orderIdentify, Pageable pageable) {
    final var order =
        orderRepository
            .findByOrderIdentify(orderIdentify.toUUID())
            .orElseThrow(() -> new OrderNotFoundException(orderIdentify.toUUID()));

    final var pageItems = itemOrderRepository.findAllPageByOrder(order, pageable);

    return pageItems.map(ItemOrderResponse::ofOrder);
  }
}
