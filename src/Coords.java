
public record Coords(int x, int y) {

    /**
     * To String implementation
     * @return String representation
     */
    public String toString() {
        if (x == 0 && y < 0)
            return "Revert move";
        else if (x < 0 && y < 0)
            return "Stop the game";
        return "(" + x + "," + y + ")";
    }

    /**
     * Hash code of coords for HashMap
     * @return hashCode
     */
    @Override
    public int hashCode() {
        return Integer.parseInt(String.format("%d%d", x, y));
    }
}
