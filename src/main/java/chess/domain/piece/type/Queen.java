package chess.domain.piece.type;

import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.piece.Position;

public class Queen extends Piece {

    public Queen(final Color color, final Position position) {
        super(color, position);
    }

    @Override
    public boolean canMoveTo(final Position target) {
        return this.position.isStraightWith(target) || this.position.isDiagonalWith(target);
    }
}
