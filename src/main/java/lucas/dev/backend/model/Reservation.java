package lucas.dev.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "reservations")
public class Reservation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Guest guest;

    @OneToOne
    private Room room;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private OffsetDateTime checkIn;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private OffsetDateTime checkOut;




}
