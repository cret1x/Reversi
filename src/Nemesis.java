import java.util.HashMap;

public final class Nemesis extends Entity{
    private int difficulty;

    /**
     * Constructor for AI
     * @param difficulty AI difficulty level 1-2
     */
    public Nemesis(int difficulty) {
        super("Nemesis", false);
        this.difficulty = difficulty;
    }

    /**
     * Makes move based on difficulty and other stuff
     * @param map all possible moves
     * @param board game board
     * @param turn current turn
     * @return move that computer made
     */
    @Override
    public Coords move(HashMap<Integer, Coords> map, Board board, int turn) {
        double maxValue = 0;
        Coords nextMove = map.get(0);
        Board fakeBoard = new Board();
        fakeBoard.playerOne = new Nemesis(0);
        fakeBoard.playerTwo = new Nemesis(0);
        for (var cell: map.values()) {
            double currentValue = Util.getMovementValue(board, cell, 1);
            if (difficulty > 1) {
                board.cloneBoardContent(fakeBoard);
                fakeBoard.apply(cell, 1);
                double playerMaxValue = 0;
                var possiblePlayerMoves = fakeBoard.predict(0);
                for (var playerCell: possiblePlayerMoves.values()) {
                    double playerCurrentValue = Util.getMovementValue(fakeBoard, playerCell, 0);
                    if (playerCurrentValue > playerMaxValue) {
                        playerMaxValue = playerCurrentValue;
                    }
                }
                currentValue -= playerMaxValue;
            }
            if (currentValue > maxValue) {
                maxValue = currentValue;
                nextMove = cell;
            }
        }
        return nextMove;
    }
}
