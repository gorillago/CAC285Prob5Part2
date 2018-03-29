import java.util.ArrayList;

public class Stack<T> {
    ArrayList<T> stack;

    public Stack() {
        stack = new ArrayList<>();
    }

    public int push(T x) {
        stack.add(x);
        return stack.size();
    }

    public T pop() {
        T popped = stack.get(stack.size()-1);
        stack.remove(stack.size()-1);
        return popped;
    }

    public T peek() {
        return stack.get(stack.size()-1);
    }
}
