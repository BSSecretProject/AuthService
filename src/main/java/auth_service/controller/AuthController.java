package auth_service.controller;

import auth_service.dto.AuthResponseDto;
import auth_service.dto.LoginDto;
import auth_service.dto.RegisterDto;
import auth_service.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterDto registerDto) {
        log.info("Регистрация пользователя в Контроллере");
        authService.registerUser(registerDto);
        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public AuthResponseDto login(@RequestBody LoginDto loginDto) {
        return authService.loginUser(loginDto);
    }
}
