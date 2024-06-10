package com.example.simplespringwebfluxapp.inventory.application.command.impl;

import com.example.simplespringwebfluxapp.inventory.application.command.IDeleteProductUseCase;
import com.example.simplespringwebfluxapp.inventory.application.command.IQueryProductEntityUseCase;
import com.example.simplespringwebfluxapp.inventory.repository.ProductJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
class DeleteProductUseCase implements IDeleteProductUseCase {

    private final IQueryProductEntityUseCase queryProductUseCase;
    private final ProductJpaRepository productRepository;

    @Override
    @Transactional
    public Mono<Void> deleteProduct(long id) {
        return queryProductUseCase.findById(id)
            .flatMap(productRepository::delete);
    }

}