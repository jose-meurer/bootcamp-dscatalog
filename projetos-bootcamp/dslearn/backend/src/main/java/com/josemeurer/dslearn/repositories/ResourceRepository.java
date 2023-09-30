package com.josemeurer.dslearn.repositories;

import com.josemeurer.dslearn.entities.Notification;
import com.josemeurer.dslearn.entities.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {
}
