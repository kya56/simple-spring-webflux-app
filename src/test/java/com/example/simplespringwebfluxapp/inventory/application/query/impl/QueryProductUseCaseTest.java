package com.example.simplespringwebfluxapp.inventory.application.query.impl;

import com.example.simplespringwebfluxapp.common.EntityNotFoundException;
import com.example.simplespringwebfluxapp.inventory.domain.Shop;
import com.example.simplespringwebfluxapp.inventory.domain.Product;
import com.example.simplespringwebfluxapp.inventory.interfaces.in.ProductSearchQueryDTO;
import com.example.simplespringwebfluxapp.inventory.interfaces.out.ProductDTO;
import com.example.simplespringwebfluxapp.inventory.repository.ShopJpaRepository;
import com.example.simplespringwebfluxapp.inventory.repository.IProductQueryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QueryProductUseCaseTest {

    @InjectMocks
    private QueryProductUseCase queryProductUseCase;
    @Mock
    private IProductQueryRepository productRepository;
    @Mock
    private ShopJpaRepository shopRepository;

    @Nested
    class Search {

        @Test
        @DisplayName("return products on search")
        void search() {
            var product1 = mock(Product.class);
            when(product1.getShopId()).thenReturn(100L);
            var product2 = mock(Product.class);
            when(product2.getShopId()).thenReturn(200L);
            var shop1 = mock(Shop.class);
            when(shop1.getId()).thenReturn(100L);
            var shop2 = mock(Shop.class);
            when(shop2.getId()).thenReturn(200L);
            var dto = mock(ProductSearchQueryDTO.class);
            when(productRepository.searchProducts(dto))
                .thenReturn(Flux.fromIterable(List.of(product1, product2)));

            when(shopRepository.findAllById(List.of(100L, 200L)))
                .thenReturn(Flux.fromIterable(List.of(shop1, shop2)));

            var productDTO1 = mock(ProductDTO.class);
            var productDTO2 = mock(ProductDTO.class);
            when(product1.transform(shop1)).thenReturn(productDTO1);
            when(product2.transform(shop2)).thenReturn(productDTO2);

            queryProductUseCase.search(dto)
                .as(StepVerifier::create)
                .expectNext(productDTO1)
                .expectNext(productDTO2)
                .verifyComplete();
        }

        @Test
        @DisplayName("return error on search when shop not found")
        void searchWhenShopNotFound() {
            var product1 = mock(Product.class);
            when(product1.getShopId()).thenReturn(100L);
            var product2 = mock(Product.class);
            when(product2.getShopId()).thenReturn(200L);
            var shop1 = mock(Shop.class);
            when(shop1.getId()).thenReturn(100L);
            var dto = mock(ProductSearchQueryDTO.class);
            when(productRepository.searchProducts(dto))
                .thenReturn(Flux.fromIterable(List.of(product1, product2)));

            when(shopRepository.findAllById(List.of(100L, 200L)))
                .thenReturn(Flux.fromIterable(List.of(shop1)));

            var productDTO1 = mock(ProductDTO.class);
            when(product1.transform(shop1)).thenReturn(productDTO1);

            queryProductUseCase.search(dto)
                .as(StepVerifier::create)
                .verifyError(EntityNotFoundException.class);
        }
    }
}