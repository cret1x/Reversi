import java.util.HashMap;

public abstract class Entity {
    public String name;
    private int score;
    public boolean isPlayer;

    /**
     * Constructor for parent of Player and Nemesis
     * @param name entity name
     * @param isPlayer if it is player
     */
    public Entity(String name, boolean isPlayer) {
        this.name = name;
        this.isPlayer = isPlayer;
    }

    /**
     * Set score for entity
     * @param score score
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Get score for entity
     * @return score
     */
    public int getScore() {
        return score;
    }

    /**
     * Gets string line for scoreboard
     * @return string
     */
    public String getScoreBoardLine() {
        return String.format("<-- %s - %d points-- >", name, score);
    }

    /**
     * Move method
     * @param map all possible moves
     * @param board game board
     * @param turn current turn
     * @return selected move
     */
    public abstract Coords move(HashMap<Integer, Coords> map, Board board, int turn);
}
