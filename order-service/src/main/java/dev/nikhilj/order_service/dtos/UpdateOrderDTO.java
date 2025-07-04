package dev.nikhilj.order_service.dtos;

import dev.nikhilj.order_service.enums.OrderStatus;

public record UpdateOrderDTO(
		OrderStatus status
) {
}
