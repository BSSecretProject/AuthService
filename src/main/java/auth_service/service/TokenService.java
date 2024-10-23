package auth_service.service;

import auth_service.entity.Token;
import auth_service.entity.User;
import auth_service.repository.TokenRepository;
import auth_service.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class TokenService {

    private final JwtTokenProvider jwtTokenProvider;
    private final TokenRepository tokenRepository;

    @Autowired
    public TokenService(JwtTokenProvider jwtTokenProvider, TokenRepository tokenRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.tokenRepository = tokenRepository;
    }

    public Token createToken(User user) {
        String accessToken = jwtTokenProvider.generateAccessToken(user.getUsername());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getUsername());

        // Получаем даты истечения токенов
        Date accessTokenExpiryDate = jwtTokenProvider.getAccessTokenExpiryDate();
        Date refreshTokenExpiryDate = jwtTokenProvider.getRefreshTokenExpiryDate();

        // Создаем новый объект токена
        Token token = Token.builder()
                .id(UUID.randomUUID())
                .user(user)
                .accessToken(accessToken)
                .accessTokenExpiry(accessTokenExpiryDate.toInstant())
                .refreshToken(refreshToken)
                .refreshTokenExpiry(refreshTokenExpiryDate.toInstant())
                .type(Token.TokenType.ACCESS)
                .build();

        return tokenRepository.save(token);
    }
}