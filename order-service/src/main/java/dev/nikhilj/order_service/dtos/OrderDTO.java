package dev.nikhilj.order_service.dtos;

import dev.nikhilj.order_service.enums.OrderStatus;

import java.math.BigDecimal;
import java.util.Set;

public record OrderDTO(
		Long id,
		UserDTO user,
		OrderStatus status,
		BigDecimal total,
		Set<OrderItemDTO> orderItems
) {
}
