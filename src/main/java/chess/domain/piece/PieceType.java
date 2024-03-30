package chess.domain.piece;

import chess.domain.piece.type.Bishop;
import chess.domain.piece.type.BlackPawn;
import chess.domain.piece.type.Empty;
import chess.domain.piece.type.King;
import chess.domain.piece.type.Knight;
import chess.domain.piece.type.Queen;
import chess.domain.piece.type.Rook;
import chess.domain.piece.type.WhitePawn;
import java.util.function.Function;

public enum PieceType { // TODO: 상호 참조 점검
    BISHOP(Bishop::new),
    KING(King::new),
    KNIGHT(Knight::new),
    PAWN(PieceType::convertPawn),
    QUEEN(Queen::new),
    ROOK(Rook::new),
    EMPTY(color -> new Empty());

    private static Piece convertPawn(final Color color) {
        if (color == Color.WHITE) {
            return new WhitePawn();
        }
        return new BlackPawn();
    }

    private final Function<Color, Piece> function;

    PieceType(final Function<Color, Piece> function) {
        this.function = function;
    }

    public Piece getPiece(Color color) {
        return function.apply(color);
    }
}
