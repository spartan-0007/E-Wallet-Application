package E_wallet.example.demo.mapper;


import E_wallet.example.demo.dto.CreateUserRequest;
import E_wallet.example.demo.enums.UserStatus;
import E_wallet.example.demo.model.User;
import lombok.experimental.UtilityClass;

import static ch.qos.logback.classic.spi.ThrowableProxyVO.build;

@UtilityClass
public class UserMapper {

    public User mapToUser(CreateUserRequest request){
        return User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .phoneNo(request.getPhoneNo())
                .userIdentificationType(request.getUserIdentificationType())
                .userIdentificationValue(request.getUserIdentificationValue())
                .userStatus(UserStatus.ACTIVE).build();
    }
}
