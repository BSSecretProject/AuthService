package auth_service.service;

import auth_service.dto.AuthResponseDto;
import auth_service.dto.LoginDto;
import auth_service.dto.RegisterDto;
import auth_service.entity.Token;
import auth_service.entity.User;
import auth_service.exeptions.DataValidationException;
import auth_service.repository.UserRepository;
import auth_service.validator.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final RestTemplate restTemplate;
    private final PasswordEncoder passwordEncoder;
    private final UserValidator userValidator;

    public AuthService(UserRepository userRepository, TokenService tokenService, RestTemplate restTemplate, PasswordEncoder passwordEncoder, UserValidator userValidator) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.restTemplate = restTemplate;
        this.passwordEncoder = passwordEncoder;
        this.userValidator = userValidator;
    }

    public void registerUser(RegisterDto registerDto) {

        userValidator.validateRegistration(registerDto);
        User user = createUser(registerDto);
        userRepository.save(user);

        log.info("Регистрация пользователя в сервисе");
        String USER_SERVICE_URL = "http://localhost:8080/users/register";
        ResponseEntity<String> response = restTemplate.postForEntity(USER_SERVICE_URL, registerDto, String.class);
        log.info("пользователь отрпавился в юзер сервис");

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Failed to register user in User Service");
        }
    }

    public AuthResponseDto loginUser(LoginDto loginDto) {
        User user;
        if (loginDto.getUsernameOrMail().contains("@")) {
            user = userRepository.getUserByEmail(loginDto.getUsernameOrMail()).orElseThrow(() -> new DataValidationException("User with this email does not exist"));
        } else {
            user = userRepository.getUserByUsername(loginDto.getUsernameOrMail()).orElseThrow(() -> new DataValidationException("User with this email does not exist"));
        }
        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new DataValidationException("Wrong password!");
        }
        Token token = tokenService.createToken(user);

        return new AuthResponseDto(token.getAccessToken(), token.getRefreshToken());
    }

    public User createUser(RegisterDto registerDto) {
        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setEmail(registerDto.getEmail());
        return user;
    }
}
