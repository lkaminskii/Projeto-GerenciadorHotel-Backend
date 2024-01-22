package lucas.dev.backend.dto;

import lombok.Data;
import lucas.dev.backend.login.UserStatus;

@Data
public class UserModel {

    private String login;
    private UserStatus userStatus;

}
