import java.util.*;

/**
 * Test conversion methods
 *
 * @author Sabrina Hatch, Anh Ngyuen, Pratima Niroula
 * @version Spring 2022
 */
public class TestConversion {
  /** test array to tree */
  public static void testArrayToTree() {
    Integer[] arr = { -2, 3, 9 };
    BinaryTree<Integer> tree = new BinaryTree<Integer>(arr);
    System.out.println(tree.toString());
  }

  /** test tree to DLL */
  public static void testTreeToDLL() {
    Integer[] arr = { 1, 2, 3, 4 };
    BinaryTree<Integer> tree = new BinaryTree<Integer>(arr);
    System.out.println(tree.toString());
    DLL<Integer> list = new DLL<Integer>(tree);
    list.check();
    System.out.println(list.toString());
  }
}