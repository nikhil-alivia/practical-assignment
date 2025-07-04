package dev.nikhilj.order_service.dtos;

import java.util.List;

public record PaginatedOrderDTO(
		List<OrderDTO> orders,
		int pageNo,
		int pageSize,
		long totalElements,
		int totalPages,
		boolean last
) {
}
