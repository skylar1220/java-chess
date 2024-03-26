package chess.domain.piece.type;

import static chess.domain.Direction.LEFT_DOWN;
import static chess.domain.Direction.LEFT_UP;
import static chess.domain.Direction.RIGHT_DOWN;
import static chess.domain.Direction.RIGHT_UP;

import chess.domain.Direction;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import chess.domain.piece.Position;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Bishop extends Piece {

    private static final Set<Direction> DIRECTIONS = Set.of(RIGHT_UP, RIGHT_DOWN, LEFT_UP, LEFT_DOWN);

    public Bishop(final Color color) {
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
        return pieceType == PieceType.BISHOP;
    }
}
