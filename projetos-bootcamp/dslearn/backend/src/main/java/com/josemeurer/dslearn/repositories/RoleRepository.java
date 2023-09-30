package com.josemeurer.dslearn.repositories;

import com.josemeurer.dslearn.entities.Notification;
import com.josemeurer.dslearn.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
