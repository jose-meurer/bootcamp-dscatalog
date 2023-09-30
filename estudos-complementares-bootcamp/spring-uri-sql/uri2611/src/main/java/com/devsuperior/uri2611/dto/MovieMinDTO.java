package com.devsuperior.uri2611.dto;

import com.devsuperior.uri2611.projections.MovieMinProjection;

public class MovieMinDTO {

    private Long id;
    private String name;

    public MovieMinDTO() {
    }

    public MovieMinDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public MovieMinDTO(MovieMinProjection obj) {
        this(obj.getId(), obj.getName());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
