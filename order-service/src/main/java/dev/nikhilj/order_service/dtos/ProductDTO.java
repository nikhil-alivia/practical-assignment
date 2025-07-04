package dev.nikhilj.order_service.dtos;

import java.math.BigDecimal;

public record ProductDTO(
		Long productId,
		String name,
		String description,
		BigDecimal price,
		Long priceId
) {
}
