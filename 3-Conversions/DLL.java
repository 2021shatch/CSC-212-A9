/**
 * Class to implement a singly linked list
 *
 * @author Anonymous
 * @version Fall 2021
 */
public class DLL<T> {
  /** The head node of the list */
  private BinaryTree<T> head;

  /** The tail node of the list */
  private BinaryTree<T> tail;

  /** Constructor for an empty list */
  public DLL() {
    head = tail = null;
  }

  /** Copy constructor makes a deep copy of the list */
  public DLL(DLL<T> orig) {
    if (orig.head == null) {
      // handle special case of an empty list
      head = tail = null;
    } else {
      // make copies of each node in list
      BinaryTree<T> pos = orig.head;
      tail = head = new BinaryTree<T>(pos.getData(), null, null);
      pos = pos.getRight();
      while (pos != null) {
        this.addLast(pos.getData());
        // tail.setRight(new BinaryTree<T>(pos.getData(),null,null));
        // tail = tail.getRight();
        pos = pos.getRight();
      }
    }
  }

  /** Constructor that convert BinaryTree tree to a DLL */
  public DLL(BinaryTree<T> tree) {
    if (tree == null) {
      // handle special case of an empty tree
      head = tail = null;
    } else {
      tail = head = new BinaryTree<T>(tree.getData(), null, null);
      DLL<T> left = conversionHelper(tree.getLeft());
      spliceByTransfer(left, null);
      DLL<T> right = conversionHelper(tree.getRight());
      spliceByTransfer(right, tree);
    }
  }

  /**
   * Recursion to make list from left and right subtree and join them to the root
   * node
   * 
   * @param tree tree to make into array
   */
  private DLL<T> conversionHelper(BinaryTree<T> tree) {
    if (tree == null) {
      return new DLL<T>();
    } else {
      DLL<T> list = new DLL<T>();
      list.addFirst(tree.getData());

      DLL<T> left = conversionHelper(tree.getLeft());
      list.spliceByTransfer(left, null);

      DLL<T> right = conversionHelper(tree.getRight());
      list.spliceByTransfer(right, list.getTail());

      return list;
    }
  }

  /**
   * Accessor for head node
   * 
   * @return the head node
   */
  public BinaryTree<T> getHead() {
    return head;
  }

  /**
   * Accessor for tail node
   * 
   * @return the tail node
   */
  public BinaryTree<T> getTail() {
    return tail;
  }

  /**
   * Determines whether a list is empty
   * 
   * @return T/F is the list empty?
   */
  public boolean isEmpty() {
    return (head == null);
  }

  /**
   * Inserts the given item at the head of the list
   * 
   * @param v item to insert
   */
  public void addFirst(T v) {
    head = new BinaryTree<T>(v, null, head);
    if (tail == null) {
      tail = head;
    } else {
      head.getRight().setLeft(head);
    }
  }

  /**
   * Inserts the given item at the tail of the list
   * 
   * @param item to insert
   */
  public void addLast(T v) {
    if (tail == null) {
      tail = head = new BinaryTree<T>(v, null, null);
    } else {
      tail.setRight(new BinaryTree<T>(v, tail, null));
      tail = tail.getRight();
    }
  }

  /**
   * Inserts the given item after the specified node
   * 
   * @param here node to insert after
   * @param v    item to insert
   */
  public void addAfter(BinaryTree<T> here, T v) {
    if (here == null) {
      // null means put at the head
      addFirst(v);
    } else if (here == tail) {
      addLast(v);
    } else {
      here.setRight(new BinaryTree<T>(v, here, here.getRight()));
      here.getRight().getRight().setLeft(here.getRight());
    }
  }

  /**
   * Removes the given item from the head of the list
   * 
   * @return v item removed
   */
  public T removeFirst() {
    T result = null;
    if (head == null) {
      throw new RuntimeException("Missing element");
    } else {
      result = head.getData();
      head = head.getRight();
      if (head == null) {
        tail = null;
      }
    }
    return result;
  }

  /**
   * Removes the given item from the tail of the list
   * 
   * @return item removed
   */
  public T removeLast() {
    T result = null;
    if (tail == null) {
      // list is empty
      throw new RuntimeException("Missing element");
    } else if (tail == head) {
      // list has one element
      result = tail.getData();
      head = tail = null;
    } else {
      result = tail.getData();
      BinaryTree<T> item = head;
      while (item.getRight() != tail) {
        item = item.getRight();
      }
      tail = item;
      tail.setRight(null);
    }
    return result;
  }

  /**
   * Removes the node specified
   * 
   * @param here marks position to remove after
   * @return item removed
   */
  public T remove(BinaryTree<T> evicted) {
    T result = evicted.getData();
    if (head == evicted) {
      removeFirst();
    } else if (tail == evicted) {
      removeLast();
    } else {
      evicted.getLeft().setRight(evicted.getRight());
      evicted.getRight().setLeft(evicted.getLeft());
    }
    return result;
  }

  /**
   * Returns a count of the number of elements in the list
   * 
   * @return current number of nodes
   */
  public int size() {
    int count = 0;
    if (head != null) {
      BinaryTree<T> pos = head;
      while ((pos != null) && (pos != tail)) {
        count++;
        pos = pos.getRight();
      }
      if (pos == tail) {
        count++;
      }
    }
    return count;
  }

  /**
   * Makes a copy of elements from the original list
   * 
   * @param here starting point of copy
   * @param n    number of items to copy
   * @return the copied list
   */
  public DLL<T> subseqByCopy(BinaryTree<T> here, int n) {
    DLL<T> result = new DLL<>();
    BinaryTree<T> pos = here;
    for (int i = 0; i < n; i++) {
      if (pos == null) {
        throw new RuntimeException("Missing element");
      }
      result.addLast(pos.getData());
      pos = pos.getRight();
    }
    return result;
  }

  /**
   * Places copy of the provided list into this after the specified node.
   * 
   * @param list      the list to splice in a copy of
   * @param afterHere marks the position in this where the new list should go
   */
  public void spliceByCopy(DLL<T> list, BinaryTree<T> afterHere) {
    if (list == this) {
      throw new RuntimeException("Can't insert list into itself");
    }
    BinaryTree<T> thisNode = afterHere; // our position in this
    BinaryTree<T> thatNode = list.head; // our position in list
    if ((afterHere == null) && (thatNode != null)) {
      // null indicates prepend; handle first node specially
      addFirst(thatNode.getData());
      thisNode = head;
      thatNode = thatNode.getRight();
    }
    while ((thatNode != null) && (thatNode != list.tail)) {
      addAfter(thisNode, thatNode.getData());
      thisNode = thisNode.getRight();
      thatNode = thatNode.getRight();
    }
    if (thatNode == list.tail) {
      // we stopped at the tail, so copy it also
      addAfter(thisNode, thatNode.getData());
    }
  }

  /**
   * Extracts a subsequence of nodes and returns them as a new list
   * 
   * @param fromHere marks the first node to extract
   * @param toHere   marks the node where the extraction ends
   * @return the new list
   */
  public DLL<T> subseqByTransfer(BinaryTree<T> fromHere, BinaryTree<T> toHere) {
    DLL<T> result = new DLL<>();
    result.head = fromHere;
    result.tail = toHere;
    if (fromHere == head) {
      head = toHere.getRight();
    } else {
      fromHere.getLeft().setRight(toHere.getRight());
    }
    if (toHere == tail) {
      tail = fromHere.getLeft();
    } else {
      toHere.getRight().setLeft(fromHere.getLeft());
    }
    result.head.setLeft(null);
    result.tail.setRight(null);
    return result;
  }

  /**
   * Takes the provided list and inserts its elements into this after the
   * specified node. The inserted list ends up empty.
   * 
   * @param list      the list to splice in. Becomes empty after the call
   * @param afterHere Marks the place where the new elements are inserted
   */
  public void spliceByTransfer(DLL<T> list, BinaryTree<T> afterHere) {
    if (list == this) {
      throw new RuntimeException("Can't insert list into itself");
    }
    if (!list.isEmpty()) {
      if (afterHere == null) {
        // splicing at front of this
        list.tail.setRight(head);
        head.setLeft(list.tail);
        head = list.head;
        if (tail == null) {
          // this was previously empty
          tail = list.tail;
        }
      } else {
        list.tail.setRight(afterHere.getRight());
        if (afterHere == tail) {
          tail = list.tail;
        } else {
          afterHere.getRight().setLeft(list.tail);
        }
        afterHere.setRight(list.head);
        list.head.setLeft(afterHere);
      }
      // empty the list we inserted from:
      list.head = list.tail = null;
    }
  }

  public void check() {
    if ((head == null) && (tail != null)) {
      System.err.println("Null head without tail.");
    } else if ((head != null) && (tail == null)) {
      System.err.println("Null tail without head.");
    } else {
      BinaryTree<T> pos;
      for (pos = head; (pos != null) && (pos != tail); pos = pos.getRight())
        ;
      if ((head != null) && (pos == null)) {
        System.err.println("Couldn't reach tail.");
      }
      if ((tail != null) && (tail.getRight() != null)) {
        System.err.println("Tail has next.");
      }
    }
  }

  /** Appends a list as a shallow copy */
  public void appendShallow(DLL<T> suffix) {
    if (tail != null) {
      tail.setRight(suffix.head);
      tail = suffix.tail;
    } else { // special case: this list is empty
      this.head = suffix.head;
      this.tail = suffix.tail;
    }
  }

  /**  Appends a list by transferring elements  */
  public void appendTransfer(DLL<T> suffix) {
    if (tail != null) {
      tail.setRight(suffix.head);
      tail = suffix.tail;
    } else { // special case: this list is empty
      this.head = suffix.head;
      this.tail = suffix.tail;
    }
    suffix.head = suffix.tail = null; // transfer
  }

  /**  Appends a list by copying elements  */
  public void appendCopy(DLL<T> suffix) {
    if (suffix.head != null) { // do nothing if suffix empty
      if (head == null) { // copy first element
        head = tail = new BinaryTree<T>(suffix.head.getData(), null, null);
      } else {
        tail.setRight(new BinaryTree<T>(suffix.head.getData(), tail, null));
        tail = tail.getRight();
      }
      // make copies of remaining nodes
      BinaryTree<T> pos = suffix.head;
      for (pos = pos.getRight(); pos != null; pos = pos.getRight()) {
        tail.setRight(new BinaryTree<T>(pos.getData(), tail, null));
        tail = tail.getRight();
      }
    }
  }

  /** Inserts a new node with the given element */
  public void insertAfter(BinaryTree<T> pos, T c) {
    pos.setRight(new BinaryTree<T>(c, pos, pos.getRight()));
    if (tail == pos) { // special case
      tail = pos.getRight();
    } else {
      pos.getRight().getRight().setLeft(pos.getRight());
    }
  }

  /** Converts to a string representation */
  public String toString() {
    String result = "[";
    if (isEmpty()) {
      result += "]";
    } else {
      BinaryTree<T> item;
      for (item = this.head; item != tail; item = item.getRight()) {
        result += item.getData() + ", ";
      }
      result += item.getData() + "]";
    }
    return result;
  }

  /** Keeps track of position in a sequence */
  public class ListIterator {
    /** Position is on either side of this node */
    private BinaryTree<T> pos;

    /** Are we on the left or the right? */
    private boolean onLeft;

    /** Default position is leftmost */
    public ListIterator(DLL<T> list) {
      pos = list.head;
      onLeft = true;
    }

    /** Tests whether there are any more */
    public boolean hasNext() {
      return ((pos != null) && (onLeft || (pos.getRight() != null)));
    }

    /** Returns next or throws an exception */
    public T next() {
      if (!hasNext()) {
        throw new RuntimeException("Missing element");
      } else if (onLeft) {
        onLeft = false;
      } else {
        pos = pos.getRight();
      }
      return pos.getData();
    }
  }

  /** Keeps track of position in a sequence */
  public class ArrayIterator {
    /** Position is to left of this node */
    private int index;

    /** Need to keep pointer to array */
    private T[] arr;

    /** Default position is leftmost */
    public ArrayIterator(T[] arr) {
      index = 0;
      this.arr = arr;
    }

    /** Tests whether there are any more */
    public boolean hasNext() {
      return (index < arr.length);
    }

    /** Returns next or throws an exception */
    public T next() {
      if (!hasNext()) {
        throw new RuntimeException("Missing element");
      } else {
        return arr[index++];
      }
    }
  }
}