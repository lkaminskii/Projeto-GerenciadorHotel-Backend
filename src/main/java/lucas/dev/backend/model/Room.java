package lucas.dev.backend.model;

import lucas.dev.backend.enums.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(name = "room_number")
    private String roomNumber;

    @NotEmpty
    @Column(name = "room_description")
    private String roomDescription;

    @NotEmpty
    @Column(name = "value_per_day")
    private double valuePerDay;

    @NotNull
    @Column(name = "is_vacant")
    private boolean isVacant;

    @NotNull
    @Column(name = "room_status")
    @Enumerated(EnumType.STRING)
    private RoomStatus roomStatus;
}
