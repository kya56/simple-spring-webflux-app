package com.example.simplespringwebfluxapp.inventory.application.command.impl;

import com.example.simplespringwebfluxapp.common.EntityNotFoundException;
import com.example.simplespringwebfluxapp.inventory.domain.Product;
import com.example.simplespringwebfluxapp.inventory.repository.ProductJpaRepository;
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
class QueryProductEntityUseCaseTest {

    @InjectMocks
    private QueryProductEntityUseCase queryProductEntityUseCase;
    @Mock
    private ProductJpaRepository productRepository;
    @Test
    @DisplayName("return product when product is found")
    void findById() {
        var product = mock(Product.class);
        when(productRepository.findById(1L))
            .thenReturn(Mono.just(product));

        queryProductEntityUseCase.findById(1L)
            .as(StepVerifier::create)
            .assertNext(result -> assertEquals(result, product))
            .verifyComplete();
    }

    @Test
    @DisplayName("return error when product is not found")
    void findByIdWhenNotFound() {
        when(productRepository.findById(1L))
            .thenReturn(Mono.empty());

        queryProductEntityUseCase.findById(1L)
            .as(StepVerifier::create)
            .verifyError(EntityNotFoundException.class);
    }
}