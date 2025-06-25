package lucas.dev.backend.service;

import lucas.dev.backend.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.time.LocalDateTime;
import static org.mockito.Mockito.*;

class ReservationCleanupServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationCleanupService cleanupService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deleteExpiredReservations_WhenExpiredReservationsExist_DeletesThem() {
        when(reservationRepository.count()).thenReturn(5L).thenReturn(2L);
        doNothing().when(reservationRepository).deleteByCheckOutBefore(any(LocalDateTime.class));
        cleanupService.deleteExpiredReservations();
        verify(reservationRepository, times(2)).count();
        verify(reservationRepository).deleteByCheckOutBefore(any(LocalDateTime.class));
    }

    @Test
    void deleteExpiredReservations_WhenNoExpiredReservationsExist_DoesNothing() {
        when(reservationRepository.count()).thenReturn(2L).thenReturn(2L);
        doNothing().when(reservationRepository).deleteByCheckOutBefore(any(LocalDateTime.class));
        cleanupService.deleteExpiredReservations();
        verify(reservationRepository, times(2)).count();
        verify(reservationRepository).deleteByCheckOutBefore(any(LocalDateTime.class));
    }
} 