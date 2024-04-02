package chess.dto;

import chess.domain.ChessBoard;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.piece.Position;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ChessGameDto {
    private final ChessBoard chessBoard;
    private final Color currentColor;

    public ChessGameDto(List<SquareDto> chessBoard, Color currentColor) {
        this.chessBoard = convertToBoard(chessBoard);
        this.currentColor = currentColor;
    }

    private static ChessBoard convertToBoard(final List<SquareDto> chessBoard) {
        Map<Position, Piece> pieces = chessBoard.stream()
                .collect(Collectors.toMap(
                        squareDto -> Position.from(squareDto.position()),
                        squareDto -> Piece.of(squareDto.pieceType(), squareDto.color()),
                        (o1, o2) -> o1,
                        LinkedHashMap::new
                ));
        return new ChessBoard(pieces);
    }

    public ChessBoard getChessBoard() {
        return chessBoard;
    }

    public Color getCurrentColor() {
        return currentColor;
    }
}
