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

        if (command == Command.START) { // 1. 새로운 게임 시작한다고하면,
            chessGameDao.deleteGame();
            final ChessGame chessGame = new ChessGame();
            chessGameDao.addGame(chessGame); // 1. 게임이 세팅되면 db에 게임 아이디와 함께 새로운 게임이 생성
            outputView.printChessBoard(chessGame.getChessBoard());
            playWithCommand(chessGame, command);
            return;
        }
        if (command == Command.CONTINUE) { // 2. 저장된 게임 계속하기를 선택하면 이전에 있던 거 불러온다.
            final ChessGame chessGame = chessGameDao.findGame(); // 2. 불러와서 그 게임 진행함
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
            CommandDto commandDto = CommandDto.from(inputView.readCommand());
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
            chessGameDao.deleteGame(chessGame);
            return;
        }
        chessGameDao.saveGame(chessGame); // 3. end가 입력되면 지금까지 한 게임을 저장한다. 킹 잡은 경우 삭제.
    }

    private void playTurn(final ChessGame chessGame, final CommandDto commandDto) {
        chessGame.play(commandDto.source(), commandDto.target());
        outputView.printChessBoard(chessGame.getChessBoard());

    }
}
