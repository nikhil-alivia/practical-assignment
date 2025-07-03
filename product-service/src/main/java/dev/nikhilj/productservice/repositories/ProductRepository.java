package dev.nikhilj.productservice.repositories;

import dev.nikhilj.productservice.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
	public Optional<Product> getProductById(Long productId);


	@Query("""
			SELECT p from Product p
				WHERE LOWER(p.name)	 like LOWER(CONCAT('%', :searchString, '%') )
					OR LOWER(p.description)	 like LOWER(CONCAT('%', :searchString, '%') )
			""")
	public Page<Product> searchProducts(@Param("searchString") String searchString, Pageable pageable);

}
