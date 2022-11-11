package ru.ustinoff.commondtos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ustinoff.commondtos.event.OrderStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto {
    private Integer userId;
    private Integer productId;
    private Integer amount;
    private Integer orderId;
    private OrderStatus orderStatus;
}
