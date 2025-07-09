package dev.nikhilj.order_service.services;

import dev.nikhilj.common.security.UserPrincipal;
import dev.nikhilj.common.exceptions.APIException;
import dev.nikhilj.order_service.dtos.*;
import dev.nikhilj.order_service.entities.Order;
import dev.nikhilj.order_service.entities.OrderItem;
import dev.nikhilj.order_service.enums.OrderStatus;
import dev.nikhilj.order_service.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class OrderService {
	private final OrderRepository orderRepository;
	private final ProductClientService productService;


	@Transactional
	public OrderDTO createOrder(CreateOrderDTO createOrderDTO) {
		Order order = new Order();
		order.setUserId(createOrderDTO.user_id());
		order.setStatus(OrderStatus.PROCESSING);
		Set<OrderItem> orderItemsSet = new HashSet<>();
		order.setTotal(new BigDecimal(0));
		order = orderRepository.save(order);
		for (var orderItemDto : createOrderDTO.order_items()) {
			ProductDTO productDTO;
			try {
				productDTO = productService.reserveStock(
						orderItemDto.price_id(), orderItemDto.quantity()
				);
			} catch (WebClientResponseException we) {
				order.setStatus(OrderStatus.FAILED);
				orderRepository.save(order);
				throw new APIException(
						HttpStatus.BAD_REQUEST,
						"Stock not enough for product with price_id : " + orderItemDto.price_id()
				);
			}
			OrderItem orderItem = new OrderItem();
			orderItem.setPriceId(orderItemDto.price_id());
			orderItem.setQuantity(orderItemDto.quantity());
			orderItem.setOrder(order);
			orderItem.setLineTotal(
					productDTO.price().multiply(
							new BigDecimal(orderItem.getQuantity())
					)
			);
			orderItemsSet.add(orderItem);
		}
		order.setOrderItems(orderItemsSet);
		order.setTotal(
				order.getOrderItems()
						.stream().map(OrderItem::getLineTotal)
						.reduce(BigDecimal.ZERO, BigDecimal::add)
		);
		order.setStatus(OrderStatus.PENDING);
		order = orderRepository.save(order);
		return mapToDTO(order);
	}

	public PaginatedOrderDTO getAllOrders(int pageNo, int pageSize) {
		Authentication authentication = SecurityContextHolder
				.getContext()
				.getAuthentication();
		if (authentication == null || !authentication.isAuthenticated()) {
			throw new APIException(HttpStatus.UNAUTHORIZED, "User is not authenticated");
		}
		UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
		Page<Order> orders;
		if (principal.isAdmin()) {
			orders = orderRepository
					.findAll(
							PageRequest.of(pageNo, pageSize)
					);
		} else {
			orders = orderRepository
					.findAllByUserId(
							principal.getId(), PageRequest.of(pageNo, pageSize)
					);
		}
		return new PaginatedOrderDTO(
				orders.getContent()
						.stream()
						.map(this::mapToDTO)
						.toList(),
				orders.getNumber(),
				orders.getSize(),
				orders.getTotalElements(),
				orders.getTotalPages(),
				orders.isLast()
		);
	}

	public OrderDTO getOrder(Long orderId) {
		var order = orderRepository.getOrderById(orderId).orElseThrow(
				() -> new APIException(HttpStatus.NOT_FOUND, "Order not found with id " + orderId)
		);
		return mapToDTO(order);
	}

	public OrderDTO updateOrder(Long orderId, UpdateOrderDTO updateOrderDTO) {
		var order = orderRepository.getOrderById(orderId).orElseThrow(
				() -> new APIException(HttpStatus.NOT_FOUND, "Order not found with id " + orderId)
		);
		if (order.getStatus() != updateOrderDTO.status()) {
			order.setStatus(updateOrderDTO.status());
			order = orderRepository.save(order);
		}
		return mapToDTO(order);
	}

	public boolean isOrderOwner(Long userId, Long orderId) {
		Order order = orderRepository.getOrderById(orderId)
				.orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "Order not found with id" + orderId));
		return Objects.equals(order.getUserId(), userId);
	}

	private OrderDTO mapToDTO(Order order) {
		return new OrderDTO(
				order.getId(),
				order.getStatus(),
				order.getTotal(),
				order.getOrderItems()
						.stream()
						.map(oi -> new OrderItemDTO(oi.getId(), oi.getQuantity(), oi.getLineTotal()))
						.collect(Collectors.toSet())
		);
	}

}
