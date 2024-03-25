package chess.controller;

import chess.domain.ChessBoard;
import chess.domain.ChessBoardMaker;
import chess.domain.Command;
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
        final ChessBoard chessBoard = ChessBoardMaker.init();

        playWithCommand(chessBoard);
    }

    private void playWithCommand(final ChessBoard chessBoard) {
        while (true) {
            CommandDto commandDto = CommandDto.from(inputView.readCommand());
            Command command = commandDto.command();

            if (command == Command.START) {
                outputView.printChessBoard(chessBoard.getPieces());
            }
            if (command == Command.MOVE) {
                playTurn(chessBoard, commandDto);
            }
            if (command == Command.END) {
                return;
            }
        }
    }

    private void playTurn(final ChessBoard chessBoard, final CommandDto commandDto) {
        chessBoard.move(commandDto.source(), commandDto.target());
        outputView.printChessBoard(chessBoard.getPieces());
    }
}
