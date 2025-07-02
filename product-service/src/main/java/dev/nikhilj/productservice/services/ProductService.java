package dev.nikhilj.productservice.services;

import dev.nikhilj.productservice.dtos.ProductDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class ProductService {

	public List<ProductDTO> getProductsPaginated() {
		return new ArrayList<ProductDTO>();
	}

	public ProductDTO createProduct(ProductDTO productDTO) {
		return productDTO;
	}

	public ProductDTO getProductById(Long productId) {
		return new ProductDTO(1L, "New", "Product", 1L, new BigDecimal(10), 1L);
	}

	public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {
		return productDTO;
	}

	public void deleteProductById(Long productId) {
	}

}
