package chess.dao;

import chess.domain.ChessGame;
import chess.domain.piece.Color;
import chess.entity.SquareEntity;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ChessGameDao {

    // TODO: 클래스로 분리하기
    public static final String SERVER = "localhost:3306"; // MySQL 서버 주소
    public static final String DATABASE = "chess"; // MySQL DATABASE 이름
    public static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    public static final String USERNAME = "username"; //  MySQL 서버 아이디
    public static final String PASSWORD = "password"; // MySQL 서버 비밀번호

    public final SquareDao squareDao = new SquareDao();

    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public ChessGame findGame() {
        try {
            final Connection connection = getConnection();
            final PreparedStatement statement = connection.prepareStatement("SELECT * FROM chessGame");

            final ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String chessGameId = resultSet.getString("chessGame_id");
                Color currentColor = Color.valueOf(resultSet.getString("currentColor"));
                List<SquareEntity> chessBoard = squareDao.findSquares(chessGameId);
                return new ChessGame(chessBoard, currentColor);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void addGame(final ChessGame chessGame) {
        final String query = "INSERT INTO chessGame (chessGame_id, currentColor) VALUES (? , ?)";
        try {
            final Connection connection = getConnection();
            final PreparedStatement statement = connection.prepareStatement(query);

            final String chessGame_id = "1";       // TODO: 저장가능 게임이 하나인 상황으로 우선 구현

            statement.setString(1, chessGame_id);
            statement.setString(2, chessGame.getCurrentColor().name());

            squareDao.addSquares(chessGame.getChessBoard(), chessGame_id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
