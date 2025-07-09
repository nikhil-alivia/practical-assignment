package dev.nikhilj.order_service.dtos;

import dev.nikhilj.order_service.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateOrderDTO(
		@NotNull(message = "status should not be empty")
		OrderStatus status
) {
}
