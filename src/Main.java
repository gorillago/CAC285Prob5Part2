import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Main {
    public static void main(String args[]) {
        PrintWriter output = null;
        try {
            output = new PrintWriter("output.txt");
        } catch (FileNotFoundException e) {
            System.out.println("File not found exception");
            System.exit(1);
        }

        Operand[] variables1 = {
                new Operand('A', new int[] {1, 2, 3, 4}),
                new Operand('B', new int[] {6, 6, 8, 8}),
                new Operand('C', new int[] {1, 2, 3, 1})
        };
        output.println("2*A-3*((B-2*C)/(A+3)-B*3)");
        Equation eq1 = new Equation("2*A-3*((B-2*C)/(A+3)-B*3)", variables1);
        output.println(eq1.result());

        Operand[] variables2 = {
                new Operand('A', new int[] {8}),
                new Operand('B', new int[] {12}),
                new Operand('C', new int[] {2}),
                new Operand('D', new int[] {3}),
                new Operand('E', new int[] {15}),
                new Operand('F', new int[] {4}),
        };
        output.println("A@(2*(A-C*D))+(9*B/(2*C+1)-B*3)+E%(F-A)");
        Equation eq2 = new Equation("A@(2*(A-C*D))+(9*B/(2*C+1)-B*3)+E%(F-A)", variables2);
        output.println(eq2.result());

        output.println("B*(3@(A-D)%(B-C@D))+4@D*2");
        Equation eq3 = new Equation("B*(3@(A-D)%(B-C@D))+4@D*2", variables2);
        output.println(eq3.result());
        output.close();
    }
}
