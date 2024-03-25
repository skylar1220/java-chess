package chess.controller;

import chess.domain.Command;
import java.util.Arrays;
import java.util.List;

public record CommandDto(Command command, String source, String target) {

    public static final int MOVE_COMMAND_PART_COUNT = 3;
    public static final int MOVE_COMMAND_POSITION_START_INDEX = 1;
    public static final int MOVE_COMMAND_MESSAGE_INDEX = 0;

    public static CommandDto from(String input) {
        if (input.equals(Command.START.getMessage())) {
            return new CommandDto(Command.START, "", "");
        }
        if (input.equals(Command.END.getMessage())) {
            return new CommandDto(Command.END, "", "");
        }

        List<String> splits = Arrays.asList(input.split(" "));
        validateMoveCommand(splits);
        String source = splits.get(1);
        String target = splits.get(2);
        return new CommandDto(Command.MOVE, source, target);
    }

    private static void validateMoveCommand(final List<String> splittedInput) {
        validateMoveCommandPartCount(splittedInput);
        validateMoveCommandMessage(splittedInput);
        validateRankNumeric(splittedInput);
    }

    private static void validateMoveCommandPartCount(final List<String> splittedInput) {
        if (splittedInput.size() != MOVE_COMMAND_PART_COUNT) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 입력입니다.");
        }
    }

    private static void validateMoveCommandMessage(final List<String> splittedInput) {
        if (!splittedInput.get(MOVE_COMMAND_MESSAGE_INDEX).equals(Command.MOVE.getMessage())) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 입력입니다.");
        }
    }

    private static void validateRankNumeric(final List<String> splittedInput) {
        splittedInput.subList(MOVE_COMMAND_POSITION_START_INDEX, MOVE_COMMAND_PART_COUNT)
                .forEach(position -> validateNumeric(position.substring(MOVE_COMMAND_POSITION_START_INDEX)));
    }

    private static void validateNumeric(final String input) {
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 입력입니다.");
        }
    }
}
