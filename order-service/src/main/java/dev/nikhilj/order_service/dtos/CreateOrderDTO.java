package dev.nikhilj.order_service.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateOrderDTO(
		@NotNull
		Long user_id,
		@NotNull
		@NotEmpty
		List<CreateOrderItemDTO> order_items
) {
}
