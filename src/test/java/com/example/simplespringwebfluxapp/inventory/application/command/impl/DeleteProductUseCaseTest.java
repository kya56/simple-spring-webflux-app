package com.example.simplespringwebfluxapp.inventory.application.command.impl;

import com.example.simplespringwebfluxapp.inventory.application.command.IQueryProductEntityUseCase;
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

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteProductUseCaseTest {

    @InjectMocks
    private DeleteProductUseCase deleteProductUseCase;
    @Mock
    private ProductJpaRepository productRepository;
    @Mock
    private IQueryProductEntityUseCase queryProductUseCase;

    @Test
    @DisplayName("delete when product is found")
    void deleteProduct() {
        var product = mock(Product.class);
        when(queryProductUseCase.findById(1L))
            .thenReturn(Mono.just(product));
        when(productRepository.delete(product)).thenReturn(Mono.empty());
        deleteProductUseCase.deleteProduct(1L)
            .as(StepVerifier::create)
            .expectNextCount(0)
            .verifyComplete();

        verify(productRepository).delete(product);
    }
}