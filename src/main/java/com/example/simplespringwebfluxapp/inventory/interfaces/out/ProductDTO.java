package com.example.simplespringwebfluxapp.inventory.interfaces.out;

import java.math.BigDecimal;

public record ProductDTO(
    Long id,
    String name,
    String shop,
    int quantity,
    BigDecimal price
) {
}
