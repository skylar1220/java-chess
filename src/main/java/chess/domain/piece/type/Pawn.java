package chess.domain.piece.type;

import static chess.domain.Direction.DOWN;
import static chess.domain.Direction.DOWN_DOWN;
import static chess.domain.Direction.LEFT_DOWN;
import static chess.domain.Direction.LEFT_UP;
import static chess.domain.Direction.RIGHT_DOWN;
import static chess.domain.Direction.RIGHT_UP;
import static chess.domain.Direction.UP;
import static chess.domain.Direction.UP_UP;

import chess.domain.Direction;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import chess.domain.piece.Position;
import chess.domain.piece.Rank;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Pawn extends Piece {

    private static final Set<Direction> DIRECTIONS = Set.of(UP, UP_UP, RIGHT_UP, LEFT_UP);

    public Pawn(final Color color) {
        super(color);
    }

    @Override
    public Set<Position> getPositions(final Position sourcePosition, final Map<Position, Piece> pieces) {
        Set<Position> positions = new HashSet<>();

        // 처음이면 두칸 or 한칸 직선 - 근데 블랙폰, 화이트폰 분리
        // 아니면 한칸 직선
        // 잡을 땐 한칸 대각선

        if (color == Color.WHITE) {
            if (sourcePosition.canMove(RIGHT_UP) && pieces.get(sourcePosition.move(RIGHT_UP)).isExist() && !pieces.get(
                    sourcePosition.move(RIGHT_UP)).isMyColor(color)) {
                positions.add(sourcePosition.move(RIGHT_UP));
            }
            if (sourcePosition.canMove(LEFT_UP) && pieces.get(sourcePosition.move(LEFT_UP)).isExist() && !pieces.get(
                    sourcePosition.move(RIGHT_UP)).isMyColor(color)) {
                positions.add(sourcePosition.move(LEFT_UP));
            }
            if (sourcePosition.canMove(UP_UP) && sourcePosition.isSameRank(Rank.TWO)) {
                positions.add(sourcePosition.move(UP_UP));
            }
            if (sourcePosition.canMove(UP)) {
                positions.add(sourcePosition.move(Direction.UP));
            }
        }
        if (color == Color.BLACK) {
            if (sourcePosition.canMove(RIGHT_DOWN) && pieces.get(sourcePosition.move(RIGHT_DOWN)).isExist() && !pieces.get(sourcePosition.move(RIGHT_UP))
                    .isMyColor(color)) {
                positions.add(sourcePosition.move(RIGHT_DOWN));
            }
            if (sourcePosition.canMove(LEFT_DOWN) &&pieces.get(sourcePosition.move(LEFT_DOWN)).isExist() && !pieces.get(sourcePosition.move(RIGHT_UP))
                    .isMyColor(color)) {
                positions.add(sourcePosition.move(LEFT_DOWN));
            }
            if (sourcePosition.canMove(DOWN_DOWN) && sourcePosition.isSameRank(Rank.SEVEN)) {
                positions.add(sourcePosition.move(DOWN_DOWN));
            }
            if (sourcePosition.canMove(DOWN)) {
                positions.add(sourcePosition.move(DOWN));
            }
        }
        return positions;
    }

    @Override
    public boolean isType(final PieceType pieceType) {
        return pieceType == PieceType.PAWN;
    }
}
