package ru.ustinoff.orderservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;
import ru.ustinoff.commondtos.dto.OrderRequestDto;
import ru.ustinoff.commondtos.event.OrderEvent;
import ru.ustinoff.commondtos.event.OrderStatus;

@Service
public class OrderStatusPublisher {
    @Autowired
    private Sinks.Many<OrderEvent> orderSinks;

    public void publishOrderEvent(OrderRequestDto orderRequestDto, OrderStatus orderStatus) {
        OrderEvent orderEvent = new OrderEvent(orderRequestDto, orderStatus);
        orderSinks.tryEmitNext(orderEvent);
    }
}
