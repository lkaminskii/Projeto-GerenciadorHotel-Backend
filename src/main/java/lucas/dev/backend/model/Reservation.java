package lucas.dev.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @NotEmpty
    private Guest guest;

    @OneToOne
    @NotEmpty
    private Room room;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotEmpty
    @Column(name = "check_in")
    private OffsetDateTime checkIn;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotEmpty
    @Column(name = "check_out")
    private OffsetDateTime checkOut;




}
