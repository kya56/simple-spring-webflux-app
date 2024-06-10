package com.example.simplespringwebfluxapp.inventory.application.command.impl;

import com.example.simplespringwebfluxapp.inventory.application.command.IQueryShopEntityUseCase;
import com.example.simplespringwebfluxapp.inventory.domain.Shop;
import com.example.simplespringwebfluxapp.inventory.domain.Product;
import com.example.simplespringwebfluxapp.inventory.interfaces.in.ProductCommandDTO;
import com.example.simplespringwebfluxapp.inventory.interfaces.out.ProductDTO;
import com.example.simplespringwebfluxapp.inventory.repository.ProductJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateProductUseCaseTest {

    @InjectMocks
    private CreateProductUseCase createProductUseCase;

    @Mock
    private IQueryShopEntityUseCase queryShopEntityUseCase;
    @Mock
    private ProductJpaRepository productRepository;

    @Test
    @DisplayName("create product when shop is found")
    void create() {
        var shop = mock(Shop.class);
        when(queryShopEntityUseCase.findById(100L))
            .thenReturn(Mono.just(shop));

        var dto = mock(ProductCommandDTO.class);
        when(dto.shopId()).thenReturn(100L);
        when(dto.name()).thenReturn("name");
        when(dto.quantity()).thenReturn(10000);
        when(dto.price()).thenReturn(BigDecimal.ONE);

        var productEntity = mock(Product.class);
        var productDTO = mock(ProductDTO.class);
        when(productRepository.save(any())).thenReturn(Mono.just(productEntity));
        when(productEntity.transform(shop)).thenReturn(productDTO);

        createProductUseCase
            .create(dto)
            .as(StepVerifier::create)
            .assertNext(result -> {
                assertEquals(productDTO, result);
            })
            .verifyComplete();

        verify(queryShopEntityUseCase).findById(100L);
        verify(productRepository).save(any());
    }
}