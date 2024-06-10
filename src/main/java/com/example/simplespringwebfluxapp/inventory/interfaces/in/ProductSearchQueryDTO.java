package com.example.simplespringwebfluxapp.inventory.interfaces.in;

import org.springframework.data.domain.PageRequest;

public record ProductSearchQueryDTO(String name, Long shopId, PageRequest page) {
}
