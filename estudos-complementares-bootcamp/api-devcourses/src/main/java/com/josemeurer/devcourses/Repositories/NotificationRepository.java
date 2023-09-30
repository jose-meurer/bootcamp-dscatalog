package com.josemeurer.devcourses.Repositories;

import com.josemeurer.devcourses.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {


    @Query("SELECT n " +
            "FROM Notification n " +
            "WHERE UPPER(n.user.email) = UPPER(:email) " +
            "AND n.read = false")
    List<Notification> unreadNotifications(String email);

    @Query("SELECT n " +
            "FROM Notification n " +
            "WHERE UPPER(n.user.email) = UPPER(:email) ")
    List<Notification> allUserNotifications(String email);
}
