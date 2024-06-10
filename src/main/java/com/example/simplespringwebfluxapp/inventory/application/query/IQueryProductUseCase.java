package com.example.simplespringwebfluxapp.inventory.application.query;

import com.example.simplespringwebfluxapp.inventory.interfaces.in.ProductSearchQueryDTO;
import com.example.simplespringwebfluxapp.inventory.interfaces.out.ProductDTO;
import reactor.core.publisher.Flux;

public interface IQueryProductUseCase {

    Flux<ProductDTO> search(ProductSearchQueryDTO productSearchQueryDTO);
}
