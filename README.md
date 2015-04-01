# Shunting Yard Algorithm

@(Algorithms)[Parsers|Java]

The shunting yard algorithm is used to parse input in infix notation, while respecting the order of operations:

```
1 * 2 + 3 * 4
```

The fixity of arithmetic operations dictates that `1*2` and `3*4` be computed before `2+3`, but a standard stack-based parser would want to interpret operations in the order they appear (`1*2`, `3+3`, `6*4`). So how do we respect the old standby PEMDAS?

#### Parsing to Reverse Polish Notation

One answer is to parse infix notation to an intermediate notation that _can_ be parsed in-order. Post-fix notation (also called Reverse Polish Notation) is an example of such a format; rather than putting the operations between the operands, we put them after the operands:

```
1 2 * 3 4 * +

// In other words:
(1 2 *) (3 4 *) +
```

A stack-based parser could read this from left-to-right: push operands onto the stack until an operation is confronted. Then, pop off two operands, and push the result back onto the stack.

```
1 2 * => 2
3 4 * => 12
2 12 + => 14
```

#### Parsing from Infix to Postfix

Parsing to this intermediate notation is no small task. Several loop invariants allow us to do this:

##### 1) Preserve PEMDAS in the Output via a Loop Invariant
We keep track of all output, in order, on a queue. All operands immediately enter the queue.

As a loop invariant, any operations present in the queue, will be in the order of operations from the highest precedence (Parentheses) to lowest precedence (Addition and Subtraction).

At the end of the loop, with this loop invariant preserved, the order of operations will be respected in the output.

##### 2) Preserve PEMDAS in the Operation Stack via a Loop Invariant
We should keep track of operations in a stack. As a loop invariant, every time we begin parsing a new token, the stack will be in the order of operations, with the highest precedence at the top of the stack, and the lowest precedence at the bottom of the stack.

With this loop invariant respected, when we run out of tokens, we can simply pop off the operations in order, and add them to the output. With both loop invariants maintained, the fixity of operations will be respected in the output.

##### 3) Maintaining these Invariants
The only time we will confront a challenge with maintaining our loop invariants is with the operations stack--when we a presented with an operation that has a lower precedence than the topmost operation on the output stack. 

In this case, we can preserve both invariants by popping off operations from the operations stack and enqueing it onto the output queue until the topmost operation on the operations stack has the same or lower precedence than the current operation. 

#### Pseudo Code for the Shunting Yard Algorithm

```
while (another token exists):
  if (token is an operand):
    enqueue it in the output queue
  else:
    if (token is a left-parend):
      push it onto the operations stack
    else (if token is a right-parend):
      while the top operation is not a left-parend:
        pop off the top operation and enqueue it to output
      end

      pop off the left-parend and throw it away
    else (if token is another supported operation):
      while its precedence is lower than the top-most operation:
        pop off the top-most op and enqueue it to output
      end

      push the operation onto the stack
    end
  end
end
```

#### Java Implementation (sans classes):

```
public static void main(String[] argv) {
  Queue<String> tokens         = new Queue<String>();
  Stack<Operator> operators    = new Stack<Operator>();
  Queue<String> output         = new Queue<String>();
  String[] supported_operators = {"+", "-", "*", "/", "(", ")"};

  while (!StdIn.isEmpty()) {
    String token = StdIn.readString();
    tokens.enqueue(token);
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

  Polish calculator = new Polish(output);

  StdOut.printf("Postfix: %s \n", calculator.toString());
  StdOut.printf("Result: %d", calculator.calculate());
}
```
