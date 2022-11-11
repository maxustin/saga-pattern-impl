package ru.ustinoff.orderservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.ustinoff.commondtos.dto.OrderRequestDto;
import ru.ustinoff.orderservice.entity.PurchaseOrder;
import ru.ustinoff.orderservice.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public PurchaseOrder createOrder(@RequestBody OrderRequestDto orderRequestDto) {
        return orderService.createOrder(orderRequestDto);
    }

    @GetMapping
    public List<PurchaseOrder> getOrders() {
        return orderService.getAllOrders();
    }
}
