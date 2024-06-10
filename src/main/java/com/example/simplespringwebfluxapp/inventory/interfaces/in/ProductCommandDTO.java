package com.example.simplespringwebfluxapp.inventory.interfaces.in;

import com.google.common.base.Preconditions;

import java.math.BigDecimal;

public record ProductCommandDTO(
    String name,
    long shopId,
    int quantity,
    BigDecimal price
) {
    public ProductCommandDTO {
        Preconditions.checkArgument(quantity >= 0, "Quantity must be zero or positive");
        Preconditions.checkArgument(BigDecimal.ZERO.compareTo(price) <= 0, "Price must be zero or positive");
    }
}
