package chess.domain;

import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.piece.Position;
import chess.domain.piece.type.Empty;
import java.util.Map;
import java.util.Set;

public class ChessBoard {

    private Map<Position, Piece> pieces;

    public ChessBoard(final Map<Position, Piece> pieces) {
        this.pieces = pieces;
    }

    public boolean isPieceColor(final String sourcePosition, final Color color) {
        final Piece sourcePiece = findPieceBy(Position.from(sourcePosition));
        return sourcePiece.isMyColor(color);
    }

    public void move(final String sourcePosition, final String targetPosition) {
        move(Position.from(sourcePosition), Position.from(targetPosition));
    }
    public void move(final Position sourcePosition, final Position targetPosition) {
        final Piece sourcePiece = findPieceBy(sourcePosition);

        Set<Position> positions = sourcePiece.getPositions(sourcePosition, pieces);

        if (positions.contains(targetPosition)) {
            movePiece(sourcePosition, targetPosition);
            return;
        }

        throw new IllegalArgumentException("[ERROR] 이동할 수 없는 위치입니다.");
    }

    public Piece findPieceBy(final Position input) {
        if (isPieceExist(input)) {
            return pieces.get(input);
        }
        throw new IllegalArgumentException("[ERROR] 해당 위치에 기물이 존재하지 않습니다.");
    }

    private boolean isPieceExist(final Position input) {
        return !pieces.get(input).isClass(Empty.class);
    }

    private void movePiece(final Position sourcePosition, final Position targetPosition) {
        Piece sourcePiece = pieces.get(sourcePosition);

        pieces.put(targetPosition, sourcePiece);
        pieces.put(sourcePosition, new Empty());
    }

    public Map<Position, Piece> getPieces() {
        return pieces;
    }

    @Override
    public String toString() {
        return "ChessBoard{" +
                "pieces=" + pieces +
                '}';
    }
}
