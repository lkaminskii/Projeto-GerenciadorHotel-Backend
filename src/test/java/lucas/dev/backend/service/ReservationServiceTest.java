package lucas.dev.backend.service;

import lucas.dev.backend.model.Reservation;
import lucas.dev.backend.model.Room;
import lucas.dev.backend.model.Guest;
import lucas.dev.backend.repository.ReservationRepository;
import lucas.dev.backend.repository.RoomRepository;
import lucas.dev.backend.exception.ReservationNotFoundException;
import lucas.dev.backend.validator.ReservationValidationService;
import lucas.dev.backend.validator.RoomLookupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private RoomRepository roomRepository;
    @Mock
    private ReservationValidationService reservationValidationService;
    @Mock
    private RoomLookupService roomLookupService;

    @InjectMocks
    private ReservationService reservationService;

    private Reservation reservation;
    private Room room;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        room = new Room();
        room.setRoomNumber("101");
        reservation = new Reservation();
        reservation.setId(1L);
        reservation.setRoom(room);

        Guest guest = new Guest();
        guest.setName("John Doe");
        reservation.setGuest(guest);
        
    }

    @Test
    void throwReservation_WhenValid_SavesReservation() {
        when(roomLookupService.findRoomOrThrow(anyString())).thenReturn(room);
        doNothing().when(reservationValidationService).validateRoomVacancy(room);
        doNothing().when(reservationValidationService).validateReservationConflict(any(), any(), any(), any());
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);
        Reservation saved = reservationService.throwReservation(reservation);
        assertThat(saved).isNotNull();
        verify(reservationRepository).save(reservation);
    }

    @Test
    void findById_WhenExists_ReturnsReservation() {
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        Reservation found = reservationService.findById(1L);
        assertThat(found).isNotNull().isEqualTo(reservation);
    }

    @Test
    void findById_WhenNotExists_ThrowsException() {
        when(reservationRepository.findById(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> reservationService.findById(1L))
            .isInstanceOf(ReservationNotFoundException.class);
    }

    @Test
    void findAll_ReturnsListOfReservations() {
        List<Reservation> reservations = Arrays.asList(reservation, new Reservation());
        when(reservationRepository.findAll()).thenReturn(reservations);
        List<Reservation> result = reservationService.findAll();
        assertThat(result).hasSize(2);
    }

    @Test
    void update_WhenExists_UpdatesReservation() {
        Reservation updated = new Reservation();
        updated.setRoom(room);
        // updated.setGuest(...); updated.setCheckIn(...); updated.setCheckOut(...);
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        doNothing().when(reservationValidationService).validateReservationUpdateConflict(any(), any(), any(), any(), any());
        when(reservationRepository.save(any(Reservation.class))).thenReturn(updated);
        Reservation result = reservationService.update(1L, updated);
        assertThat(result).isNotNull();
        verify(reservationRepository).save(any(Reservation.class));
    }

    @Test
    void update_WhenNotExists_ThrowsException() {
        when(reservationRepository.findById(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> reservationService.update(1L, reservation))
            .isInstanceOf(ReservationNotFoundException.class);
    }

    @Test
    void deleteById_WhenExists_DeletesReservation() {
        when(reservationRepository.existsById(1L)).thenReturn(true);
        reservationService.deleteById(1L);
        verify(reservationRepository).deleteById(1L);
    }

    @Test
    void deleteById_WhenNotExists_ThrowsException() {
        when(reservationRepository.existsById(1L)).thenReturn(false);
        assertThatThrownBy(() -> reservationService.deleteById(1L))
            .isInstanceOf(ReservationNotFoundException.class);
        verify(reservationRepository, never()).deleteById(any());
    }
} 