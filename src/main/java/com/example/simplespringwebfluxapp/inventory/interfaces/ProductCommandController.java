package com.example.simplespringwebfluxapp.inventory.interfaces;

import com.example.simplespringwebfluxapp.inventory.application.command.ICreateProductUseCase;
import com.example.simplespringwebfluxapp.inventory.application.command.IDeleteProductUseCase;
import com.example.simplespringwebfluxapp.inventory.application.command.IUpdateProductUseCase;
import com.example.simplespringwebfluxapp.inventory.interfaces.in.ProductCommandDTO;
import com.example.simplespringwebfluxapp.inventory.interfaces.out.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class ProductCommandController {

    private final ICreateProductUseCase createProductUseCase;
    private final IUpdateProductUseCase updateProductUseCase;
    private final IDeleteProductUseCase deleteProductUseCase;

    @PostMapping(value = "/api/v1/products")
    public Mono<ProductDTO> createProduct(@RequestBody ProductCommandDTO productDTO) {
        return createProductUseCase.create(productDTO);
    }

    @PutMapping(value = "/api/v1/products/{id}")
    public Mono<ProductDTO> updateProduct(@PathVariable long id, @RequestBody ProductCommandDTO productDTO) {
        return updateProductUseCase.updateProduct(id, productDTO);
    }

    @DeleteMapping(value = "/api/v1/products/{id}")
    public Mono<Void> deleteProduct(@PathVariable long id) {
        return deleteProductUseCase.deleteProduct(id);
    }
}
