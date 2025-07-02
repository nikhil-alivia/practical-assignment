package dev.nikhilj.productservice;

import dev.nikhilj.productservice.dtos.ProductDTO;
import jakarta.validation.Valid;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	@GetMapping
	public ResponseEntity<List<ProductDTO>> productListing(
			@Param("id") Long productId
	) {
		return ResponseEntity.ok(new ArrayList<ProductDTO>());
	}

	@PostMapping
	public ResponseEntity<ProductDTO> createProduct(@RequestBody @Valid ProductDTO productDTO) {
		return new ResponseEntity<>(
				productDTO, HttpStatus.CREATED
		);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Void> getProduct(@Param("id") Long productId) {
		return ResponseEntity.ok(null);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ProductDTO> updateProduct(@RequestBody @Valid ProductDTO productDTO) {
		return ResponseEntity.ok(productDTO);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProduct(@Param("id") Long productId) {
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
