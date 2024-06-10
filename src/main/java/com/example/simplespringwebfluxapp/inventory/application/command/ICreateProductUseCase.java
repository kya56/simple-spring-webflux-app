package com.example.simplespringwebfluxapp.inventory.application.command;

import com.example.simplespringwebfluxapp.inventory.interfaces.in.ProductCommandDTO;
import com.example.simplespringwebfluxapp.inventory.interfaces.out.ProductDTO;
import reactor.core.publisher.Mono;

public interface ICreateProductUseCase {
    Mono<ProductDTO> create(ProductCommandDTO productDTO);
}
