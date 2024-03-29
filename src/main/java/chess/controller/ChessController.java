package chess.controller;

import chess.domain.ChessGame;
import chess.domain.Command;
import chess.dto.CommandDto;
import chess.view.InputView;
import chess.view.OutputView;

public class ChessController {

    private final InputView inputView;
    private final OutputView outputView;

    public ChessController() {
        this.inputView = new InputView();
        this.outputView = new OutputView();
    }

    public void runChess() {
        outputView.printStart();
        final ChessGame chessGame = new ChessGame();

        CommandDto commandDto = CommandDto.fromStart(inputView.readCommand());
        Command command = commandDto.command();

        if (command == Command.START) {
            outputView.printChessBoard(chessGame.getChessBoard());
            playWithCommand(chessGame, command);
            return;
        }
        if (command == Command.END) {
            return;
        }

        throw new IllegalArgumentException("[ERROR] 유효하지 않은 입력입니다.");
    }

    private void playWithCommand(final ChessGame chessGame, Command command) {
        while (command != Command.END && !chessGame.isEnd()) {
            CommandDto commandDto = CommandDto.from(inputView.readCommand());
            command = commandDto.command();

            if (command == Command.MOVE) {
                playTurn(chessGame, commandDto);
            }
            if (command == Command.STATUS) {
                outputView.printResult(chessGame.getScore());
//                outputView.printResult(chessGame.getWinResult());
            }
        }

    }

    private void playTurn(final ChessGame chessGame, final CommandDto commandDto) {
        chessGame.play(commandDto.source(), commandDto.target());
        outputView.printChessBoard(chessGame.getChessBoard());

    }
}
