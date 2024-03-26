package chess.domain.piece.type;

import static chess.domain.Direction.DOWN_DOWN_LEFT;
import static chess.domain.Direction.DOWN_DOWN_RIGHT;
import static chess.domain.Direction.LEFT_LEFT_DOWN;
import static chess.domain.Direction.LEFT_LEFT_UP;
import static chess.domain.Direction.RIGHT_RIGHT_DOWN;
import static chess.domain.Direction.RIGHT_RIGHT_UP;
import static chess.domain.Direction.UP_UP_LEFT;
import static chess.domain.Direction.UP_UP_RIGHT;

import chess.domain.Direction;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import chess.domain.piece.Position;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Knight extends Piece {

    private static final Set<Direction> DIRECTIONS = Set.of(RIGHT_RIGHT_DOWN, RIGHT_RIGHT_UP, LEFT_LEFT_UP,
            LEFT_LEFT_DOWN, UP_UP_RIGHT, UP_UP_LEFT, DOWN_DOWN_RIGHT, DOWN_DOWN_LEFT);

    public Knight(final Color color) {
        super(color);
    }

    @Override
    public Set<Position> getPositions(final Position sourcePosition, final Map<Position, Piece> pieces) {
        Set<Position> positions = new HashSet<>();

        for (Direction direction : DIRECTIONS) {
            if (sourcePosition.canMove(direction) && (!pieces.get(sourcePosition.move(direction)).isExist() || !pieces.get(
                    sourcePosition.move(direction)).isMyColor(color))) { // 여기만 다름
                positions.add(sourcePosition.move(direction));
            }
        }
        return positions;
    }

    @Override
    public boolean isType(final PieceType pieceType) {
        return pieceType == PieceType.KNIGHT;
    }
}
