package com.josemeurer.projectCrud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.josemeurer.projectCrud.entities.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

}
