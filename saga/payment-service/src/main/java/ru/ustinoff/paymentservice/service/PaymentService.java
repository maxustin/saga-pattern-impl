package ru.ustinoff.paymentservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ustinoff.commondtos.dto.OrderRequestDto;
import ru.ustinoff.commondtos.dto.PaymentRequestDto;
import ru.ustinoff.commondtos.event.OrderEvent;
import ru.ustinoff.commondtos.event.PaymentEvent;
import ru.ustinoff.commondtos.event.PaymentStatus;
import ru.ustinoff.paymentservice.entity.UserBalance;
import ru.ustinoff.paymentservice.entity.UserTransaction;
import ru.ustinoff.paymentservice.repository.UserBalanceRepository;
import ru.ustinoff.paymentservice.repository.UserTransactionRepository;

import javax.annotation.PostConstruct;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PaymentService {
    @Autowired
    private UserBalanceRepository userBalanceRepository;
    @Autowired
    private UserTransactionRepository userTransactionRepository;

    @PostConstruct
    public void initUserBalanceInDB() {
        userBalanceRepository.saveAll(Stream.of(
                new UserBalance(102, 3000),
                new UserBalance(103, 4200),
                new UserBalance(104, 20000),
                new UserBalance(105, 999)
        ).collect(Collectors.toList()));
    }

    @Transactional
    public PaymentEvent newOrderEvent(OrderEvent orderEvent) {
        OrderRequestDto orderRequestDto = orderEvent.getOrderRequestDto();
        PaymentRequestDto paymentRequestDto = new PaymentRequestDto(
                orderRequestDto.getOrderId(),
                orderRequestDto.getUserId(),
                orderRequestDto.getAmount()
        );
        return userBalanceRepository.findById(orderRequestDto.getOrderId()).filter(ub -> ub.getPrice() > orderRequestDto.getAmount())
                .map(ub -> {
                    ub.setPrice(ub.getPrice() - orderRequestDto.getAmount());
                    userTransactionRepository.save(new UserTransaction(
                            orderRequestDto.getOrderId(),
                            orderRequestDto.getUserId(),
                            orderRequestDto.getAmount()));
                    return new PaymentEvent(paymentRequestDto, PaymentStatus.PAYMENT_COMPLETED);
                }).orElse(new PaymentEvent(paymentRequestDto, PaymentStatus.PAYMENT_FAILED));
    }

    @Transactional
    public void cancelOrderEvent(OrderEvent orderEvent) {
        userTransactionRepository.findById(orderEvent.getOrderRequestDto().getOrderId()).ifPresent(ut -> {
            userTransactionRepository.delete(ut);
            userTransactionRepository.findById(ut.getUserId()).ifPresent(ub -> ub.setPrice(ub.getPrice() + ut.getPrice()));
        });
    }
}
