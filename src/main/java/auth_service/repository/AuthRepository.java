package auth_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import auth_service.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface AuthRepository extends JpaRepository<User, UUID> {
    Optional<User> findUserByEmailOrNickname(String email, String nickname);
}
