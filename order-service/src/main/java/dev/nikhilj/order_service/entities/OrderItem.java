package dev.nikhilj.order_service.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SoftDelete;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "order_items")
@SoftDelete
public class OrderItem extends AuditableEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id")
	private Order order;


	@Column(name = "price_id", nullable = false)
	private Long priceId;

	@Column(name = "quantity", nullable = false)
	private int quantity;

	@Column(name = "line_total", nullable = false)
	private BigDecimal lineTotal;

}
