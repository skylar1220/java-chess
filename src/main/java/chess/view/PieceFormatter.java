package chess.view;

import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;

public class PieceFormatter {
    private PieceFormatter() {
    }

    public static String convertToMark(final Piece piece) {
        if (piece.isType(PieceType.BISHOP) && piece.isBlack()) {
            return "B";
        }
        if (piece.isType(PieceType.BISHOP) && piece.isWhite()) {
            return "b";
        }
        if (piece.isType(PieceType.KING) && piece.isBlack()) {
            return "K";
        }
        if (piece.isType(PieceType.KING) && piece.isWhite()) {
            return "k";
        }
        if (piece.isType(PieceType.KNIGHT) && piece.isBlack()) {
            return "N";
        }
        if (piece.isType(PieceType.KNIGHT) && piece.isWhite()) {
            return "n";
        }
        if (piece.isType(PieceType.PAWN) && piece.isBlack()) {
            return "P";
        }
        if (piece.isType(PieceType.PAWN) && piece.isWhite()) {
            return "p";
        }
        if (piece.isType(PieceType.QUEEN) && piece.isBlack()) {
            return "Q";
        }
        if (piece.isType(PieceType.QUEEN) && piece.isWhite()) {
            return "q";
        }
        if (piece.isType(PieceType.ROOK) && piece.isBlack()) {
            return "R";
        }
        if (piece.isType(PieceType.ROOK) && piece.isWhite()) {
            return "r";
        }
        return ".";
    }
}
