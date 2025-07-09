package dev.nikhilj.order_service.services;

import dev.nikhilj.order_service.dtos.ProductDTO;
import dev.nikhilj.order_service.dtos.StockReservationRequestDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Service
public class ProductClientService {
	private final WebClient webClient;

	ProductClientService(WebClient.Builder webClientBuilder) {
		webClient = webClientBuilder.baseUrl("http://product-service").build();
	}

	public ProductDTO reserveStock(
			Long priceId, int productQuantity
	) throws WebClientResponseException {
		return webClient
				.post()
				.uri("/api/products/reserve-stock")
				.body(
						Mono.just(new StockReservationRequestDTO(priceId, productQuantity)),
						StockReservationRequestDTO.class
				).retrieve()
				.bodyToMono(ProductDTO.class)
				.block();
	}

}
