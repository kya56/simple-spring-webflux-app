package com.example.simplespringwebfluxapp.inventory.repository;

import com.example.simplespringwebfluxapp.inventory.domain.Product;
import com.example.simplespringwebfluxapp.inventory.interfaces.in.ProductSearchQueryDTO;
import reactor.core.publisher.Flux;

public interface IProductQueryRepository {
    Flux<Product> searchProducts(ProductSearchQueryDTO queryDTO);
}
