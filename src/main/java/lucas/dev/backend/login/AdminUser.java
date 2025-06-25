package lucas.dev.backend.login;

import org.springframework.beans.factory.annotation.Value;

public class AdminUser {

    @Value("${adminuser.login}")
    private String login;

    @Value("${adminuser.password}")
    private String password;

}
