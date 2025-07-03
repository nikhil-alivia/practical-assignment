package dev.nikhilj.productservice;

import dev.nikhilj.productservice.dtos.PaginatedProductsDTO;
import dev.nikhilj.productservice.dtos.ProductDTO;
import dev.nikhilj.productservice.services.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {

	private ProductService productService;

	@GetMapping
	public ResponseEntity<PaginatedProductsDTO> productListing(
			@RequestParam(value = "pageNo", defaultValue = "0", required = false)
			int pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false)
			int pageSize,
			@RequestParam(value = "search", defaultValue = "", required = false)
			String searchString
	) {
		return ResponseEntity.ok(productService.getProductsPaginated(pageNo, pageSize, searchString));
	}

	@PostMapping
	public ResponseEntity<ProductDTO> createProduct(@RequestBody @Valid ProductDTO productDTO) {
		ProductDTO product = productService.createProduct(productDTO);
		return new ResponseEntity<>(
				product, HttpStatus.CREATED
		);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProductDTO> getProduct(@PathVariable("id") Long productId) {
		ProductDTO productDTO = productService.getProductById(productId);
		return ResponseEntity.ok(productDTO);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ProductDTO> updateProduct(@PathVariable("id") Long productId, @RequestBody @Valid ProductDTO productDTO) {
		ProductDTO product = productService.updateProduct(productId, productDTO);
		return ResponseEntity.ok(product);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long productId) {
		productService.deleteProductById(productId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
