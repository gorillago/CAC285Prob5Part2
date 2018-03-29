public class Operand {
    private char character;
    private int[] value;

    public Operand(char character, int[] value) {
        this.character = character;
        this.value = value;
    }

    public char getCharacter() {
        return character;
    }

    public int[] getValue() {
        return value;
    }
}
