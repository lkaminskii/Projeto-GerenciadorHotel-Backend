package lucas.dev.backend.login;

import jakarta.persistence.*;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import java.io.Serial;
import java.io.Serializable;

@Data
@Entity
@Table(name = "users")
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @NotBlank
    private String login;

    @Column
    @NotBlank
    private String password;

    @Column(name = "is_admin")
    @NotBlank
    private boolean isAdmin;

}
