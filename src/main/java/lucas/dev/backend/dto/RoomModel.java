package lucas.dev.backend.dto;

import lucas.dev.backend.enums.RoomStatus;
import lucas.dev.backend.model.Room;

import lombok.Getter;

@Getter
public class RoomModel {
    
    private String roomNumber;
    private String roomDescription;
    private double valuePerDay;
    private boolean isVacant;
    private RoomStatus roomStatus;

    public RoomModel() {}

    public RoomModel(Room room) {
        this.roomNumber = room.getRoomNumber();
        this.roomDescription = room.getRoomDescription();
        this.valuePerDay = room.getValuePerDay();
        this.isVacant = room.isVacant();
        this.roomStatus = room.getRoomStatus();
    }

}
