package chess.db.dao;

import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import chess.domain.piece.Position;
import chess.db.entity.SquareEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class SquareDao {

    public List<SquareEntity> findSquares() {
        final String query = "SELECT * FROM square WHERE chessGame_id = 1 ORDER BY position";

        try (final Connection connection = ChessDbConnector.getConnection();
             final PreparedStatement statement = connection.prepareStatement(query)) {

            final ResultSet resultSet = statement.executeQuery();
            final List<SquareEntity> squareEntities = new ArrayList<>();

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

    public void addSquares(final Map<Position, Piece> chessBoard) {
        final String query = "INSERT INTO square (position, pieceType, color, chessGame_id) VALUES (?, ?, ?, 1)";

        try (final Connection connection = ChessDbConnector.getConnection();
             final PreparedStatement statement = connection.prepareStatement(query)) {

            final List<SquareEntity> squareEntities = convertToEntities(chessBoard);

            for (SquareEntity squareEntity : squareEntities) {
                statement.setString(1, squareEntity.position());
                statement.setString(2, squareEntity.pieceType().name());
                statement.setString(3, squareEntity.color().name());

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteSquares() {
        final String query = "DELETE FROM square WHERE chessGame_id = 1";

        try (final Connection connection = ChessDbConnector.getConnection();
             final PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<SquareEntity> convertToEntities(final Map<Position, Piece> chessBoard) {
        final List<SquareEntity> squareEntities = convertToEntities(chessBoard);

        for (Entry<Position, Piece> positionPiece : chessBoard.entrySet()) {
            final Position rawPosition = positionPiece.getKey();
            final Piece rawPiece = positionPiece.getValue();

            final String position = rawPosition.getFile().getSymbol() + rawPosition.getRank().getIndex();
            final PieceType pieceType = rawPiece.getPieceType();
            final Color color = rawPiece.getColor();

            squareEntities.add(new SquareEntity(position, pieceType, color));
        }
        return squareEntities;
    }
}
