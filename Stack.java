import java.util.Iterator;

public class Stack<Item> implements Iterable<Item> {
  private Node last = new Node();
  private int N = 0;

  private class Node {
    Item item;
    Node next;
  }

  public boolean isEmpty() {
    return N == 0;
  }

  public int size() {
    return N;
  }

  public Item peek() {
    if (last == null) return null;
    return last.item;
  }

  public void push(Item item) {
    Node oldlast = last;
    last = new Node();
    last.item = item;
    last.next = oldlast;
    N++;
  }

  public Item pop() {
    Node oldlast = last;
    last = oldlast.next;
    N--;
    return oldlast.item;
  }

  public Iterator<Item> iterator() {
    return new StackIterator();
  }

  private class StackIterator implements Iterator<Item> {
    private Node nextNode = last;
    private int i = N;

    public boolean hasNext() {
      return i > 0;
    }

    public Item next() {
      Node oldnext = nextNode;
      nextNode = oldnext.next;
      i--;
      return oldnext.item;
    }
  }
}
