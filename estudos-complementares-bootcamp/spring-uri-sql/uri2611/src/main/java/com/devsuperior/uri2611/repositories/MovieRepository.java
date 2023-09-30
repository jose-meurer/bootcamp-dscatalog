package com.devsuperior.uri2611.repositories;

import com.devsuperior.uri2611.dto.MovieMinDTO;
import com.devsuperior.uri2611.entities.Movie;
import com.devsuperior.uri2611.projections.MovieMinProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query(nativeQuery = true, value = "SELECT m.id, m.name " +
            "FROM movies m " +
            "INNER JOIN genres g ON m.id_genres = g.id " +
            "WHERE UPPER(g.description) = UPPER(:genre)")
    List<MovieMinProjection> searchSQL(String genre);

    @Query("SELECT new com.devsuperior.uri2611.dto.MovieMinDTO(m.id, m.name) " +
            "FROM Movie m " +
            "WHERE m.genre.description = :genre")
    List<MovieMinDTO> searchJPQL(String genre);
}
