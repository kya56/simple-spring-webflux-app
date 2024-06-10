package com.example.simplespringwebfluxapp.inventory.application.command.impl;

import com.example.simplespringwebfluxapp.inventory.application.command.ICreateProductUseCase;
import com.example.simplespringwebfluxapp.inventory.application.command.IQueryShopEntityUseCase;
import com.example.simplespringwebfluxapp.inventory.domain.Product;
import com.example.simplespringwebfluxapp.inventory.interfaces.in.ProductCommandDTO;
import com.example.simplespringwebfluxapp.inventory.interfaces.out.ProductDTO;
import com.example.simplespringwebfluxapp.inventory.repository.ProductJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
class CreateProductUseCase implements ICreateProductUseCase {

    private final IQueryShopEntityUseCase queryShopUseCase;
    private final ProductJpaRepository productRepository;

    @Override
    @Transactional
    public Mono<ProductDTO> create(ProductCommandDTO productDTO) {
        return queryShopUseCase.findById(productDTO.shopId())
            .flatMap(shop ->
                productRepository.save(Product.of(productDTO))
                    .map(product -> product.transform(shop))
            );
    }

}