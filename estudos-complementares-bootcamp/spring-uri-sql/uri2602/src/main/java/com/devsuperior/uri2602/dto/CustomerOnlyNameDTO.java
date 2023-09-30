package com.devsuperior.uri2602.dto;

import com.devsuperior.uri2602.entities.Customer;

public class CustomerOnlyNameDTO {

    private String name;

    public CustomerOnlyNameDTO() {
    }

    public CustomerOnlyNameDTO(String name) {
        this.name = name;
    }

    public CustomerOnlyNameDTO(Customer proj) {
        this(proj.getName());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
