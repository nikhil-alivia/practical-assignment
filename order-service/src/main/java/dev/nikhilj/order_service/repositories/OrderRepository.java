package dev.nikhilj.order_service.repositories;

import dev.nikhilj.order_service.entities.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

	public Optional<Order> getOrderById(Long id);

	public Page<Order> findAllByUserId(Long userId, Pageable pageable);
}
