package auth_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class LoginDto {

    @NotBlank(message = "Username or Email is required")
    private String usernameOrMail;

    @NotBlank(message = "Password is required")
    private String password;
}
