package com.example.simplespringwebfluxapp.inventory.application.command;

import com.example.simplespringwebfluxapp.inventory.domain.Product;
import reactor.core.publisher.Mono;

public interface IQueryProductEntityUseCase {
    Mono<Product> findById(long id);
}
