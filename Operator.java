public class Operator {
  private String operator;

  public Operator(String op) {
    operator = op;
  }

  public String operator() {
    return operator;
  }

  public int precedence() {
    if (operator.equals("("))                              return 1;
    else if (operator.equals("+") || operator.equals("-")) return 2;
    else if (operator.equals("*") || operator.equals("/")) return 3;

    return -1;
  }
}
