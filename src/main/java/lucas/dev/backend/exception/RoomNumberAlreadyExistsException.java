package lucas.dev.backend.exception;

public class RoomNumberAlreadyExistsException extends RuntimeException {
    public RoomNumberAlreadyExistsException(String message) {
        super(message);
    }
}
