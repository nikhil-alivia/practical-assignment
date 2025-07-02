package dev.nikhilj.productservice.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProductDTO(
		@JsonProperty(access = JsonProperty.Access.READ_ONLY)
		Long id,
		@NotEmpty
		@Size(min = 3, message = "Product name should have at least 3 characters")
		String name,
		@NotEmpty
		String description,
		@NotNull
		@Min(value = 0, message = "Stock quantity must be non negative")
		Long stockQuantity,
		@NotNull
		@Min(value = 0, message = "Price should be non negative")
		BigDecimal price,
		@JsonProperty(access = JsonProperty.Access.READ_ONLY)
		Long price_id
) {
}
