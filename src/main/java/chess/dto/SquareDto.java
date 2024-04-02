package chess.dto;

import chess.domain.piece.Color;
import chess.domain.piece.PieceType;

public record SquareDto(String position, PieceType pieceType, Color color) {
}
