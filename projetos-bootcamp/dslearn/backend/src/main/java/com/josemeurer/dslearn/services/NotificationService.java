package com.josemeurer.dslearn.services;

import com.josemeurer.dslearn.dto.NotificationDTO;
import com.josemeurer.dslearn.entities.Notification;
import com.josemeurer.dslearn.entities.User;
import com.josemeurer.dslearn.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private AuthService authService;

    @Transactional(readOnly = true)
    public Page<NotificationDTO> notificationsForCurrentUser(boolean unreadOnly, Pageable pageable) {
        User user = authService.authenticated();
        Page<Notification> page = notificationRepository.find(user,unreadOnly, pageable);
        return page.map(x -> new NotificationDTO(x));
    }
}
