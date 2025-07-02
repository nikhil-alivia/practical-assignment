package dev.nikhilj.productservice.dtos;


import java.math.BigDecimal;

public record PriceDTO(
		Long id,
		BigDecimal amount,
		boolean isActive,
		Long productId
) {
}
