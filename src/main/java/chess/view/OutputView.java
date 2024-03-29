package chess.view;

import chess.domain.Command;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.piece.Position;
import chess.domain.piece.Rank;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OutputView {

    public void printStart() {
        System.out.println("> 체스 게임을 시작합니다." + System.lineSeparator()
                + "> 게임 시작 : " + Command.START + System.lineSeparator()
                + "> 게임 종료 : " + Command.END + System.lineSeparator()
                + "> 게임 이동 :  " + Command.MOVE + " source위치 target위치 - 예. " + Command.MOVE + " b2 b3");
    }

    public void printChessBoard(final Map<Position, Piece> pieces) {
        List<List<String>> rows = sortByBoardOrder(pieces);
        rows.forEach(this::printChessRow);
    }

    public void printResult(final Map<Color, Double> scores) {
        Double blackScore = scores.get(Color.BLACK);
        Double whiteScore = scores.get(Color.WHITE);

        System.out.printf("블랙 기물 점수: %.1f", blackScore);
        System.out.println();
        System.out.printf("화이트 기물 점수: %.1f", whiteScore);
    }

    private void printChessRow(final List<String> row) {
        row.forEach(System.out::print);
        System.out.println();
    }

    private List<List<String>> sortByBoardOrder(final Map<Position, Piece> pieces) {
        final List<List<String>> board = new ArrayList<>();

        for (int index = Rank.values().length; index > 0; index--) {
            Rank rank = Rank.fromNumber(index);

            List<String> row = pieces.entrySet().stream()
                    .filter(positionPiece -> positionPiece.getKey().isSameRank(rank))
                    .map(positionPiece -> PieceFormatter.convertToMark(positionPiece.getValue()))
                    .toList();

            board.add(row);
        }
        return board;
    }
}
