package auth_service.validator;

import auth_service.dto.RegisterDto;
import auth_service.exeptions.DataValidationException;
import auth_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidator {
    private final UserRepository userRepository;

    public void validateRegistration(RegisterDto registerDto){
        validateIfExistByEmail(registerDto.getEmail());
        validateIfExistByUsername(registerDto.getUsername());
    }

    public void validateIfExistByEmail(String email){
        if (userRepository.existsByEmail(email)){
            throw new DataValidationException("User with this email already exist!");
        }
    }
    public void validateIfExistByUsername(String username){
        if (userRepository.existsByUsername(username)){
            throw new DataValidationException("User with this username already exist!");
        }
    }
}
