package com.example.simplespringwebfluxapp.inventory.application.command.impl;

import com.example.simplespringwebfluxapp.common.EntityNotFoundException;
import com.example.simplespringwebfluxapp.inventory.domain.Shop;
import com.example.simplespringwebfluxapp.inventory.repository.ShopJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QueryShopUseCaseTest {

    @InjectMocks
    private QueryShopUseCase queryShopUseCase;
    @Mock
    private ShopJpaRepository shopRepository;

    @Test
    @DisplayName("return shop when shop is found")
    void findById() {
        var shop = mock(Shop.class);
        when(shopRepository.findById(1L))
            .thenReturn(Mono.just(shop));

        queryShopUseCase.findById(1L)
            .as(StepVerifier::create)
            .assertNext(result -> assertEquals(result, shop))
            .verifyComplete();
    }

    @Test
    @DisplayName("return error when shop is not found")
    void findByIdWhenNotFound() {
        when(shopRepository.findById(1L))
            .thenReturn(Mono.empty());

        queryShopUseCase.findById(1L)
            .as(StepVerifier::create)
            .verifyError(EntityNotFoundException.class);
    }
}