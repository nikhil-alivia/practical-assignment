package dev.nikhilj.common.http.configuration;


import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

	@Bean
	@LoadBalanced
	public WebClient.Builder webClientBuilder() {
		return WebClient.builder()
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
				.filter(addAuthorizationHeaderFilter());
	}

	private ExchangeFilterFunction addAuthorizationHeaderFilter() {
		return (clientRequest, next) -> {
			ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
			String authorizationHeader = null;
			if (requestAttributes != null) {
				authorizationHeader = requestAttributes.getRequest()
						.getHeader(HttpHeaders.AUTHORIZATION);
				if (authorizationHeader != null) {
					ClientRequest filteredRequest = ClientRequest.from(clientRequest)
							.header(HttpHeaders.AUTHORIZATION, authorizationHeader)
							.build();
					return next.exchange(filteredRequest);
				}
			}
			return next.exchange(clientRequest);
		};
	}

}
