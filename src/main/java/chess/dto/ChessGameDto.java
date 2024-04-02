package chess.db.entity;

import chess.domain.piece.Color;
import java.util.List;

public record ChessGameEntity(List<SquareEntity> chessBoard, Color currentColor) {
}
