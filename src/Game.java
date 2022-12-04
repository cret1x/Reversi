import java.util.Scanner;

public final class Game {
    private Board board;
    private final Scanner sc;
    private boolean isExit;
    private String maxScoreString = "No results yet!";
    private int maxScore = 0;

    /**
     * Constructor
     */
    public Game() {
        sc = new Scanner(System.in);
        isExit = false;
    }

    /**
     * Main game loop
     */
    public void loop() {
        while (!isExit) {
            Util.printMenu("Select option:", new String[]{"New game", "Show best score", "Exit game"});
            int menu = Util.getUserMenuInput(3);
            if (menu == 3)
                isExit = true;
            else if (menu == 2) {
                System.out.println(maxScoreString);
                System.out.println("Press ENTER to continue");
                sc.nextLine();
            } else if (menu == 1) {
                chooseDifficulty();
            }
        }
        System.out.println("Goodbye!");
    }

    /**
     * Choose difficulty menu
     */
    private void chooseDifficulty() {
        Util.printMenu("Choose Difficulty:", new String[]{
                "Can I play, Daddy? (easy)",
                "I am Death incarnate! (hard)",
                "I hate my friends (PvP)",
                "Return back"
        });
        int menu = Util.getUserMenuInput(4);
        if (menu == 4)
            return;
        board = new Board();
        System.out.print("Enter first player name: ");
        board.playerOne = new Player(sc.nextLine());
        if (menu == 1) {
            board.playerTwo = new Nemesis(1);
        } else if (menu == 2) {
            board.playerTwo = new Nemesis(2);
        } else {
            System.out.print("Enter second player name: ");
            board.playerTwo = new Player(sc.nextLine());
        }
        start();
    }

    /**
     * New game main loop
     */
    private void start() {
        int currentTurn = 0;
        int noMovesCount = 0;
        board.saveBoardOne();
        while (!board.isFilled()) {
            var movements = board.predict(currentTurn);
            System.out.println("-----------------------------------");
            System.out.println("Turn: #" + currentTurn);
            board.printBoard();
            board.printScore();
            if (board.playerOne.getScore() == 0 || board.playerTwo.getScore() == 0) {
                break;
            }
            if (movements.size() == 0) {
                System.out.println("No available movements! Next turn...");
                currentTurn++;
                noMovesCount++;
                if (noMovesCount >= 2) {
                    System.out.println("No available movements for both players.");
                    break;
                }
                continue;
            }
            noMovesCount = 0;

            Coords currentMove;
            if (currentTurn % 2 == 0) {
                System.out.println(board.playerOne.name + " Turn");
                currentMove = board.playerOne.move(movements, board, currentTurn);
            } else {
                System.out.println(board.playerTwo.name + " Turn");
                currentMove = board.playerTwo.move(movements, board, currentTurn);
                if (!board.playerTwo.isPlayer)
                    System.out.println("Moved to: " + currentMove);
            }
            if (currentMove.y() == -1) {
                if (currentMove.x() == -1) {
                    break;
                } else {
                    System.out.println("Paid option: 5$ to cancel your move");
                    continue;
                }
            }
            board.apply(currentMove, currentTurn);
            currentTurn++;
        }
        if (board.playerOne.getScore() > board.playerTwo.getScore()) {
            if (board.playerOne.getScore() > maxScore) {
                maxScoreString = board.playerOne.getScoreBoardLine();
                maxScore = board.playerOne.getScore();
            }
            System.out.println(board.playerOne.name + " won!");
        } else if (board.playerOne.getScore() < board.playerTwo.getScore()){
            System.out.println(board.playerTwo.name + " won!");
            if (board.playerTwo.isPlayer) {
                if (board.playerTwo.getScore() > maxScore) {
                    maxScoreString = board.playerTwo.getScoreBoardLine();
                    maxScore = board.playerTwo.getScore();
                }
            }
        } else {
            System.out.println("Its a tie!");
        }
    }
}
