package lucas.dev.backend.dto;

import lucas.dev.backend.model.Room;
import org.springframework.beans.BeanUtils;

import lombok.Getter;

@Getter
public class RoomModel {
    
    private String roomNumber;
    private String roomDescription;

    public RoomModel(Room room) {
        BeanUtils.copyProperties(room, this);
    }

}
