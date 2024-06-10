package com.example.simplespringwebfluxapp.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.Column;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public abstract class BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @CreatedBy
    @Column(value = "created_by")
    private String createdBy;

    @CreatedDate
    @Column(value = "created_date")
    private LocalDateTime createdDate;

    @LastModifiedBy
    @Column(value = "last_modified_by")
    private String lastModifiedBy;

    @LastModifiedDate
    @Column(value = "last_modified_date")
    private LocalDateTime lastModifiedDate;

}
