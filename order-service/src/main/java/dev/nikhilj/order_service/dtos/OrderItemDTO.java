package dev.nikhilj.order_service.dtos;

import java.math.BigDecimal;

public record OrderItemDTO(
		Long id,
		ProductDTO product,
		int quantity,
		BigDecimal lineTotal
) {
}
