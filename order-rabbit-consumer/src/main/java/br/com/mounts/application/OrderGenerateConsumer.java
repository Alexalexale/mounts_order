package br.com.mounts.application;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import br.com.mounts.domain.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = PRIVATE)
@RequiredArgsConstructor(access = PACKAGE, onConstructor_ = @Autowired)
public class OrderGenerateConsumer {

  OrderService orderService;

  @RabbitListener(queues = OrderGenerateRabbitConfig.MAIN_QUEUE)
  public void processarMensagem(OrderGenerateMessage mensagem) {
    orderService.generateOrder(mensagem);
  }
}
