package com.josemeurer.dslearn.services;

import com.josemeurer.dslearn.entities.Role;
import com.josemeurer.dslearn.entities.User;
import com.josemeurer.dslearn.repositories.UserRepository;
import com.josemeurer.dslearn.services.exceptions.ForbiddenException;
import com.josemeurer.dslearn.services.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public User authenticated() { //Retorna o user logado
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName(); //Pega o nome do usuario do spring security
            return userRepository.findByEmail(username);
        }
        catch (Exception e) {
            throw new UnauthorizedException("Invalid user");
        }
    }

    public void validateSelfOrAdmin(Long id) { //Verifica se o id buscado é dele ou se ele é admin
        User user = authenticated();
        if (!user.getId().equals(id) && !userHasHole(user, "ROLE_ADMIN")) {
            throw new ForbiddenException("Access denied");
        }
    }

    private boolean userHasHole(User user, String roleName) { //Verifica se o user tem a role
        for (Role role : user.getRoles()) {
            if (role.getAuthority().equals(roleName)) {
                return true;
            }
        }
        return false;
    }
}
