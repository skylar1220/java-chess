package chess.domain;

import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.piece.Position;
import java.util.Map;

public class ChessGame {

    private final ChessBoard chessBoard;
    private Color currentColor;

    public ChessGame() {
        this.chessBoard = ChessBoardMaker.init();
        this.currentColor = Color.WHITE;
    }

    ChessGame(final ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
        this.currentColor = Color.WHITE;
    }

    public void play(final String sourcePosition, final String targetPosition) {
        validateTurn(sourcePosition);
        chessBoard.move(sourcePosition, targetPosition);
        currentColor = currentColor.opposite();
    }

    public boolean isEnd() {
        return chessBoard.doesKingDead();
    }

    private void validateTurn(final String sourcePosition) {
        if (!chessBoard.isPieceColor(sourcePosition, currentColor)) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 순서입니다.");
        }
    }

    public Map<Position, Piece> getChessBoard() {
        return chessBoard.getPieces();
    }

    public Map<Color, Double> getScore() {
        return chessBoard.getScores();
    }
}
