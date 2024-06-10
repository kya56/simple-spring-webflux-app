package com.example.simplespringwebfluxapp.inventory.domain;

import com.example.simplespringwebfluxapp.common.BaseEntity;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@Table(name = "shop")
public class Shop extends BaseEntity {

    @Id
    private Long id;

    @Column(value = "name")
    private String name;
}
