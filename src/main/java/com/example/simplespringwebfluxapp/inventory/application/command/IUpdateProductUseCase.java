package com.example.simplespringwebfluxapp.inventory.application.command;

import com.example.simplespringwebfluxapp.inventory.interfaces.in.ProductCommandDTO;
import com.example.simplespringwebfluxapp.inventory.interfaces.out.ProductDTO;
import reactor.core.publisher.Mono;

public interface IUpdateProductUseCase {
    Mono<ProductDTO> updateProduct(long id, ProductCommandDTO productDTO);
}
