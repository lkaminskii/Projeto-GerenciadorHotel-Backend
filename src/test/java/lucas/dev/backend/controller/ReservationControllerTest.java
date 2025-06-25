package lucas.dev.backend.controller;

import lucas.dev.backend.model.Reservation;
import lucas.dev.backend.service.ReservationService;
import lucas.dev.backend.exception.ReservationNotFoundException;
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

@WebMvcTest(ReservationController.class)
class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createReservation_WhenValid_ReturnsCreated() throws Exception {
        Reservation reservation = new Reservation();
        reservation.setId(1L);
        when(reservationService.throwReservation(any(Reservation.class))).thenReturn(reservation);
        mockMvc.perform(post("/reservations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reservation)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void getReservationById_WhenExists_ReturnsReservation() throws Exception {
        Reservation reservation = new Reservation();
        reservation.setId(1L);
        when(reservationService.findById(1L)).thenReturn(reservation);
        mockMvc.perform(get("/reservations/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void getReservationById_WhenNotExists_ReturnsNotFound() throws Exception {
        when(reservationService.findById(1L)).thenThrow(new ReservationNotFoundException("Reservation Not Found"));
        mockMvc.perform(get("/reservations/1"))
            .andExpect(status().isNotFound());
    }

    @Test
    void getAllReservations_ReturnsListOfReservations() throws Exception {
        Reservation r1 = new Reservation(); r1.setId(1L);
        Reservation r2 = new Reservation(); r2.setId(2L);
        when(reservationService.findAll()).thenReturn(Arrays.asList(r1, r2));
        mockMvc.perform(get("/reservations"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", org.hamcrest.Matchers.hasSize(2)))
            .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    void updateReservation_WhenExists_UpdatesReservation() throws Exception {
        Reservation updated = new Reservation();
        updated.setId(1L);
        when(reservationService.update(eq(1L), any(Reservation.class))).thenReturn(updated);
        mockMvc.perform(put("/reservations/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void updateReservation_WhenNotExists_ReturnsNotFound() throws Exception {
        Reservation updated = new Reservation();
        updated.setId(1L);
        when(reservationService.update(eq(1L), any(Reservation.class))).thenThrow(new ReservationNotFoundException("Reservation Not Found"));
        mockMvc.perform(put("/reservations/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))
            .andExpect(status().isNotFound());
    }

    @Test
    void deleteReservation_WhenExists_ReturnsNoContent() throws Exception {
        doNothing().when(reservationService).deleteById(1L);
        mockMvc.perform(delete("/reservations/1"))
            .andExpect(status().isNoContent());
    }

    @Test
    void deleteReservation_WhenNotExists_ReturnsNotFound() throws Exception {
        doThrow(new ReservationNotFoundException("Reservation Not Found")).when(reservationService).deleteById(1L);
        mockMvc.perform(delete("/reservations/1"))
            .andExpect(status().isNotFound());
    }
} 