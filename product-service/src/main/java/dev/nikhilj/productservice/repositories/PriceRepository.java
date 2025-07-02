package dev.nikhilj.productservice.repositories;

import dev.nikhilj.productservice.entities.Price;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PriceRepository extends JpaRepository<Price, Long> {

}
