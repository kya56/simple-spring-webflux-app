package com.example.simplespringwebfluxapp.inventory.application.command.impl;

import com.example.simplespringwebfluxapp.inventory.application.command.IQueryShopEntityUseCase;
import com.example.simplespringwebfluxapp.inventory.application.command.IQueryProductEntityUseCase;
import com.example.simplespringwebfluxapp.inventory.domain.Shop;
import com.example.simplespringwebfluxapp.inventory.domain.Product;
import com.example.simplespringwebfluxapp.inventory.interfaces.in.ProductCommandDTO;
import com.example.simplespringwebfluxapp.inventory.interfaces.out.ProductDTO;
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
class UpdateProductUseCaseTest {

    @InjectMocks
    private UpdateProductUseCase updateProductUseCase;

    @Mock
    private IQueryProductEntityUseCase queryProductUseCase;
    @Mock
    private IQueryShopEntityUseCase queryShopUseCase;

    @Test
    void updateProduct() {
        var product = mock(Product.class);
        var shop = mock(Shop.class);
        when(queryProductUseCase.findById(1L)).thenReturn(Mono.just(product));
        when(queryShopUseCase.findById(100L)).thenReturn(Mono.just(shop));
        var productDTO = mock(ProductDTO.class);
        when(product.transform(shop)).thenReturn(productDTO);

        var dto = new ProductCommandDTO("name", 100L, 10, BigDecimal.ONE);
        updateProductUseCase
            .updateProduct(1L, dto)
            .as(StepVerifier::create)
            .assertNext(result -> {
                assertEquals(result, productDTO);
            })
            .verifyComplete();

        verify(product).apply(dto);
        verify(product).transform(shop);
    }
}