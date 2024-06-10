package com.example.simplespringwebfluxapp.inventory.application.command.impl;

import com.example.simplespringwebfluxapp.inventory.application.command.IQueryShopEntityUseCase;
import com.example.simplespringwebfluxapp.inventory.application.command.IQueryProductEntityUseCase;
import com.example.simplespringwebfluxapp.inventory.application.command.IUpdateProductUseCase;
import com.example.simplespringwebfluxapp.inventory.interfaces.in.ProductCommandDTO;
import com.example.simplespringwebfluxapp.inventory.interfaces.out.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
class UpdateProductUseCase implements IUpdateProductUseCase {

    private final IQueryProductEntityUseCase queryProductUseCase;
    private final IQueryShopEntityUseCase queryShopUseCase;

    @Override
    @Transactional
    public Mono<ProductDTO> updateProduct(long id, ProductCommandDTO dto) {
        return queryProductUseCase.findById(id)
            .flatMap(product ->
                queryShopUseCase.findById(dto.shopId())
                    .map(shop -> {
                        product.apply(dto);
                        return product.transform(shop);
                    })
            );
    }

}