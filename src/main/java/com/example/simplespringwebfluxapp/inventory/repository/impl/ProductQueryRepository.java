package com.example.simplespringwebfluxapp.inventory.repository.impl;

import com.example.simplespringwebfluxapp.inventory.domain.Product;
import com.example.simplespringwebfluxapp.inventory.interfaces.in.ProductSearchQueryDTO;
import com.example.simplespringwebfluxapp.inventory.repository.IProductQueryRepository;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
@RequiredArgsConstructor
class ProductQueryRepository implements IProductQueryRepository {

    private final R2dbcEntityTemplate r2dbcEntityTemplate;

    @Override
    public Flux<Product> searchProducts(ProductSearchQueryDTO queryDTO) {
        var nameCriteria = StringUtils.isBlank(queryDTO.name()) ? Criteria.empty() : Criteria.where("name").is(queryDTO.name());
        var shopIdCriteria = queryDTO.shopId() == null ? Criteria.empty() : Criteria.where("shop_id").is(queryDTO.shopId());
        return r2dbcEntityTemplate
            .select(Query.query(nameCriteria.and(shopIdCriteria))
                .with(queryDTO.page()), Product.class);
    }

}
