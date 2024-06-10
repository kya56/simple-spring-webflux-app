package com.example.simplespringwebfluxapp.inventory.application.query.impl;

import com.example.simplespringwebfluxapp.common.EntityNotFoundException;
import com.example.simplespringwebfluxapp.inventory.application.query.IQueryProductUseCase;
import com.example.simplespringwebfluxapp.inventory.domain.Shop;
import com.example.simplespringwebfluxapp.inventory.domain.Product;
import com.example.simplespringwebfluxapp.inventory.interfaces.in.ProductSearchQueryDTO;
import com.example.simplespringwebfluxapp.inventory.interfaces.out.ProductDTO;
import com.example.simplespringwebfluxapp.inventory.repository.ShopJpaRepository;
import com.example.simplespringwebfluxapp.inventory.repository.IProductQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
class QueryProductUseCase implements IQueryProductUseCase {

    private final IProductQueryRepository productRepository;
    private final ShopJpaRepository shopRepository;

    @Override
    public Flux<ProductDTO> search(ProductSearchQueryDTO productSearchQueryDTO) {
        var productsMono = productRepository.searchProducts(productSearchQueryDTO)
            .collectList();

        return productsMono
            .flatMapMany(this::attachShop);
    }

    private Flux<ProductDTO> attachShop(List<Product> products) {
        return getShopMap(products)
            .map(shopMap ->
                products.stream()
                    .map(product -> {
                        var shop = shopMap.get(product.getShopId());

                        if (shop == null) {
                            throw new EntityNotFoundException("shop not found");
                        }

                        return product.transform(shop);
                    })
                    .toList()
            )
            .flatMapIterable(list -> list);
    }

    private Mono<Map<Long, Shop>> getShopMap(List<Product> products) {
        return shopRepository.findAllById(
                products.stream()
                    .map(Product::getShopId)
                    .toList()
            )
            .collectMap(Shop::getId);
    }
}
