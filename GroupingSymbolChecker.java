import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

public class GroupingSymbolChecker {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java GroupingSymbolChecker <source-code-file>");
            System.exit(1);
        }

        String fileName = args[0];

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int lineNumber = 0;

            Stack<Character> stack = new Stack<>();

            while ((line = reader.readLine()) != null) {
                lineNumber++;

                for (char c : line.toCharArray()) {
                    if (isOpenGroupingSymbol(c)) {
                        stack.push(c);
                    } else if (isCloseGroupingSymbol(c)) {
                        if (stack.isEmpty() || !isMatchingPair(stack.pop(), c)) {
                            System.out.println("Error: Unmatched grouping symbol '" + c + "' at line " + lineNumber);
                            System.exit(1);
                        }
                    }
                }
            }

            if (!stack.isEmpty()) {
                System.out.println("Error: Unmatched grouping symbol '" + stack.pop() + "' at the end of file");
                System.exit(1);
            }

            System.out.println("All grouping symbols are correctly matched!");

        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
    }

    private static boolean isOpenGroupingSymbol(char c) {
        return c == '(' || c == '{' || c == '[';
    }

    private static boolean isCloseGroupingSymbol(char c) {
        return c == ')' || c == '}' || c == ']';
    }

    private static boolean isMatchingPair(char open, char close) {
        return (open == '(' && close == ')') ||
               (open == '{' && close == '}') ||
               (open == '[' && close == ']');
    }
}
