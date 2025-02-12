package br.com.mounts.interfaces;

import static br.com.mounts.infrascruture.OrderMediaType.APPLICATION_V1_PLUS_JSON_UTF8_VALUE;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import br.com.mounts.application.OrderApiService;
import br.com.mounts.infrascruture.UUIDFacilitator;
import br.com.mounts.interfaces.request.OrderFilterDTO;
import br.com.mounts.interfaces.response.ItemOrderResponse;
import br.com.mounts.interfaces.response.OrderResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
    path = "/api/orders",
    produces = APPLICATION_V1_PLUS_JSON_UTF8_VALUE,
    consumes = APPLICATION_V1_PLUS_JSON_UTF8_VALUE)
@FieldDefaults(makeFinal = true, level = PRIVATE)
@RequiredArgsConstructor(access = PACKAGE, onConstructor_ = @Autowired)
@Tag(name = "OrderResource", description = "Endpoints para realizar consultas dos pedidos")
public class OrderResource {

  OrderApiService orderService;

  @Operation(description = "Retorna uma lista de pedidos paginada.")
  @GetMapping
  public ResponseEntity<Page<OrderResponse>> findPageOrders(Pageable pageable) {
    return ResponseEntity.ok(orderService.findPageOrders(pageable));
  }

  @GetMapping("/search")
  public ResponseEntity<Page<OrderResponse>> searchOrders(
      @ModelAttribute OrderFilterDTO filterDTO, Pageable pageable) {
    return ResponseEntity.ok(orderService.searchOrders(filterDTO, pageable));
  }

  @GetMapping("/{orderIdentify}")
  public ResponseEntity<OrderResponse> findOrder(
      @PathVariable final UUIDFacilitator orderIdentify) {
    return ResponseEntity.ok(orderService.findOrder(orderIdentify));
  }

  @GetMapping("/{orderIdentify}/items")
  public ResponseEntity<Page<ItemOrderResponse>> findPageItemsOrder(
      @PathVariable final UUIDFacilitator orderIdentify, Pageable pageable) {
    return ResponseEntity.ok(orderService.findPageItemsOrder(orderIdentify, pageable));
  }
}
