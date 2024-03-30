package chess.dao;

import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import chess.domain.piece.Position;
import chess.entity.SquareEntity;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class SquareDao {

    public static final String SERVER = "localhost:3306"; // MySQL 서버 주소
    public static final String DATABASE = "chess"; // MySQL DATABASE 이름
    public static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    public static final String USERNAME = "username"; //  MySQL 서버 아이디
    public static final String PASSWORD = "password"; // MySQL 서버 비밀번호

    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<SquareEntity> findSquares(final String chessGameId) {
        try {
            final Connection connection = getConnection();
            final PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM square WHERE chessGame_id = ?");
            statement.setString(1, chessGameId);

            final ResultSet resultSet = statement.executeQuery();
            List<SquareEntity> squareEntities = new ArrayList<>();

            while (resultSet.next()) {
                String position = resultSet.getString("position");
                PieceType pieceType = PieceType.valueOf(resultSet.getString("pieceType"));
                Color color = Color.valueOf(resultSet.getString("color"));

                squareEntities.add(new SquareEntity(position, pieceType, color));
            }
            return squareEntities;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addSquares(final Map<Position, Piece> chessBoard, final String chessGame_id) {
        final String query = "INSERT INTO square (position, pieceType, color, chessGame_id) VALUES (?, ?, ?, ?)";
        try {
            final Connection connection = getConnection();
            final PreparedStatement statement = connection.prepareStatement(query);

            for (Entry<Position, Piece> positionPiece : chessBoard.entrySet()) {
                final Position rawPosition = positionPiece.getKey();
                final String position = rawPosition.getFile().getSymbol() + rawPosition.getRank().getIndex();
                final String pieceType = positionPiece.getValue().getPieceType().name();
                final String color = positionPiece.getValue().getColor().name();

                statement.setString(1, position);
                statement.setString(2, pieceType);
                statement.setString(3, color);
                statement.setString(4, chessGame_id);

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
