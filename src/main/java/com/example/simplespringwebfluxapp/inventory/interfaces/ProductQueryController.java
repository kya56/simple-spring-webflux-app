package com.example.simplespringwebfluxapp.inventory.interfaces;

import com.example.simplespringwebfluxapp.inventory.application.query.IQueryProductUseCase;
import com.example.simplespringwebfluxapp.inventory.interfaces.in.ProductSearchQueryDTO;
import com.example.simplespringwebfluxapp.inventory.interfaces.out.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
public class ProductQueryController {

    private final IQueryProductUseCase queryProductUseCase;

    @GetMapping(value = "/api/v1/products/search")
    public Flux<ProductDTO> searchProducts(@RequestParam(required = false) String name,
                                           @RequestParam(required = false) Long shopId,
                                           @RequestParam(required = false, defaultValue = "0") int page,
                                           @RequestParam(required = false, defaultValue = "20") int size) {
        return queryProductUseCase.search(
            new ProductSearchQueryDTO(name, shopId, PageRequest.of(page, size))
        );
    }

}
