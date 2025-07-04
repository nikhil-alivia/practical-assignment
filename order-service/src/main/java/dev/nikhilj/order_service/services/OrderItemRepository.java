package dev.nikhilj.order_service.services;

import dev.nikhilj.order_service.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
