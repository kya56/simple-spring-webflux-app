package com.example.simplespringwebfluxapp.inventory.application.command.impl;

import com.example.simplespringwebfluxapp.common.EntityNotFoundException;
import com.example.simplespringwebfluxapp.inventory.application.command.IQueryShopEntityUseCase;
import com.example.simplespringwebfluxapp.inventory.domain.Shop;
import com.example.simplespringwebfluxapp.inventory.repository.ShopJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
class QueryShopUseCase implements IQueryShopEntityUseCase {

    private final ShopJpaRepository shopRepository;

    @Override
    public Mono<Shop> findById(long id) {
        return shopRepository.findById(id)
            .switchIfEmpty(Mono.error(new EntityNotFoundException("Shop not found, id=%s".formatted(id))));
    }
}
