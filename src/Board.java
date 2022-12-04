import java.util.ArrayList;
import java.util.HashMap;

public final class Board {
    private final Cell[][] board;
    private final Cell[][] boardOldOne;
    private final Cell[][] boardOldTwo;
    public Entity playerOne;
    public Entity playerTwo;
    public static final int size = 8;

    /**
     * Board constructor
     */
    public Board() {
        board = new Cell[size][size];
        boardOldOne = new Cell[size][size];
        boardOldTwo = new Cell[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = new Cell(0);
            }
        }
        board[3][3].setBlack();
        board[4][4].setBlack();
        board[3][4].setWhite();
        board[4][3].setWhite();
    }

    /**
     * Checks if board is full
     * @return True if full
     */
    public boolean isFilled() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j].getTypeCode() == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Predicts all possible movements
     * @param turn current turn
     * @return map of possible moves
     */
    public HashMap<Integer, Coords> predict(int turn) {
        HashMap<Integer, Coords> map = new HashMap<>();
        int counter = 0;
        int matchType = turn % 2 == 0 ? 1 : 2;
        boolean edgeFlag = false;
        int playerOneScore = 0;
        int playerTwoScore = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j].getTypeCode() == 1)
                    playerOneScore++;
                if (board[i][j].getTypeCode() == 2)
                    playerTwoScore++;
                if (board[i][j].getTypeCode() == matchType) {
                    for (int x = Math.max(i - 1, 0); x <= Math.min(i + 1, size - 1); x++) {
                        for (int y = Math.max(j - 1, 0); y <= Math.min(j + 1, size - 1); y++) {
                            if (x == i && y == j)
                                continue;
                            if (board[x][y].getTypeCode() == 3 - matchType) {
                                int deltaX = x - i;
                                int deltaY = y - j;
                                int xx = x;
                                int yy = y;
                                while (board[xx][yy].getTypeCode() != 0) {
                                    xx += deltaX;
                                    yy += deltaY;
                                    if (xx >= size || yy >= size || xx < 0 || yy < 0) {
                                        if (xx >= size) xx--;
                                        if (yy >= size) yy--;
                                        if (xx < 0) xx++;
                                        if (yy < 0) yy++;
                                        edgeFlag = true;
                                        break;
                                    }
                                }
                                if (board[xx - deltaX][yy -deltaY].getTypeCode() == 3 - matchType) {
                                    if (!map.containsValue(new Coords(xx, yy)) && !edgeFlag) {
                                        map.put(counter, new Coords(xx, yy));
                                        counter++;
                                    }
                                }
                                edgeFlag = false;
                            }
                        }
                    }
                }
            }
        }
        playerOne.setScore(playerOneScore);
        playerTwo.setScore(playerTwoScore);
        for (Coords c : map.values()) {
            if (turn % 2 == 0)
                board[c.x()][c.y()].setWhitePredict();
            else
                board[c.x()][c.y()].setBlackPredict();
        }
        return map;
    }

    /**
     * Based on movement returns all killed cells
     * @param update movement
     * @param turn current turn
     * @return ArrayList of all killed cells
     */
    public ArrayList<Coords> getKilledCells(Coords update, int turn) {
        var cells = new ArrayList<Coords>();
        int matchType = turn % 2 == 0 ? 1 : 2;
        for (int x = Math.max(update.x() - 1, 0); x <= Math.min(update.x() + 1, size - 1); x++) {
            for (int y = Math.max(update.y() - 1, 0); y <= Math.min(update.y() + 1, size - 1); y++) {
                if (x == update.x() && y == update.y())
                    continue;
                if (board[x][y].getTypeCode() == 3 - matchType) {
                    int deltaX = x - update.x();
                    int deltaY = y - update.y();
                    int xx = x;
                    int yy = y;
                    var tmp = new ArrayList<Coords>();
                    while (board[xx][yy].getTypeCode() != 0) {
                        tmp.add(new Coords(xx, yy));
                        xx += deltaX;
                        yy += deltaY;
                        if (xx >= size || yy >= size || xx < 0 || yy < 0) {
                            if (xx >= size) xx--;
                            if (yy >= size) yy--;
                            if (xx < 0) xx++;
                            if (yy < 0) yy++;
                            break;
                        }
                        if (board[xx][yy].getTypeCode() == matchType)
                            break;
                    }
                    if (board[xx][yy].getTypeCode() == matchType) {
                        cells.addAll(tmp);
                    }
                    tmp.clear();
                }
            }
        }
        return cells;
    }

    /**
     * Colors killed cells
     * @param update movement
     * @param turn current turn
     */
    public void apply(Coords update, int turn) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j].getTypeCode() == 3 || board[i][j].getTypeCode() == 4) {
                    board[i][j].setEmpty();
                }
            }
        }
        if (turn % 2 == 0)
            saveBoardOne();
        else
            saveBoardTwo();
        if (turn % 2 == 0)
            board[update.x()][update.y()].setWhite();
        else
            board[update.x()][update.y()].setBlack();
        var cellsToKill = getKilledCells(update, turn);
        for (Coords cell: cellsToKill) {
            if (turn % 2 == 0)
                board[cell.x()][cell.y()].setWhite();
            else
                board[cell.x()][cell.y()].setBlack();
        }
    }

    /**
     * Prints the board wow
     */
    public void printBoard() {
        System.out.println("x\\y0   1   2   3   4   5   6   7");
        for (int i = 0; i < size; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < size; j++) {
                System.out.print(board[i][j].toString());
            }
            System.out.println();
        }
    }

    /**
     * Prints the score
     */
    public void printScore() {
        System.out.println(playerOne.getScoreBoardLine());
        System.out.println(playerTwo.getScoreBoardLine());
    }

    /**
     * 4 methods below are used to implement movement cancel functions
     * Not used currently
     */
    public void rollBackOne() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = new Cell(boardOldOne[i][j]);
            }
        }
    }

    public void saveBoardOne() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                boardOldOne[i][j] = new Cell(board[i][j]);
            }
        }
    }

    public void rollBackTwo() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = new Cell(boardOldTwo[i][j]);
            }
        }
    }

    public void saveBoardTwo() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                boardOldTwo[i][j] = new Cell(board[i][j]);
            }
        }
    }

    /**
     * Clones content of current board into param
     * @param board dst
     */
    public void cloneBoardContent(Board board) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board.board[i][j] = new Cell(this.board[i][j]);
            }
        }
    }
}
