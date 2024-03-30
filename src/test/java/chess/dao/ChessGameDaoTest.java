package chess.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
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
}
