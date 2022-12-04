public final class Cell {
    private Character type;
    private int typeCode;

    /**
     * Cell constructor
     * @param type type of cell
     */
    public Cell(int type) {
        typeCode = type;
        this.type = switch (type) {
            case 1 -> '\u25CF';
            case 2 -> '\u25A0';
            case 3 -> '\u25CC';
            case 4 -> '\u25A1';
            default -> ' ';
        };
    }

    /**
     * Copy constructor
     * @param another another cell
     */
    public Cell(Cell another) {
        this.type = another.type;
        this.typeCode = another.typeCode;
    }

    /**
     * Marks cell as empty
     */
    public void setEmpty() {
        typeCode = 0;
        this.type = ' ';
    }

    /**
     * Marks cell as white
     */
    public void setWhite() {
        typeCode = 1;
        this.type = '\u25CF';
    }

    /**
     * Marks cell as black
     */
    public void setBlack() {
        typeCode = 2;
        this.type = '\u25A0';
    }

    /**
     * Marks cell as white predict
     */
    public void setWhitePredict() {
        typeCode = 3;
        this.type = '\u25CC';
    }

    /**
     * Marks cell as black predict
     */
    public void setBlackPredict() {
        typeCode = 4;
        this.type = '\u25A1';
    }

    /**
     * Return type code of cell
     * @return type code
     */
    public int getTypeCode() {
        return typeCode;
    }

    /**
     * To string method
     * @return string representation
     */
    public String toString() {
        return String.format("[%c] ", type);
    }
}
