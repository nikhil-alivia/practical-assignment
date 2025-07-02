package dev.nikhilj.productservice.repositories;

import dev.nikhilj.productservice.entities.Price;
import dev.nikhilj.productservice.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.Optional;

public interface PriceRepository extends JpaRepository<Price, Long> {
	Optional<Price> getPriceByAmountAndProductId(BigDecimal amount, Long productId);

	void deletePricesByProductId(Long productId);
}
