package com.example.simplespringwebfluxapp.inventory.interfaces;

import com.example.simplespringwebfluxapp.common.JwtTokenUtils;
import com.example.simplespringwebfluxapp.config.WebSecurityConfig;
import com.example.simplespringwebfluxapp.inventory.application.query.IQueryProductUseCase;
import com.example.simplespringwebfluxapp.inventory.interfaces.in.ProductSearchQueryDTO;
import com.example.simplespringwebfluxapp.inventory.interfaces.out.ProductDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockJwt;

@WebFluxTest(controllers = ProductQueryController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import({WebSecurityConfig.class, JwtTokenUtils.class})
class ProductQueryControllerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private IQueryProductUseCase queryProductUseCase;

    @BeforeEach
    void setUp() {
        this.webClient = webClient.mutateWith(mockJwt());
    }

    @Test
    void searchProducts() {
        var body = new ProductSearchQueryDTO("name", 1L, PageRequest.of(0, 20));
        var response = new ProductDTO(10L, "name", "shop", 100, BigDecimal.ONE);
        when(queryProductUseCase.search(body)).thenReturn(Flux.just(response));
        webClient
            .mutateWith(csrf())
            .get().uri("/api/v1/products/search?name=name&shopId=1")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody()
            .jsonPath("$[0].id").isEqualTo("10")
            .jsonPath("$[0].name").isEqualTo("name")
            .jsonPath("$[0].shop").isEqualTo("shop")
            .jsonPath("$[0].quantity").isEqualTo("100")
            .jsonPath("$[0].price").isEqualTo("1");
    }
}