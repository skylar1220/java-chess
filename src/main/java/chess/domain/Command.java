package chess.domain;

public enum Command {

    START("start"),
    END("end"),
    MOVE("move");

    private final String message;

    Command(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
