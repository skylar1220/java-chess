package chess.view;

import chess.domain.Command;
import chess.domain.piece.Piece;
import chess.domain.piece.Position;
import chess.domain.piece.Rank;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class OutputView {

    public void printStart() {
        System.out.println("> 체스 게임을 시작합니다." + System.lineSeparator()
                + "> 게임 시작 : " + Command.START + System.lineSeparator()
                + "> 게임 종료 : " + Command.END + System.lineSeparator()
                + "> 게임 이동 :  " + Command.MOVE + " source위치 target위치 - 예. " + Command.MOVE + " b2 b3");
    }

    public void printChessBoard(final Map<Position, Piece> pieces) {
        final List<List<String>> board = sortByBoardOrder(pieces);
        board.forEach(this::printChessRow);
    }

    private void printChessRow(final List<String> row) {
        row.forEach(System.out::print);
        System.out.println();
    }

    private List<List<String>> sortByBoardOrder(final Map<Position, Piece> pieces) {
        final List<List<String>> board = new ArrayList<>();
        for (int i = 0; i < Rank.values().length; i++) {
            board.add(new ArrayList<>(List.of(".", ".", ".", ".", ".", ".", ".", ".")));
        }

        for (Entry<Position, Piece> positionPiece: pieces.entrySet()) {
            final Position position = positionPiece.getKey();
            final int fileIndex = position.getFile().getIndex() - 1;
            final int rankIndex = 7 - (position.getRank().getIndex() - 1);
            final List<String> marks = board.get(rankIndex);
            marks.set(fileIndex, PieceFormatter.convertToMark(positionPiece.getValue()));
        }

        return board;
    }
}
