package lucas.dev.backend.exception;

public class RoomNotVacantException extends RuntimeException {
    public RoomNotVacantException(String message) {
        super(message);
    }
} 