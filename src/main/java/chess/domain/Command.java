package chess.domain;

import java.util.Arrays;

public enum Command {

    START("start"),
    CONTINUE("continue"),
    END("end"),
    MOVE("move"),
    STATUS("status");

    private final String message;

    Command(final String message) {
        this.message = message;
    }

    public static Command from(final String input) {
        validateBlank(input);

        if (input.startsWith("move")) {
            return MOVE;
        }
        return Arrays.stream(values())
                .filter(command -> command.message.equals(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 유효하지 않은 입력입니다."));
    }

    private static void validateBlank(final String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 입력입니다.");
        }
    }

    public String getMessage() {
        return message;
    }
}
