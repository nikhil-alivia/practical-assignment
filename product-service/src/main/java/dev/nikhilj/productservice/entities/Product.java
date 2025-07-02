package dev.nikhilj.productservice.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JoinFormula;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SoftDelete;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "products")
@SoftDelete
public class Product extends AuditableEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false, unique = true, length = 50)
	private String name;

	@Column(name = "description", nullable = false, length = 1000)
	private String description;

	@Min(value = 0, message = "Stock quantity can not be negative")
	@Column(name = "stock_quantity", nullable = false)
	private Long stockQuantity;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "active_price_id", unique = true)
	private Price activePrice;


}
