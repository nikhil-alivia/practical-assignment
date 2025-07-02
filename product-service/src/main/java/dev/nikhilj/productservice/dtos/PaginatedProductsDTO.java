package dev.nikhilj.productservice.dtos;

import java.util.List;

public record PaginatedProductsDTO(
		List<ProductDTO> products,
		int pageNo,
		int pageSize,
		long totalElements,
		int totalPages,
		boolean last
) {
}
