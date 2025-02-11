package br.com.mounts.application;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderGenerateRabbitConfig {

  public static final String MAIN_QUEUE = "mounts.order.generate";
  public static final String DLQ_QUEUE = "mounts.order.generate.dlq";

  public static final String EXCHANGE = "mounts.order.generate_exchange";
  public static final String DLQ_EXCHANGE = "mounts.order.generate_exchange.dlq";

  public static final String ROUTING_KEY = "mounts.order.generate_routing_key";

  @Bean
  public Queue mainQueue() {
    return QueueBuilder.durable(MAIN_QUEUE)
        .deadLetterExchange(DLQ_EXCHANGE)
        .deadLetterRoutingKey(DLQ_QUEUE)
        .build();
  }

  @Bean
  public Queue dlqQueue() {
    return QueueBuilder.durable(DLQ_QUEUE).build();
  }

  @Bean
  public DirectExchange mainExchange() {
    return new DirectExchange(EXCHANGE);
  }

  @Bean
  public DirectExchange dlxExchange() {
    return new DirectExchange(DLQ_EXCHANGE);
  }

  @Bean
  public Binding bindingMainQueue() {
    return BindingBuilder.bind(mainQueue()).to(mainExchange()).with(MAIN_QUEUE);
  }

  @Bean
  public Binding bindingDLQ() {
    return BindingBuilder.bind(dlqQueue()).to(dlxExchange()).with(DLQ_QUEUE);
  }

  @Bean
  public MessageConverter jsonMessageConverter() {
    return new Jackson2JsonMessageConverter();
  }
}
