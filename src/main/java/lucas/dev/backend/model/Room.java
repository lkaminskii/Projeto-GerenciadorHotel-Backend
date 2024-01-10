package lucas.dev.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

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
    private Integer id;

    @NotBlank
    @Size(max = 60)
    @Column(name = "roomNumber")
    private Integer number;

    @NotBlank
    @Column(name = "roomDescription")
    private String description;

}
