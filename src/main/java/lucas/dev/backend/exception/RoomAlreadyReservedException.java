package lucas.dev.backend.exception;

public class RoomAlreadyReservedException extends RuntimeException {
    public RoomAlreadyReservedException(String message) {
        super(message);
    }
} 