package chess.domain.piece.type;

import static chess.domain.Direction.LEFT_UP;
import static chess.domain.Direction.RIGHT_UP;
import static chess.domain.Direction.UP;
import static chess.domain.Direction.UP_UP;

import chess.domain.Direction;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import chess.domain.piece.Position;
import java.util.Map;
import java.util.Set;

public abstract class Pawn extends Piece {

    public Pawn(final Color color) {
        super(color);
    }

    @Override
    protected Set<Direction> directions() {
        return Set.of(UP, UP_UP, RIGHT_UP, LEFT_UP);
    }

    @Override
    protected Set<Position> getPositionsByDirection(final Direction direction, final Position sourcePosition,
                                                    final Map<Position, Piece> pieces) {
        return getPositions(sourcePosition, pieces);
    }

    public abstract Set<Position> getPositions(final Position sourcePosition, final Map<Position, Piece> pieces);

    @Override
    public boolean isType(final PieceType pieceType) {
        return pieceType == PieceType.PAWN;
    }
}
