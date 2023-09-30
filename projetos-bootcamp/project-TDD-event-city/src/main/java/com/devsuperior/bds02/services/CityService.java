package com.devsuperior.bds02.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds02.dto.CityDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.repositories.CityRepository;
import com.devsuperior.bds02.services.exceptions.DatabaseException;
import com.devsuperior.bds02.services.exceptions.ResourceNotFoundException;

@Service
public class CityService {

	@Autowired
	CityRepository repository;

	public List<CityDTO> findAll() {
		List<City> list = repository.findAll(Sort.by(Direction.ASC, "name"));
		return list.stream().map(x -> new CityDTO(x)).toList();
	}

	@Transactional
	public CityDTO insert(CityDTO dto) {
		City entity = new City();
		dtoToEntity(dto, entity);
		entity = repository.save(entity);
		return new CityDTO(entity);
	}

	private void dtoToEntity(CityDTO dto, City entity) {
		entity.setName(dto.getName());
	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);
		}
		catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
		
	}
}
