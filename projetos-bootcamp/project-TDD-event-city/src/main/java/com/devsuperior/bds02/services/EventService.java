package com.devsuperior.bds02.services;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds02.dto.EventDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.entities.Event;
import com.devsuperior.bds02.repositories.EventRepository;
import com.devsuperior.bds02.services.exceptions.ResourceNotFoundException;

@Service
public class EventService {

	@Autowired
	EventRepository repository;

	@Transactional
	public EventDTO update(Long id, EventDTO dto) {
		try {
			Event entity = repository.getOne(id);
			dtoToEntity(entity, dto);
			entity = repository.save(entity);
			return new EventDTO(entity);
		} 
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("id not found " + id);
		}
	}

	private void dtoToEntity(Event entity, EventDTO dto) {
		entity.setName(dto.getName());
		entity.setCity(new City(dto.getCityId(), null));
		entity.setDate(dto.getDate());
		entity.setUrl(dto.getUrl());
	}
}
