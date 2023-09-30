package com.devsuperior.uri2621.dto;

import com.devsuperior.uri2621.projections.ProductOnlyNameProjection;

public class ProductOnlyNameDTO {

    String name;

    public ProductOnlyNameDTO() {
    }

    public ProductOnlyNameDTO(String name) {
        this.name = name;
    }

    public ProductOnlyNameDTO(ProductOnlyNameProjection projection) {
        this(projection.getName());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
