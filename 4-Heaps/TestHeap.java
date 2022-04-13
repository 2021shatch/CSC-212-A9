import java.util.*;

/**
 * Test Heap methods
 *
 * @author Sabrina Hatch, Anh Ngyuen, Pratima Niroula
 * @version Spring 2022
 */
public class TestHeap {
  /** test heap method */
  public static void test() {
    Heap<Integer> h = new Heap<Integer>();
    Integer arr[] = { -2, 3, 9, -7, 1, 2, 6, -3 };
    ArrayList<Integer> al = new ArrayList<Integer>(Arrays.asList(arr));
    System.out.println("Unsorted: " + al.toString());
    Heap.heapSort(al);
    System.out.println("Sorted: " + al.toString());
  }
}