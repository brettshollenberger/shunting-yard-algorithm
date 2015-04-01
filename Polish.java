public class Polish {
  private Stack<Integer> stack = new Stack<Integer>();
  private Queue<String> queue;

  public Polish(Queue<String> q) {
    queue = q;
  }

  public String toString() {
    String str = "";

    for (String item: queue) {
      str += item + " ";
    }

    return str;
  }

  public int calculate() {
    for (String item: queue) {
      if (isNumeric(item)) stack.push(Integer.parseInt(item));
      else {
        int right = stack.pop();
        int left  = stack.pop();

        if (item.equals("+"))      stack.push(left + right);
        else if (item.equals("-")) stack.push(left - right);
        else if (item.equals("*")) stack.push(left * right);
        else if (item.equals("/")) stack.push(left / right);
      }
    }

    return stack.pop();
  }

  private static boolean isNumeric(String s)  {  
    try  {  
      double d = Double.parseDouble(s);  
    } catch(NumberFormatException nfe)  {  
      return false;  
    }  
    return true;  
  }
}
