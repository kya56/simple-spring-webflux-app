package com.example.simplespringwebfluxapp.inventory.application.command.impl;

import com.example.simplespringwebfluxapp.common.EntityNotFoundException;
import com.example.simplespringwebfluxapp.inventory.application.command.IQueryProductEntityUseCase;
import com.example.simplespringwebfluxapp.inventory.domain.Product;
import com.example.simplespringwebfluxapp.inventory.repository.ProductJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
class QueryProductEntityUseCase implements IQueryProductEntityUseCase {

    private final ProductJpaRepository productRepository;

    @Override
    public Mono<Product> findById(long id) {
        return productRepository.findById(id)
            .switchIfEmpty(Mono.error(new EntityNotFoundException("Product not found, id=%s".formatted(id))));
    }
}
