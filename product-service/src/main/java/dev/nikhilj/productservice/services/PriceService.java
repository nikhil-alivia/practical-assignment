package dev.nikhilj.productservice.services;

import dev.nikhilj.common.security.exceptions.APIException;
import dev.nikhilj.productservice.entities.Price;
import dev.nikhilj.productservice.entities.Product;
import dev.nikhilj.productservice.repositories.PriceRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@AllArgsConstructor
@Service
public class PriceService {

	private final PriceRepository priceRepository;

	public Price create(BigDecimal amount, Product product) {
		Price price = new Price();
		price.setAmount(amount);
		price.setProduct(product);
		return priceRepository.save(price);
	}

	public Price getOrCreate(BigDecimal amount, Product product) {
		return priceRepository.getPriceByAmountAndProductId(
				amount, product.getId()
		).orElseGet(() -> priceRepository.save(
				new Price(amount, product)
		));
	}

}
