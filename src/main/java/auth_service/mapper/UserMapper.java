package auth_service.mapper;

import auth_service.dto.LoginDto;
import auth_service.dto.RegisterDto;
import auth_service.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    LoginDto toUserDto(User user);
    User toUser(LoginDto userDto);
    User toUser(RegisterDto registerDto);
}
