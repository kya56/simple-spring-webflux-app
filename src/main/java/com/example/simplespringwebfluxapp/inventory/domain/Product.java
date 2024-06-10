package com.example.simplespringwebfluxapp.inventory.domain;

import com.example.simplespringwebfluxapp.common.BaseEntity;
import com.example.simplespringwebfluxapp.inventory.interfaces.in.ProductCommandDTO;
import com.example.simplespringwebfluxapp.inventory.interfaces.out.ProductDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@Table(name = "product")
public class Product extends BaseEntity {

    @Id
    private Long id;

    @Column(value = "name")
    private String name;

    @Column(value = "shop_id")
    private long shopId;

    @Column(value = "quantity")
    @Positive
    private int quantity;

    @Column(value = "price")
    @NotNull
    @Positive
    private BigDecimal price;

    public static Product of(ProductCommandDTO dto) {
        return new Product(
            null,
            dto.name(),
            dto.shopId(),
            dto.quantity(),
            dto.price()
        );
    }

    public void apply(ProductCommandDTO dto) {
        this.name = dto.name();
        this.shopId = dto.shopId();
        this.quantity = dto.quantity();
        this.price = dto.price();
    }

    public ProductDTO transform(Shop shop) {
        return new ProductDTO(
            this.id,
            this.name,
            shop.getName(),
            this.quantity,
            this.price
        );
    }
}
