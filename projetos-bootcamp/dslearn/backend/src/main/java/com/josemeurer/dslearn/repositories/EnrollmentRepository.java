package com.josemeurer.dslearn.repositories;

import com.josemeurer.dslearn.entities.Enrollment;
import com.josemeurer.dslearn.entities.pk.EnrollmentPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollmentRepository extends JpaRepository <Enrollment, EnrollmentPK> {
}
