package chess.domain.piece;

import chess.domain.piece.type.Empty;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public abstract class Piece {

    protected final Color color;

    protected Piece(final Color color) {
        this.color = color;
    }

    public abstract Set<Position> getPositions(final Position sourcePosition, final Map<Position, Piece> pieces);

    public abstract boolean isType(PieceType pieceType);

    public boolean isExist() {
        return !isEmpty();
    }

    public boolean isEmpty() {
        return getClass().equals(Empty.class);
    }

    public boolean isMyColor(final Color other) {
        return this.color == other;
    }

    public boolean isEnemyColor(final Color other) {
        return !isMyColor(other);
    }

    public boolean isBlack() {
        return this.color.equals(Color.BLACK);
    }

    public boolean isWhite() {
        return this.color.equals(Color.WHITE);
    }

    public boolean isClass(final Class<?> other) {
        return getClass().equals(other);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Piece piece = (Piece) o;
        return color == piece.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ":" + color;
    }
}
