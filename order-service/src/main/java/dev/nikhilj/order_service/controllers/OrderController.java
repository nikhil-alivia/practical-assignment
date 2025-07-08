package dev.nikhilj.order_service.controllers;

import dev.nikhilj.common.security.UserPrincipal;
import dev.nikhilj.order_service.dtos.CreateOrderDTO;
import dev.nikhilj.order_service.dtos.OrderDTO;
import dev.nikhilj.order_service.dtos.PaginatedOrderDTO;
import dev.nikhilj.order_service.dtos.UpdateOrderDTO;
import dev.nikhilj.order_service.services.OrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class OrderController {

	private final OrderService orderService;

	@PostMapping
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<OrderDTO> createProduct(
			@RequestBody @Valid CreateOrderDTO createOrderDTO
	) {
		return ResponseEntity.ok(orderService.createOrder(createOrderDTO));
	}

	@GetMapping
	public ResponseEntity<PaginatedOrderDTO> getOrders(
			@RequestParam(value = "pageNo", defaultValue = "0", required = false)
			int pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false)
			int pageSize
	) {
		return ResponseEntity.ok(orderService.getAllOrders(pageNo, pageSize));
	}

	@GetMapping("/{orderId}")
	@PreAuthorize("@orderService.isOrderOwner(authentication.principal.id, #orderId) OR hasRole('ADMIN')")
	public ResponseEntity<OrderDTO> getOrder(@PathVariable("orderId") Long orderId) {
		return ResponseEntity.ok(orderService.getOrder(orderId));
	}

	@PutMapping("/{orderId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<OrderDTO> updateOrder(
			@PathVariable("orderId") Long orderId,
			@RequestBody @Valid UpdateOrderDTO updateOrderDTO
	) {
		// Order Service . update Order by Id and dto
		return ResponseEntity.ok(orderService.updateOrder(orderId, updateOrderDTO));
	}

}
