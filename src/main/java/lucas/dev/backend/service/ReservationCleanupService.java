package lucas.dev.backend.service;

import lucas.dev.backend.repository.ReservationRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDateTime;

@Component
public class ReservationCleanupService {

    private static final Logger logger = LoggerFactory.getLogger(ReservationCleanupService.class);
    private final ReservationRepository reservationRepository;

    public ReservationCleanupService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Scheduled(cron = "0 0 * * * *")
    public void deleteExpiredReservations() {
        LocalDateTime now = LocalDateTime.now();
        logger.info("Iniciando limpeza automática de reservas expiradas. Horário atual: {}", now);
        
        long countBefore = reservationRepository.count();
        reservationRepository.deleteByCheckOutBefore(now);
        long countAfter = reservationRepository.count();
        long removedCount = countBefore - countAfter;
        
        if (removedCount > 0) {
            logger.info("Limpeza concluída. {} reservas expiradas foram removidas", removedCount);
        } else {
            logger.debug("Limpeza concluída. Nenhuma reserva expirada encontrada");
        }
    }
} 