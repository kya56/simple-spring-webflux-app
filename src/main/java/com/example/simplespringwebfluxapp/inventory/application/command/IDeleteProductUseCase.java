package com.example.simplespringwebfluxapp.inventory.application.command;

import reactor.core.publisher.Mono;

public interface IDeleteProductUseCase {
    Mono<Void> deleteProduct(long id);
}
