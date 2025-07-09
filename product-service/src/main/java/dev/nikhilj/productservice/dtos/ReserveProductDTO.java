package dev.nikhilj.productservice.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ReserveProductDTO(
		@NotNull(message = "priceId should not be null")
		@Min(value = 0, message = "Please specify the price id")
		Long priceId,

		@NotNull(message = "quantityToReserve should not be null")
		@Min(value = 0, message = "You have to reserve at least 1 unit")
		int quantityToReserve
) {
}
