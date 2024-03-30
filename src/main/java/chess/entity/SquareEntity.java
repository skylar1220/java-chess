package chess.entity;

import chess.domain.piece.Color;
import chess.domain.piece.PieceType;

public record SquareEntity(String position, PieceType pieceType, Color color) {
}
