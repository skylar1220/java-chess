package chess.controller;

import chess.dao.ChessGameDao;
import chess.domain.ChessGame;
import chess.domain.Command;
import chess.dto.CommandDto;
import chess.view.InputView;
import chess.view.OutputView;

public class ChessController {

    private final InputView inputView;
    private final OutputView outputView;
    private final ChessGameDao chessGameDao;

    public ChessController() {
        this.inputView = new InputView();
        this.outputView = new OutputView();
        this.chessGameDao = new ChessGameDao();
    }

    public void runChess() {
        outputView.printStart();

        CommandDto commandDto = CommandDto.fromStart(inputView.readCommand());
        Command command = commandDto.command();

        if (command == Command.START) {
            chessGameDao.deleteGame();
            final ChessGame chessGame = new ChessGame();
            chessGameDao.addGame(chessGame);
            outputView.printChessBoard(chessGame.getChessBoard());
            playWithCommand(chessGame, command);
            return;
        }
        if (command == Command.CONTINUE) {
            final ChessGame chessGame = new ChessGame(chessGameDao.findGame());
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
        while (command != Command.END && !chessGame.doesKingDead()) {
            final CommandDto commandDto = CommandDto.from(inputView.readCommand());
            command = commandDto.command();

            if (command == Command.MOVE) {
                playTurn(chessGame, commandDto);
            }
            if (command == Command.STATUS) {
                outputView.printScore(chessGame.getScore());
                outputView.printWinnner(chessGame.getWinners());
            }
        }
        if (chessGame.doesKingDead()) {
            chessGameDao.deleteGame();
            return;
        }
        chessGameDao.saveGame(chessGame);
    }

    private void playTurn(final ChessGame chessGame, final CommandDto commandDto) {
        chessGame.play(commandDto.source(), commandDto.target());
        outputView.printChessBoard(chessGame.getChessBoard());
    }
}
