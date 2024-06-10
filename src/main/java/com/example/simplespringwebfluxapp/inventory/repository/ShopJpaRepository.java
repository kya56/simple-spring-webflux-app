package com.example.simplespringwebfluxapp.inventory.repository;

import com.example.simplespringwebfluxapp.inventory.domain.Shop;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopJpaRepository extends ReactiveCrudRepository<Shop, Long> {
}
