package dev.nikhilj.productservice.services;

import dev.nikhilj.common.security.exceptions.APIException;
import dev.nikhilj.productservice.dtos.ProductDTO;
import dev.nikhilj.productservice.entities.Price;
import dev.nikhilj.productservice.entities.Product;
import dev.nikhilj.productservice.repositories.PriceRepository;
import dev.nikhilj.productservice.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class ProductService {

	private final ProductRepository productRepository;
	private final PriceService priceService;

	public List<ProductDTO> getProductsPaginated() {
		return new ArrayList<ProductDTO>();
	}

	public ProductDTO createProduct(ProductDTO productDTO) {
		Product product = new Product();
		product.setName(productDTO.name());
		product.setDescription(productDTO.description());
		product.setStockQuantity(productDTO.stockQuantity());
		product = productRepository.save(product);

		Price price = priceService.create(productDTO.price(), product);

		product.setActivePrice(price);
		product = productRepository.save(product);

		return mapToDTO(product);
	}

	public ProductDTO getProductById(Long productId) {
		Product product = productRepository.getProductById(productId)
				.orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "Product not found with id " + productId));
		return mapToDTO(product);
	}

	public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {
		return productDTO;
	}

	public void deleteProductById(Long productId) {
	}

	private ProductDTO mapToDTO(Product product) {
		return new ProductDTO(
				product.getId(),
				product.getName(),
				product.getDescription(),
				product.getStockQuantity(),
				product.getActivePrice().getAmount(),
				product.getActivePrice().getId()
		);
	}

}
