package dev.nikhilj.productservice.repositories;

import dev.nikhilj.productservice.entities.Price;
import dev.nikhilj.productservice.entities.Product;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
	public Optional<Product> getProductById(Long productId);


	@Query("""
			SELECT p from Product p
				WHERE LOWER(p.name)	 like LOWER(CONCAT('%', :searchString, '%') )
					OR LOWER(p.description)	 like LOWER(CONCAT('%', :searchString, '%') )
			""")
	public Page<Product> searchProducts(@Param("searchString") String searchString, Pageable pageable);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	public Optional<Product> findAndLockById(Long id);

	Optional<Product> findAndLockByActivePrice(Price activePrice);

}
