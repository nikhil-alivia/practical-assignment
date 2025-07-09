package dev.nikhilj.productservice.services;

import dev.nikhilj.productservice.entities.Price;
import dev.nikhilj.productservice.entities.Product;
import dev.nikhilj.productservice.repositories.PriceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@AllArgsConstructor
@Service
public class PriceService {

	private final PriceRepository priceRepository;

	@Transactional
	public Price create(BigDecimal amount, Product product) {
		Price price = new Price();
		price.setAmount(amount);
		price.setProduct(product);
		return priceRepository.save(price);
	}

	@Transactional
	public Price getOrCreate(BigDecimal amount, Product product) {
		return priceRepository.getPriceByAmountAndProductId(
				amount, product.getId()
		).orElseGet(() -> priceRepository.save(
				new Price(amount, product)
		));
	}

	@Transactional
	public void deletePricesByProduct(Product product) {
		priceRepository.deletePricesByProductId(product.getId());
	}

}
