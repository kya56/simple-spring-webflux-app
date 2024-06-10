package com.example.simplespringwebfluxapp.inventory.repository.impl;

import com.example.simplespringwebfluxapp.inventory.interfaces.in.ProductSearchQueryDTO;
import com.example.simplespringwebfluxapp.inventory.testutils.BaseIntegrationTest;
import com.example.simplespringwebfluxapp.inventory.testutils.DBMigrate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import reactor.test.StepVerifier;

@DataR2dbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductQueryRepositoryTest extends BaseIntegrationTest {

    private ProductQueryRepository productQueryRepository;
    @Autowired
    private R2dbcEntityTemplate r2dbcEntityTemplate;

    @BeforeEach
    void init() {
        this.productQueryRepository = new ProductQueryRepository(r2dbcEntityTemplate);
    }

    @Test
    @DBMigrate(scripts = {"src/test/resources/db/migration/ProductQueryRepositoryTest/testdata.sql"})
    void searchProducts() {
        var dto = new ProductSearchQueryDTO(null, 1L, PageRequest.of(0, 20));
        productQueryRepository.searchProducts(dto)
            .as(StepVerifier::create)
            .expectSubscription()
            .expectNextMatches(product -> product.getId() == 1L)
            .expectNextMatches(product -> product.getId() == 2L)
            .verifyComplete();
    }
}