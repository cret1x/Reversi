import java.util.Scanner;

public final class Util {
    private static final double cornerValue = 0.8;
    private static final double edgeValue = 0.4;
    private static final double cellKillValue = 1.0;
    private static final double edgeKillValue = 2.0;

    /**
     * Prints simple menu list
     * @param header header
     * @param options options
     */
    public static void printMenu(String header, String[] options) {
        System.out.println("---------------------------------");
        System.out.println(header);
        for (int i = 0; i < options.length; i++) {
            System.out.println((i+1) + ") " + options[i]);
        }
        System.out.println("---------------------------------");
    }

    /**
     * Gets user input
     * @param optionsCount number of all options
     * @return selected option number
     */
    public static int getUserMenuInput(int optionsCount) {
        Scanner sc = new Scanner(System.in);
        int opt;
        do {
            try {
                opt = Integer.parseInt(sc.nextLine());
            } catch (Exception ex) {
                opt = -1;
            }
            if (opt < 1 || opt > optionsCount)
                System.out.println("Incorrect option");
        } while (opt < 1 || opt > optionsCount);
        return opt;
    }

    /**
     * Calculates value of possible move
     * @param board game board
     * @param cell cell to move to
     * @param turn current turn
     * @return value of move
     */
    public static double getMovementValue(Board board, Coords cell, int turn) {
        double currentValue = 0;
        if (cell.x() == 0 && cell.y() == 0)
            currentValue += cornerValue;
        else if (cell.x() == 7 && cell.y() == 7)
            currentValue += cornerValue;
        else if (cell.x() == 0 && cell.y() == 7)
            currentValue += cornerValue;
        else if (cell.x() == 7 && cell.y() == 0)
            currentValue += cornerValue;
        else if (cell.x() == 0 || cell.x() == 7)
            currentValue += edgeValue;
        else if (cell.y() == 0 || cell.y() == 7)
            currentValue += edgeValue;
        var cells = board.getKilledCells(cell, turn);
        for (var c : cells) {
            if (c.x() == 0 || c.x() == 7)
                currentValue += edgeKillValue;
            else if (c.y() == 0 || c.y() == 7)
                currentValue += edgeKillValue;
            else
                currentValue += cellKillValue;
        }
        return currentValue;
    }
}
