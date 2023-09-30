package com.josemeurer.devcourses.Repositories;

import com.josemeurer.devcourses.entities.Enrollment;
import com.josemeurer.devcourses.entities.pk.EnrollmentPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, EnrollmentPK> {
}
