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

    public OperandType getType() {
        if (value.length == 1) {
            return OperandType.CONSTANT;
        } else if (value.length == 4) {
            return OperandType.MATRIX;
        }
        return OperandType.BAD_VARIABLE;
    }
}
