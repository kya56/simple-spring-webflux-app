package com.example.simplespringwebfluxapp.inventory.application.command;

import com.example.simplespringwebfluxapp.inventory.domain.Shop;
import reactor.core.publisher.Mono;

public interface IQueryShopEntityUseCase {
    Mono<Shop> findById(long id);
}
