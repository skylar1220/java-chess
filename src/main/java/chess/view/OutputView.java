package chess.view;

import chess.domain.Command;
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

//        final List<List<String>> rows = Arrays.stream(Rank.values())
//                .map(rank -> pieces.entrySet().stream()
//                        .filter(positionPiece -> positionPiece.getKey().isSameRank(rank))
//                        .map(positionPiece -> PieceFormatter.convertToMark(positionPiece.getValue()))
//                        .toList())
//                .toList();
//
        rows.forEach(this::printChessRow);
    }

    private void printChessRow(final List<String> row) {
        row.forEach(System.out::print);
        System.out.println();
    }

    private List<List<String>> sortByBoardOrder(final Map<Position, Piece> pieces) {
        final List<List<String>> board = new ArrayList<>();

        for (int index = 8; index > 0; index--) {
            Rank rank = Rank.fromNumber(index);
            List<String> row = pieces.entrySet().stream()
                    .filter(positionPiece -> positionPiece.getKey().isSameRank(rank))
                    .map(positionPiece -> PieceFormatter.convertToMark(positionPiece.getValue()))
                    .toList();
            board.add(row);
        }

//        for (Entry<Position, Piece> positionPiece : pieces.entrySet()) {
//            final Position position = positionPiece.getKey();
//            final int fileIndex = position.getFile().getIndex() - 1;
//            final int rankIndex = 7 - (position.getRank().getIndex() - 1);
//            final List<String> marks = board.get(rankIndex);
//            marks.add(fileIndex, PieceFormatter.convertToMark(positionPiece.getValue()));
//        }

        return board;
    }
}
