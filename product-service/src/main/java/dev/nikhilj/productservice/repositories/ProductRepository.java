package dev.nikhilj.productservice.repositories;

import dev.nikhilj.productservice.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
	public Optional<Product> getProductById(Long productId);
}
