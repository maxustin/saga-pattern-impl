package ru.ustinoff.orderservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.ustinoff.commondtos.event.PaymentEvent;

import java.util.function.Consumer;

@Configuration
public class EventConsumerConfig {
    @Autowired
    private OrderStatusUpdateHandler handler;

    @Bean
    public Consumer<PaymentEvent> paymentEventConsumer() {
        return (payment) -> handler.updateOrder(payment.getPaymentRequestDto().getProductId(), po -> {
            po.setPaymentStatus(payment.getPaymentStatus());
        });
    }
}
