package chess.domain.piece.type;

import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import chess.domain.piece.Position;
import java.util.Map;
import java.util.Set;

public class Empty extends Piece {

    public Empty() {
        super(Color.EMPTY);
    }

    @Override
    public Set<Position> getPositions(final Position sourcePosition, final Map<Position, Piece> pieces) {
        return Set.of();
    }

    @Override
    public boolean isType(final PieceType pieceType) {
        return false;
    }
}
