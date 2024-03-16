package lucas.dev.backend.service;

import org.springframework.stereotype.Component;

import lucas.dev.backend.model.Reservation;
import lucas.dev.backend.repository.ReservationRepository;

@Component
public class ReservationService {

    private ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository){
        this.reservationRepository = reservationRepository;
    }

    public Reservation throwReservation (Reservation reservation){
        return null;
    }
    
}
