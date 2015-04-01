import java.util.Arrays;

public class Main {
  public static boolean isNumeric(String s)  {  
    try  {  
      double d = Double.parseDouble(s);  
    } catch(NumberFormatException nfe)  {  
      return false;  
    }  
    return true;  
  }

  public static void main(String[] argv) {
    Queue<String> input          = new Queue<String>();
    Queue<String> tokens         = new Queue<String>();
    Stack<Operator> operators    = new Stack<Operator>();
    Queue<String> output         = new Queue<String>();
    String[] supported_operators = {"+", "-", "*", "/", "(", ")"};

    while (!StdIn.isEmpty()) {
      String token = StdIn.readString();
      tokens.enqueue(token);
    }

    for (String in: tokens) {
      input.enqueue(in);
    }

    while (!tokens.isEmpty()) {
      String token = tokens.dequeue();

      if (isNumeric(token)) output.enqueue(token);
      else if (Arrays.asList(supported_operators).contains(token)) {
        Operator operator     = new Operator(token);
        Operator top_operator = operators.peek();

        if (token.equals(")")) {
          while (top_operator != null && !top_operator.operator().equals("(")) {
            output.enqueue(top_operator.operator());
            operators.pop();
            top_operator = operators.peek();
          }
          operators.pop();

        } else if (token.equals("(")) {
          operators.push(operator);
        } else {
          while (top_operator != null && top_operator.precedence() > operator.precedence()) {
            output.enqueue(top_operator.operator());
            operators.pop();
            top_operator = operators.peek();
          }

          operators.push(operator);
        }
      }
    }

    while (!operators.isEmpty()) {
      Operator operator = operators.pop();
      output.enqueue(operator.operator());
    }

    String in = "";
    Polish calculator = new Polish(output);

    for (String s: input) {
      in += s + " ";
    }

    StdOut.printf("Infix: %s \n", in);
    StdOut.printf("Postfix: %s \n", calculator.toString());
    StdOut.printf("Result: %d", calculator.calculate());
  }
}
