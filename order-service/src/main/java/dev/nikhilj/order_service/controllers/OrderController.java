package dev.nikhilj.order_service.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class OrderController {

	@PostMapping
	public ResponseEntity<Void> createProduct() {
		// Order Service . createProduct(dto);
		return ResponseEntity.ok(null);
	}

	@GetMapping
	public ResponseEntity<Void> getOrders() {
		// Order Service .get Orders(User or admin)
		return ResponseEntity.ok(null);
	}

	@GetMapping("/{orderId}")
	public ResponseEntity<Void> getOrder(@PathVariable("orderId") Long orderId) {
		// Order Service . get Order By Id
		return ResponseEntity.ok(null);
	}

	@PutMapping("/{orderId}")
	public ResponseEntity<Void> updateOrder(@PathVariable("orderId") Long orderId) {
		// Order Service . update Order by Id and dto
		return ResponseEntity.ok(null);
	}

}
