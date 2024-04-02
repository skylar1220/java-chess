package chess.dto;

import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import chess.domain.piece.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public record SquareDto(String position, PieceType pieceType, Color color) {
    public static List<SquareDto> toSquareDtos(final Map<Position, Piece> chessBoard) {
        final List<SquareDto> squareEntities = new ArrayList<>();

        chessBoard.forEach((rawPosition, rawPiece) -> {
                    final String position = rawPosition.getFile() + rawPosition.getRank();
                    final PieceType pieceType = rawPiece.getPieceType();
                    final Color color = rawPiece.getColor();

                    squareEntities.add(new SquareDto(position, pieceType, color));
                }
        );
        return squareEntities;
    }
}
