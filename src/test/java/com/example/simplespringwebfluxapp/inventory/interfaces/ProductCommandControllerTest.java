package com.example.simplespringwebfluxapp.inventory.interfaces;

import com.example.simplespringwebfluxapp.common.JwtTokenUtils;
import com.example.simplespringwebfluxapp.config.WebSecurityConfig;
import com.example.simplespringwebfluxapp.inventory.application.command.ICreateProductUseCase;
import com.example.simplespringwebfluxapp.inventory.application.command.IDeleteProductUseCase;
import com.example.simplespringwebfluxapp.inventory.application.command.IUpdateProductUseCase;
import com.example.simplespringwebfluxapp.inventory.interfaces.in.ProductCommandDTO;
import com.example.simplespringwebfluxapp.inventory.interfaces.out.ProductDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockJwt;

@WebFluxTest(controllers = ProductCommandController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import({WebSecurityConfig.class, JwtTokenUtils.class})
class ProductCommandControllerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private ICreateProductUseCase createProductUseCase;
    @MockBean
    private IUpdateProductUseCase updateProductUseCase;
    @MockBean
    private IDeleteProductUseCase deleteProductUseCase;

    @BeforeEach
    void setUp() {
        this.webClient = webClient.mutateWith(mockJwt().authorities(List.of(new SimpleGrantedAuthority("ADMIN"))));
    }

    @Test
    void createProduct() {
        var body = new ProductCommandDTO("name", 1L, 100, BigDecimal.ONE);
        var response = new ProductDTO(10L, "name", "shop", 100, BigDecimal.ONE);
        when(createProductUseCase.create(body)).thenReturn(Mono.just(response));
        webClient
            .mutateWith(csrf())
            .post().uri("/api/v1/products")
            .bodyValue(body)
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody()
            .jsonPath("$.id").isEqualTo("10")
            .jsonPath("$.name").isEqualTo("name")
            .jsonPath("$.shop").isEqualTo("shop")
            .jsonPath("$.quantity").isEqualTo("100")
            .jsonPath("$.price").isEqualTo("1");
    }

    @Test
    void updateProduct() {
        var body = new ProductCommandDTO("name", 1L, 100, BigDecimal.ONE);
        var response = new ProductDTO(10L, "name", "shop", 100, BigDecimal.ONE);
        when(updateProductUseCase.updateProduct(10L, body)).thenReturn(Mono.just(response));
        webClient
            .mutateWith(csrf())
            .put().uri("/api/v1/products/10")
            .bodyValue(body)
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody()
            .jsonPath("$.id").isEqualTo("10")
            .jsonPath("$.name").isEqualTo("name")
            .jsonPath("$.shop").isEqualTo("shop")
            .jsonPath("$.quantity").isEqualTo("100")
            .jsonPath("$.price").isEqualTo("1");
    }

    @Test
    void deleteProduct() {
        when(deleteProductUseCase.deleteProduct(10L)).thenReturn(Mono.empty());
        webClient
            .mutateWith(csrf())
            .delete().uri("/api/v1/products/10")
            .exchange()
            .expectStatus()
            .isOk();
    }
}