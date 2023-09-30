package com.josemeurer.dslearn.services;

import com.josemeurer.dslearn.dto.DeliverRevisionDTO;
import com.josemeurer.dslearn.entities.Deliver;
import com.josemeurer.dslearn.repositories.DeliverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeliverService {

    @Autowired
    private DeliverRepository deliverRepository;

    @PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR')")
    @Transactional
    public void saveRevision(Long id, DeliverRevisionDTO dto) {
        Deliver entity = deliverRepository.getOne(id);
        entity.setStatus(dto.getStatus());
        entity.setFeedback(dto.getFeedback());
        entity.setCorrectCount(dto.getCorrectCount());
        deliverRepository.save(entity);
    }
}
