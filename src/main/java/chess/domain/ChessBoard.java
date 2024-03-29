package chess.domain;

import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import chess.domain.piece.Position;
import chess.domain.piece.type.Empty;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ChessBoard {

    private final Map<Position, Piece> pieces;
    private final Map<Color, Double> scores;

    public ChessBoard(final Map<Position, Piece> pieces) {
        this.pieces = pieces;
        this.scores = new HashMap<>();
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

    public boolean doesKingDead() {
        int kingCount = (int) pieces.values().stream()
                .filter(piece -> piece.isType(PieceType.KING))
                .count();
        return kingCount != 2;
    }

    private boolean isPieceExist(final Position input) {
        return !pieces.get(input).isClass(Empty.class);
    }

    private void movePiece(final Position sourcePosition, final Position targetPosition) {
        Piece sourcePiece = pieces.get(sourcePosition);
        Piece targetPiece = pieces.get(targetPosition);

        pieces.put(targetPosition, sourcePiece);
        addScore(targetPosition, targetPiece);
        pieces.put(sourcePosition, new Empty());
    }

    private void addScore(final Position targetPosition, final Piece targetPiece) {
        Color color = pieces.get(targetPosition).getColor();

        if (targetPiece.isExist()) {
            double score = targetPiece.getScore(hasSameFilePawn(targetPosition));
            scores.put(color, scores.getOrDefault(color, 0.0) + score);
        }
    }

    private boolean hasSameFilePawn(final Position targetPosition) {
        Piece targetPiece = findPieceBy(targetPosition);
        return pieces.entrySet().stream()
                .anyMatch(positionPiece -> positionPiece.getKey().isSameFile(targetPosition)
                        && positionPiece.getValue().isType(PieceType.PAWN)
                        && positionPiece.getValue().isEnemyColor(targetPiece)
                );
    }

    public Map<Position, Piece> getPieces() {
        return pieces;
    }

    public Map<Color, Double> getScores() {
        return scores;
    }

    @Override
    public String toString() {
        return "ChessBoard{" +
                "pieces=" + pieces +
                '}';
    }
}
