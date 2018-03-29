public enum Operator {
    EXPONENT('@', 3),
    MULTIPLY('*', 2),
    DIVIDE('/', 2),
    MODULUS('%', 2),
    ADD('+', 1),
    SUBTRACT('-', 1),
    RIGHT_PARENTHESIS(')', 99),
    LEFT_PARENTHESIS('(', -99),
    BAD_OPERATOR('~', -99),
    END('#', -100);

    private char character;
    private int priority;

    Operator(char character, int priority) {
        this.character = character;
        this.priority = priority;
    }

    public char getCharacter() {
        return character;
    }

    protected int getPriority() {
        return priority;
    }

    //Takes in a character and returns an enum value with the matching char.
    static Operator findOperator(char c) {
        Operator[] values = Operator.values();

        int i = 0;
        boolean found = false;

        while (!found && (i < values.length)) {
            found = values[i].character == c;
            i++;
        }

        if (!found) {
            return Operator.BAD_OPERATOR;
        }

        return values[i-1];
    }
}
