package chess.domain;

public enum Command {

    START("start"),
    END("end"),
    MOVE("move"),
    STATUS("status");

    private final String message;

    Command(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
