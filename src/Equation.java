public class Equation {
    String equation;
    Operand[] variables;
    Operand[] constants;

    int[] solution;

    public Equation(String equation, Operand[] variables) {
        this.equation = equation;
        this.variables = variables;
        createConstants();
        solution = solve();
    }

    public int[] solve() {

        Stack<Operator> operatorStack = new Stack<>();
        Stack<int[]> operandStack = new Stack<>();

        char[] evaluate = (equation + "#").toCharArray();


        //Must start with end command.
        operatorStack.push(Operator.END);

        int i = 0;

        //While there are still characters to parse
        while (evaluate[i] != Operator.END.getCharacter()) {
            if (isVariable(evaluate[i])) {
                //If the character is a variable, find its value in the variables list.
                Operand variable = findVariable(evaluate[i], variables);
                //Push the variable into the stack.
                operandStack.push(variable.getValue());
            } else if (isConstant(evaluate[i])) {
                //If the character is a constant, find its value in the constants list.
                Operand constant = findVariable(evaluate[i], constants);
                //Push the constant into the stack.
                operandStack.push(constant.getValue());
            } else {
                //Use the findOperator method to match the operator character with one from the enum.
                Operator operator = Operator.findOperator(evaluate[i]);

                //If the operator is a left parenthesis, Push it onto the stack and move on.
                if (operator == Operator.LEFT_PARENTHESIS) {
                    operatorStack.push(operator);
                } else if (operator == Operator.RIGHT_PARENTHESIS) {
                    //When you hit a right parenthesis, go back and
                    //evaluate everything until you find its other side.
                    while (operatorStack.peek() != Operator.LEFT_PARENTHESIS)
                        popAndPush(operatorStack, operandStack);
                    //Pop the left parenthesis.
                    operatorStack.pop();
                } else {
                    //If the operator is any other, make sure that the value is higher than the current on the stack
                    //If it is not, keep popping variables until it is.
                    while (operator.getPriority() <= operatorStack.peek().getPriority())
                        popAndPush(operatorStack, operandStack);
                    //Push the value onto the stack.
                    operatorStack.push(operator);
                }
            }
            i++;
        }
        //Finish up any reamaining operators.
        while (operatorStack.peek() != Operator.END)
            popAndPush(operatorStack, operandStack);
        //Pop final value off and return.
        return operandStack.pop();

    }

    public void popAndPush(Stack<Operator> operatorStack, Stack<int[]> operandStack) {
        //Pull the two values off the top of the operand stack.
        int[] opnd2 = operandStack.pop();
        int[] opnd1 = operandStack.pop();

        //Pull the top operator off the stack.
        Operator oper = operatorStack.pop();
        //Run the evaluate method with the two operands and the operator.
        int[] result = evaluate(opnd1, oper, opnd2);
        //Push the result back on the operand stack.
        operandStack.push(result);
    }

    private int[] evaluate(int[] opnd1, Operator oper, int[] opnd2) {
        //If both items are constants.
        if ((opnd1.length == 1) && (opnd2.length == 1)) {
            switch (oper) {
                case EXPONENT:
                    return new int[] {
                            (int)Math.pow(opnd1[0], opnd2[0])
                    };
                case ADD:
                    return new int[] {
                            opnd1[0] + opnd2[0]
                    };
                case SUBTRACT:
                    return new int[] {
                            opnd1[0] - opnd2[0]
                    };
                case MULTIPLY:
                    return new int[] {
                            opnd1[0] * opnd2[0]
                    };
                case DIVIDE:
                    if (opnd2[0] != 0) {
                        return new int[] {
                                opnd1[0] / opnd2[0]
                        };
                    } else {
                        System.out.println("Attempted divide by zero not allowed.");
                        System.exit(1);
                    }
                case MODULUS:
                    if (opnd2[0] != 0) {
                        return new int[] {
                                opnd1[0] % opnd2[0]
                        };
                    } else {
                        System.out.println("Attempted modulus by zero not allowed.");
                        System.exit(1);
                    }
            }
            //If the first item is a constant, and the second is a matrix.
        } else if ((opnd1.length == 1) && (opnd2.length == 4)) {
            switch (oper) {
                case ADD:
                    return new int[] {
                            opnd1[0] + opnd2[0],
                            opnd1[0] + opnd2[1],
                            opnd1[0] + opnd2[2],
                            opnd1[0] + opnd2[3]
                    };
                case SUBTRACT:
                    return new int[] {
                            opnd1[0] - opnd2[0],
                            opnd1[0] - opnd2[1],
                            opnd1[0] - opnd2[2],
                            opnd1[0] - opnd2[3]
                    };
                case MULTIPLY:
                    return new int[] {
                            opnd1[0] * opnd2[0],
                            opnd1[0] * opnd2[1],
                            opnd1[0] * opnd2[2],
                            opnd1[0] * opnd2[3]
                    };
                case DIVIDE:
                    boolean divideZero = false;
                    int i = 0;
                    while (!divideZero && i < 4) {
                        divideZero = (opnd2[i++] == 0);
                    }

                    if (!divideZero) {
                        return new int[] {
                                opnd1[0] / opnd2[0],
                                opnd1[0] / opnd2[1],
                                opnd1[0] / opnd2[2],
                                opnd1[0] / opnd2[3]
                        };
                    } else {
                        System.out.println("Attempted divide by zero not allowed.");
                        System.exit(1);
                    }
            }
            //If the first item is a matrix, and the second item is a constant.
        } else if ((opnd1.length == 4) && (opnd2.length == 1)) {
            switch (oper) {
                case ADD:
                    return new int[] {
                            opnd1[0] + opnd2[0],
                            opnd1[1] + opnd2[0],
                            opnd1[2] + opnd2[0],
                            opnd1[3] + opnd2[0]
                    };
                case SUBTRACT:
                    return new int[] {
                            opnd1[0] - opnd2[0],
                            opnd1[1] - opnd2[0],
                            opnd1[2] - opnd2[0],
                            opnd1[3] - opnd2[0]
                    };
                case MULTIPLY:
                    return new int[] {
                            opnd1[0] * opnd2[0],
                            opnd1[1] * opnd2[0],
                            opnd1[2] * opnd2[0],
                            opnd1[3] * opnd2[0]
                    };
                case DIVIDE:
                    boolean divideZero = false;
                    int i = 0;
                    while (!divideZero && i < 4) {
                        divideZero = (opnd2[i++] == 0);
                    }

                    if (!divideZero) {
                        return new int[] {
                                opnd1[0] / opnd2[0],
                                opnd1[1] / opnd2[0],
                                opnd1[2] / opnd2[0],
                                opnd1[3] / opnd2[0]
                        };
                    } else {
                        System.out.println("Attempted divide by zero not allowed.");
                        System.exit(1);
                    }
            }
            //If both items are matrices.
        } else if ((opnd1.length == 4) && (opnd2.length == 4)) {
            switch (oper) {
                case ADD:
                    return new int[] {
                            opnd1[0] + opnd2[0],
                            opnd1[1] + opnd2[1],
                            opnd1[2] + opnd2[2],
                            opnd1[3] + opnd2[3]
                    };
                case SUBTRACT:
                    return new int[] {
                            opnd1[0] - opnd2[0],
                            opnd1[1] - opnd2[1],
                            opnd1[2] - opnd2[2],
                            opnd1[3] - opnd2[3]
                    };
                case MULTIPLY:
                    return new int[] {
                            opnd1[0] * opnd2[0],
                            opnd1[1] * opnd2[1],
                            opnd1[2] * opnd2[2],
                            opnd1[3] * opnd2[3]
                    };
                case DIVIDE:
                    boolean divideZero = false;
                    int i = 0;
                    while (!divideZero && i < 4) {
                        divideZero = (opnd2[i++] == 0);
                    }

                    if (!divideZero) {
                        return new int[] {
                                opnd1[0] / opnd2[0],
                                opnd1[1] / opnd2[1],
                                opnd1[2] / opnd2[2],
                                opnd1[3] / opnd2[3]
                        };
                    } else {
                        System.out.println("Attempted divide by zero not allowed.");
                        System.exit(1);
                    }
            }
        }
        System.out.println("Not valid.");
        System.exit(1);
        return null;
    }

    public void createConstants() {
        constants = new Operand[] {
                new Operand('0', new int[] {0}),
                new Operand('1', new int[] {1}),
                new Operand('2', new int[] {2}),
                new Operand('3', new int[] {3}),
                new Operand('4', new int[] {4}),
                new Operand('5', new int[] {5}),
                new Operand('6', new int[] {6}),
                new Operand('7', new int[] {7}),
                new Operand('8', new int[] {8}),
                new Operand('9', new int[] {9})
        };
    }

    //Find the variable matching the character in the list.
    public Operand findVariable(char character, Operand[] list) {
        int i = 0;
        boolean found = false;

        //Loop through the values in variables until one with a matching character is found.
        while(!found && (i < list.length)) {
            found = (list[i].getCharacter() == character);
            i++;
        }

        //If the variable is not found in the array, end the program and provide a message explaining the problem.
        if (!found) {
            System.out.printf("\"%s\" was not found in the list of variables provided.", character);
            System.exit(1);
        }

        //Return the variable with the corresponding character.
        return list[i-1];
    }

    public boolean isVariable(char c) {
        return ((c >= 'A')&&(c <= 'Z'));
    }

    public boolean isConstant(char c) {
        return ((c >= '0')&&(c <= '9'));
    }

    //Formats the result into an easy to read format.
    public String result() {
        if (solution.length == 4) {
            return String.format("|%d, %d|\n|%d, %d|\n", solution[0], solution[1], solution[2], solution[3]);
        }
        return String.format("%d\n", solution[0]);
    }
}
