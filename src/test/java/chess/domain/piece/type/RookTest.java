package chess.domain.piece.type;

import static org.assertj.core.api.Assertions.assertThat;

import chess.domain.piece.Color;
import chess.domain.piece.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RookTest {

    @DisplayName("룩을 직선으로 이동한다.")
    @Test
    void canMove() {
        // given
        final Rook rook = new Rook(Color.BLACK, new Position('d', 5));

        // when
        boolean canMove = rook.canMoveTo(new Position('d', 6)); // 상

        // then
        assertThat(canMove).isTrue();
    }

    @DisplayName("룩은 직선 이외로는 이동할 수 없다.") // TODO: 대각선+직선 이동 케이스 추가
    @Test
    void canNotMove() {
        // given
        final Rook rook = new Rook(Color.BLACK, new Position('d', 5));

        // when
        boolean canMove = rook.canMoveTo(new Position('e', 4)); // 대각선 위

        // then
        assertThat(canMove).isFalse();
    }
}
