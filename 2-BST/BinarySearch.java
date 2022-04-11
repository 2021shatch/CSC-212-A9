import java.util.ArrayDeque;
import java.util.ArrayList;

/**
 * Generic class to implement binary search trees.
 *
 * @Sabrina Hatch, Anh Ngyuen, Pratima Niroula
 * @version Spring 2022
 */
public class BinarySearch<E extends Comparable<E>> extends BinaryTree<E> {

  /** leaf constructor */
  public BinarySearch(E s) {
    super(s);
  }

  /** This constructor creates a branch node */
  public BinarySearch(E s, BinaryTree<E> left, BinaryTree<E> right) {
    super(s);
    this.setLeft(left);
    this.setRight(right);
  }

  /** This constructor creates a deep copy of the entire tree structure */
  public BinarySearch(BinaryTree<E> tree) {
    super(tree.getData());
    this.setLeft(tree.getLeft());
    this.setRight(tree.getRight());
  }

  /** @override set left */
  public void setLeft(BinaryTree<E> left) {
    if ((left == null) || (left instanceof BinarySearch)) {
      super.setLeft(left);
    } else {
      throw new UnsupportedOperationException();
    }
  }

  /** @override get left */
  public BinarySearch getLeft() {
    return (BinarySearch) super.getLeft();
  }

  /** @override set right */
  public void setRight(BinaryTree<E> right) {
    if ((right == null) || (right instanceof BinarySearch)) {
      super.setRight(right);
    } else {
      throw new UnsupportedOperationException();
    }
  }

  /** @override get right */
  public BinarySearch getRight() {
    return (BinarySearch) super.getRight();
  }

  // depth first search - BST recursive O(nlog(n))
public BinaryTree<E> lookup(BinaryTree<E> tree, E q)
  {
    if (tree == null)
    {
      System.out.println("Lookup failed. Please give a valid input.");
      return null; 
    }
    else if (tree.getData().compareTo(q) == 0){
      return tree;
    }
    else
    {
      //if q is smaller it equals 1
      // if q is bigger it equals -1
      // if they are the same, it equals 0
      if (tree.getData().compareTo(q) == 1)
      {
        return lookup(tree.getLeft(), q);
      }
      else
      {
        return lookup(tree.getRight(), q);
      }
    }
    
  }

  

 public void insert(BinaryTree<E> tree, E newData)
  {
    if (tree.getData().compareTo(newData) == 0)
    {
      System.out.println("The node already exists! Please enter a different value to be placed into the tree.");
    }
    else if (tree.getData().compareTo(newData) == 1)
    {
      if (tree.getLeft() == null)
      {
        tree.setLeft(new BinarySearch(newData));
      }
      else
      {
      insert(tree.getLeft(), newData);
      }
    }
    else 
    {
      if (tree.getRight() == null)
      {
        tree.setRight(new BinarySearch(newData));
      }
      else
      {
        insert(tree.getRight(), newData);
      }
    }
  }

  // public void delete() - use copy left and left/right rotations

  //public void leftRotation()
  //public void rightRotation()


}
