import java.util.ArrayList;
import java.util.HashMap;

public final class Player extends Entity{

    /**
     * Player constructor
     * @param name player name
     */
    public Player(String name) {
        super(name, true);
    }

    /**
     * Gets user input movement
     * @param map all possible movements
     * @param board game board
     * @param turn current turn
     * @return selected movement
     */
    @Override
    public Coords move(HashMap<Integer, Coords> map, Board board, int turn) {
        ArrayList<String> possible = new java.util.ArrayList<>(map.values().stream().map(Coords::toString).toList());
        map.put(possible.size(), new Coords(-1,-1));
        possible.add("Stop the game");
        /* Stuff for canceling the movements (doesn't work)
        if (turn >= 2 ) {
            map.put(possible.size(), new Coords(0,-1));
            possible.add("Revert move");
        }
        */
        Util.printMenu("Choose cell: ", possible.toArray(String[]::new));
        int opt = Util.getUserMenuInput(possible.size()) - 1;
        return map.get(opt);
    }
}
