package chess.domain;

import java.util.Arrays;

public enum Command {
    /*
    이 두 메서드를 나누지 말고 하나로 묶는 것이 어떨까요? 결국은 커맨드를 생성하는 역할은 동일한데 메서드가 나눠지는 것이 어색하다고 느껴집니다.
    Command 객체에서 가질 수 있는 args의 개수 같은 값을 관리하고 사용한다면 적절한 유효성 검증 및 커맨드 유형에 따른 객체 생성도 가능할 것 같습니다.
    */
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
