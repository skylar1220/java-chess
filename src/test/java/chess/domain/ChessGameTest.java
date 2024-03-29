package chess.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.entry;

import chess.Fixtures;
import chess.domain.piece.Color;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ChessGameTest {

    @DisplayName("블랙 기물이 먼저 움직이면 예외를 발생한다.")
    @Test
    void blackFirst() {
        // given
        final ChessGame chessGame = new ChessGame();

        // when && then
        assertThatThrownBy(() -> chessGame.play("a7", "a6")).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("화이트 기물이 먼저 움직이면 예외를 발생하지 않는다.")
    @Test
    void whiteFirst() {
        // given
        final ChessGame chessGame = new ChessGame();

        // when && then
        assertThatCode(() -> chessGame.play("a2", "a3")).doesNotThrowAnyException();
    }

    @DisplayName("화이트 - 화이트 기물 순서로 움직이려고 하면 예외를 발생한다.")
    @Test
    void white_white() {
        // given
        final ChessGame chessGame = new ChessGame();

        // when
        chessGame.play("a2", "a3");

        // then
        assertThatThrownBy(() -> chessGame.play("a3", "a4")).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("화이트 - 블랙 기물 순서로 움직이려고 하면 예외를 발생하지 않는다.")
    @Test
    void white_black() {
        // given
        final ChessGame chessGame = new ChessGame();

        // when
        chessGame.play("a2", "a3");

        // then
        assertThatCode(() -> chessGame.play("a7", "a6")).doesNotThrowAnyException();
    }

    @DisplayName("화이트 - 블랙 - 블랙 기물 순서로 움직이려고 하면 예외를 발생한다.")
    @Test
    void white_black_black() {
        // given
        final ChessGame chessGame = new ChessGame();

        // when
        chessGame.play("a2", "a3");
        chessGame.play("a7", "a6");

        // then
        assertThatThrownBy(() -> chessGame.play("a6", "a5")).isInstanceOf(IllegalArgumentException.class);
    }

    /*
    RNB.KBNR
    ...P.PPP
    .P......
    P.P.q.b. -> 3. q가 N을 잡는다. 2.5
    .rpQP.p. -> 2. Q가 p를 잡는다. 0.5
    p.n.kp.. -> 1. p가 P를 잡는다. 1
    .p.pp..p
    ..b...nr
     */
    @DisplayName("잡힌 말들의 점수를 구한다.")
    @Test
    void getScore() {
        // given
        final ChessBoard chessBoard = new ChessBoard(new HashMap<>(Fixtures.GAMING_PIECES));
        final ChessGame chessGame = new ChessGame(chessBoard);

        // when
        chessGame.play("f3", "e4");
        chessGame.play("d4", "e4");
        chessGame.play("e5", "b8");
        Map<Color, Double> scores = chessBoard.getScores();

        // then
        assertThat(scores).contains(entry(Color.WHITE, 3.5), entry(Color.BLACK, 0.5));
    }
}
