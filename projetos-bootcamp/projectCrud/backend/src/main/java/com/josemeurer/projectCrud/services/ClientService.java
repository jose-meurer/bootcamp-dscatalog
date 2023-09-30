package com.josemeurer.projectCrud.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.josemeurer.projectCrud.dto.ClientDTO;
import com.josemeurer.projectCrud.entities.Client;
import com.josemeurer.projectCrud.repositories.ClientRepository;
import com.josemeurer.projectCrud.services.exceptions.DatabaseException;
import com.josemeurer.projectCrud.services.exceptions.ResourceNotFoundException;

@Service
public class ClientService {

	@Autowired
	private ClientRepository clientRepository;

	@Transactional(readOnly = true)
	public Page<ClientDTO> findAllPaged(PageRequest pageRequest) {
		Page<Client> clients = clientRepository.findAll(pageRequest);
		return clients.map(x -> new ClientDTO(x));
	}

	@Transactional(readOnly = true)
	public ClientDTO findById(Long id) {
		Optional<Client> obj = clientRepository.findById(id);
		Client entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new ClientDTO(entity);
	}
	
	@Transactional
	public ClientDTO insert(ClientDTO clientDto) {
		Client entity = new Client();
		copyDtoToEntity(clientDto, entity);
		entity = clientRepository.save(entity);
		return new ClientDTO(entity);
	}
	
	@Transactional
	public ClientDTO update(Long id, ClientDTO clientDto) {
		try {
			Client entity = clientRepository.getReferenceById(id);
			copyDtoToEntity(clientDto, entity);
			entity = clientRepository.save(entity);
			return new ClientDTO(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
	}
	
	public void delete(Long id) {
		try {
			clientRepository.deleteById(id);
		}
		catch(EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
		catch(DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation"); 
		}
	}

	private void copyDtoToEntity(ClientDTO clientDto, Client entity) {
		entity.setName(clientDto.getName());
		entity.setCpf(clientDto.getCpf());
		entity.setIncome(clientDto.getIncome());
		entity.setBirthDate(clientDto.getBirthDate());
		entity.setChildren(clientDto.getChildren());
	}
	
	
}
