package auth_service.service;

import auth_service.controller.AuthController;
import auth_service.dto.RegisterDto;
import auth_service.entity.User;
import auth_service.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerUser(RegisterDto registerDto) {

        if (userRepository.existsByUsername(registerDto.getUsername()) || userRepository.existsByEmail(registerDto.getEmail())) {
            throw new RuntimeException("Username or email already taken!");
        }

        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setEmail(registerDto.getEmail());
        userRepository.save(user);
        log.info("Регистрация пользователя в сервисе");
        String USER_SERVICE_URL = "http://localhost:8080/users/register";
        ResponseEntity<String> response = restTemplate.postForEntity(USER_SERVICE_URL, registerDto, String.class);
        log.info("пользователь отрпавился в юзер сервис");

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Failed to register user in User Service");
        }
    }
}
