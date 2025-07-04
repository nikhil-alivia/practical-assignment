package dev.nikhilj.order_service.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CreateOrderItemDTO(
		@NotNull
		Long price_id,
		@NotNull
		int quantity
) {
}
