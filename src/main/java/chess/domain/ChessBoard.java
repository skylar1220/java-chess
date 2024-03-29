package chess.domain;

import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import chess.domain.piece.Position;
import chess.domain.piece.type.Empty;
import java.util.EnumMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class ChessBoard {

    private final Map<Position, Piece> pieces;
    private final Map<Color, Double> scores;

    public ChessBoard(final Map<Position, Piece> pieces) {
        this.pieces = pieces;
        this.scores = new EnumMap<>(Color.class);
    }
//
//    private Map<Color, Double> initScore() {
//        Map<Color, Double> scores = new HashMap<>();
//        scores.put(Color.BLACK, 0.0);
//        scores.put(Color.WHITE, 0.0);
//        return scores;
//    }

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

        pieces.put(targetPosition, sourcePiece);
        pieces.put(sourcePosition, new Empty());

        scores.put(Color.WHITE, calculateScore(Color.WHITE));
        scores.put(Color.BLACK, calculateScore( Color.BLACK));
    }

    private double calculateScore(final Color color) {
        double score = 0;
        for (Entry<Position, Piece> positionPiece : pieces.entrySet()) {
            Piece piece = positionPiece.getValue();
            Position position = positionPiece.getKey();

            if (piece.isMyColor(color)) {
                score += piece.getScore(hasSameFilePawn3(position, piece));
            }
        }
        return score;
    }

    private boolean hasSameFilePawn3(final Position position, final Piece piece) {
        long count = pieces.entrySet().stream()
                .filter(positionPiece -> positionPiece.getKey().isSameFile(position)
                        && positionPiece.getValue().isType(PieceType.PAWN)
                        && positionPiece.getValue().isMyColor(piece))
                .count();
        return count > 1;
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
