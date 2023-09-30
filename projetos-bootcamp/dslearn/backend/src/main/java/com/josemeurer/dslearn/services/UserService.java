package com.josemeurer.dslearn.services;

import com.josemeurer.dslearn.dto.UserDTO;
import com.josemeurer.dslearn.entities.User;
import com.josemeurer.dslearn.repositories.UserRepository;
import com.josemeurer.dslearn.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        authService.validateSelfOrAdmin(id); //Validacao de busca do id

        Optional<User> userOptional = userRepository.findById(id);
        User entity = userOptional.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new UserDTO(entity);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = new User();
        user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Email not found");
        }

        return user;
    }
}
