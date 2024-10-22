package auth_service.controller;

import auth_service.dto.AuthResponseDto;
import auth_service.dto.LoginDto;
import auth_service.dto.RegisterDto;
import auth_service.entity.Token;
import auth_service.entity.User;
import auth_service.repository.UserRepository;
import auth_service.security.JwtTokenProvider;
import auth_service.service.AuthService;
import auth_service.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private TokenService tokenService;
    @Autowired
    private AuthService authService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterDto registerDto) {
        log.info("Регистрация пользователя в Контроллере");
        authService.registerUser(registerDto);
        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public AuthResponseDto login(@RequestBody LoginDto loginDto) {
        // TODO: Поменять логику поиска по логину/юзернейму
        User user = userRepository.findByUsernameOrEmail(loginDto.getUsername(), loginDto.getUsername()).orElseThrow(() -> new RuntimeException("Invalid username!"));
        log.info("Проверка юзернейма");

        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password!");
        }
        log.info("Проверка пароля");

        String accessToken = jwtTokenProvider.generateAccessToken(user.getUsername());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getUsername());
        log.info("Генерация токенов");

        Token token = tokenService.createToken(user);
        log.info("Токены сохранены");

        return new AuthResponseDto(token.getAccessToken(), token.getRefreshToken());
    }

}
