package lucas.dev.backend.model;


import jakarta.persistence.*;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import java.io.Serial;
import java.io.Serializable;



@Data
@Entity
@Table(name = "rooms")
public class Room implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "room_number")
    private String roomNumber;

    @NotBlank
    @Column(name = "room_description")
    private String roomDescription;

}
