package lucas.dev.backend.controller;

import lucas.dev.backend.model.Room;
import lucas.dev.backend.service.RoomService;
import lucas.dev.backend.exception.RoomNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(RoomController.class)
class RoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoomService roomService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void insertRoom_WhenValid_ReturnsCreated() throws Exception {
        Room room = new Room();
        room.setId(1L);
        room.setRoomNumber("101");
        when(roomService.save(any(Room.class))).thenReturn(room);
        mockMvc.perform(post("/rooms")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(room)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void findRoomById_WhenExists_ReturnsRoom() throws Exception {
        Room room = new Room();
        room.setId(1L);
        when(roomService.findById(1L)).thenReturn(room);
        mockMvc.perform(get("/rooms/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void findRoomById_WhenNotExists_ReturnsNotFound() throws Exception {
        when(roomService.findById(1L)).thenThrow(new RoomNotFoundException("Room Not Found"));
        mockMvc.perform(get("/rooms/1"))
            .andExpect(status().isInternalServerError()); // Ajuste se houver handler para 404
    }

    @Test
    void getAllRooms_ReturnsListOfRooms() throws Exception {
        Room room1 = new Room(); room1.setId(1L); room1.setRoomNumber("101");
        Room room2 = new Room(); room2.setId(2L); room2.setRoomNumber("102");
        when(roomService.findAll()).thenReturn(Arrays.asList(room1, room2));
        mockMvc.perform(get("/rooms"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", org.hamcrest.Matchers.hasSize(2)))
            .andExpect(jsonPath("$[0].roomNumber").value("101"));
    }

    @Test
    void deleteRoomById_WhenExists_ReturnsNoContent() throws Exception {
        doNothing().when(roomService).deleteById(1L);
        mockMvc.perform(delete("/rooms/1"))
            .andExpect(status().isNoContent());
    }

    @Test
    void deleteRoomById_WhenNotExists_ReturnsNotFound() throws Exception {
        doThrow(new RoomNotFoundException("Room Not Found")).when(roomService).deleteById(1L);
        mockMvc.perform(delete("/rooms/1"))
            .andExpect(status().isInternalServerError()); // Ajuste se houver handler para 404
    }

    @Test
    void partialUpdateRoom_WhenExists_UpdatesRoom() throws Exception {
        Room updated = new Room();
        updated.setId(1L);
        updated.setVacant(false);
        Map<String, Object> updates = new HashMap<>();
        updates.put("isVacant", false);
        when(roomService.partialUpdate(eq(1L), anyMap())).thenReturn(updated);
        mockMvc.perform(patch("/rooms/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updates)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.vacant").value(false));
    }

    @Test
    void partialUpdateRoom_WhenNotExists_ReturnsNotFound() throws Exception {
        Map<String, Object> updates = new HashMap<>();
        updates.put("isVacant", false);
        when(roomService.partialUpdate(eq(1L), anyMap())).thenThrow(new RoomNotFoundException("Room Not Found"));
        mockMvc.perform(patch("/rooms/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updates)))
            .andExpect(status().isInternalServerError()); // Ajuste se houver handler para 404
    }
} 