package chess.domain.piece.type;

import static chess.domain.Direction.DOWN;
import static chess.domain.Direction.LEFT;
import static chess.domain.Direction.RIGHT;
import static chess.domain.Direction.UP;

import chess.domain.Direction;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import chess.domain.piece.Position;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Rook extends Piece {
    private static final Set<Direction> DIRECTIONS = Set.of(UP, DOWN, RIGHT, LEFT);

    public Rook(final Color color) {
        super(color);
    }

    @Override
    public Set<Position> getPositions(Position sourcePosition, final Map<Position, Piece> pieces) {
        Set<Position> positions = new HashSet<>();

        for (Direction direction : DIRECTIONS) {
            Position currentPosition = sourcePosition;

            while (currentPosition.canMove(direction)) {
                positions.add(currentPosition.move(direction));
                currentPosition = currentPosition.move(direction);

                if (pieces.get(currentPosition).isExist() || pieces.get(currentPosition).isMyColor(color)) {
                    break;
                }
            }
        }

        return positions;
    }

    @Override
    public boolean isType(final PieceType pieceType) {
        return pieceType == PieceType.ROOK;
    }
}
