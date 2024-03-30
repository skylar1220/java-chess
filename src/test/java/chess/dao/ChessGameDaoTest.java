package chess.dao;

import static chess.Fixtures.A4;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import chess.domain.ChessBoard;
import chess.domain.ChessGame;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.piece.Position;
import chess.domain.piece.type.Queen;
import java.sql.Connection;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ChessGameDaoTest {

    private final ChessGameDao chessGameDao = new ChessGameDao();

    @DisplayName("db 연결을 확인한다.")
    @Test
    void connection() {
        // given && when
        final Connection connection = chessGameDao.getConnection();

        // then
        assertThat(connection).isNotNull();
    }

    @DisplayName("저장된 게임을 불러온다.")
    @Test
    void findGame() {
        // given && when
        final ChessGame chessGame = chessGameDao.findGame();
        final Color currentColor = chessGame.getCurrentColor();

        // then
        assertThat(currentColor).isEqualTo(Color.WHITE);
    }

    @DisplayName("새로 만들어진 게임을 추가한다.")
    @Test
    void addGame() {
        // given
        final Map<Position, Piece> pieces = Map.of(A4, new Queen(Color.WHITE));
        final ChessBoard rawChessBoard = new ChessBoard(pieces);
        final ChessGame rawChessGame = new ChessGame(rawChessBoard);

        // when
        chessGameDao.addGame(rawChessGame);
        ChessGame chessGame = chessGameDao.findGame();

        final Map<Position, Piece> chessBoard = chessGame.getChessBoard();
        final Color currentColor = chessGame.getCurrentColor();

        // then
        assertAll(
                () -> assertThat(chessBoard).containsEntry(A4, new Queen(Color.WHITE)),
                () -> assertThat(currentColor).isEqualTo(Color.WHITE));
    }

    @DisplayName("왕이 잡힌후 게임이 종료되면 저장된 게임은 삭제된다.")
    @Test
    void deleteGame() {
        // given
        final ChessGame rawChessGame = new ChessGame();

        // when
        chessGameDao.deleteGame(rawChessGame);

        // then
        assertThat(chessGameDao.findGame()).isNull();
    }
}
