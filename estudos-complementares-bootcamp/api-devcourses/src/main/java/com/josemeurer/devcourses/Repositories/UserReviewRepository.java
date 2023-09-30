package com.josemeurer.devcourses.Repositories;

import com.josemeurer.devcourses.entities.UserReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserReviewRepository extends JpaRepository<UserReview, Long> {
}
