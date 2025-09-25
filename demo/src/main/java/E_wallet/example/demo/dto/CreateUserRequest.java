package E_wallet.example.demo.dto;

import E_wallet.example.demo.enums.UserIdentificationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class CreateUserRequest {

    String name;

    String email;


    @NotBlank
    String phoneNo;


    @NotNull
    String password;


    @NotNull
    UserIdentificationType userIdentificationType;

    @NotNull
    String userIdentificationValue;

}
