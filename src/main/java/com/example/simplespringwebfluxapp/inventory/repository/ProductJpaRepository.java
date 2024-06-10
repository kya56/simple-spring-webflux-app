package com.example.simplespringwebfluxapp.inventory.repository;

import com.example.simplespringwebfluxapp.inventory.domain.Product;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductJpaRepository extends ReactiveCrudRepository<Product, Long> {

}
