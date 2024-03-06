package lucas.dev.backend.dto;

import lombok.Getter;
import lucas.dev.backend.login.User;

@Getter
public class UserModel {

    private String login;

    public UserModel(User user) {
        login = user.getLogin();
    }

}
