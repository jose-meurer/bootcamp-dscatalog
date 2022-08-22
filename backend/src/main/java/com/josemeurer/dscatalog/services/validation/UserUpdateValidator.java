package com.josemeurer.dscatalog.services.validation;

import com.josemeurer.dscatalog.dto.UserUpdateDTO;
import com.josemeurer.dscatalog.repositories.UserRepository;
import com.josemeurer.dscatalog.resources.exceptions.FieldMessage;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class UserUpdateValidator implements ConstraintValidator<UserUpdateValid, UserUpdateDTO> {

    @Autowired
    private UserRepository repository;

    @Override
    public void initialize(UserUpdateValid ann) {
    }

    @Override
    public boolean isValid(UserUpdateDTO dto, ConstraintValidatorContext context) {

        List<FieldMessage> list = new ArrayList<>();

        // Coloque aqui seus testes de validação, acrescentando objetos FieldMessage à lista

//        User user = repository.findByEmail(dto.getEmail());
//        if (user != null) {
//            list.add(new FieldMessage("email", "Email already exists"));
//        }

        for (FieldMessage e : list) { //Insere o meu erro de validacao na lista de erro do bean validation
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }
}
