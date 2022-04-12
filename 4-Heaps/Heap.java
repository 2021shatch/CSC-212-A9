import java.util.*;

/**
 * Implements a heap data structure, using ArrayList for storage.
 *
 * @author Nicholas R. Howe
 * @version CSC 112, 20 November 2006
 */
public class Heap<E extends Comparable<E>> {
	/** Elements of the heap are stored in a ArrayList */
	private ArrayList<E> storage;
	/** Integer to indicate end of heap portion during heap sort */
	private Integer endHeap;

	/** Default constructor creates an empty heap */
	public Heap() {
		storage = new ArrayList<E>();
	}

	/** Default constructor creates an empty heap */
	public Heap(ArrayList<E> v) {
		storage = v;
		for (int i = 0; i < v.size(); i++) {
			bubbleUp(i);
		}
	}

	/** @return heap size */
	public int size() {
		return storage.size();
	}

	/** @return largest element in heap */
	public E peekTop() {
		return storage.get(0);
	}

	/** @return index of parent */
	private static int parent(int pos) {
		return (pos - 1) / 2;
	}

	/** @return index of left child */
	private static int leftChild(int pos) {
		return 2 * pos + 1;
	}

	/** @return index of right child */
	private static int rightChild(int pos) {
		return 2 * pos + 2;
	}

	/** @return T/F does left child exist in tree? */
	private boolean hasLeftChild(int pos) {
		return (leftChild(pos) < endHeap);
	}

	/** @return T/F does right child exist in tree? */
	private boolean hasRightChild(int pos) {
		return (rightChild(pos) < endHeap);
	}

	/**
	 * Swaps an element upwards
	 * 
	 * @param pos Position of element to swap upwards
	 */
	private void swapWithParent(int pos) {
		E tmp = storage.get(parent(pos));
		storage.set(parent(pos), storage.get(pos));
		storage.set(pos, tmp);
	}

	/**
	 * Swaps an element downwards to the right
	 * 
	 * @param pos Position of element to swap
	 */
	private void swapWithRightChild(int pos) {
		E tmp = storage.get(rightChild(pos));
		storage.set(rightChild(pos), storage.get(pos));
		storage.set(pos, tmp);
	}

	/**
	 * Swaps an element downwards to the left
	 * 
	 * @param pos Position of element to swap
	 */
	private void swapWithLeftChild(int pos) {
		E tmp = storage.get(leftChild(pos));
		storage.set(leftChild(pos), storage.get(pos));
		storage.set(pos, tmp);
	}

	/**
	 * Compares to elements in the heap
	 * 
	 * @return true iff the first is bigger than the second
	 */
	private boolean isBigger(int pos1, int pos2) {
		// Comparable c1 = storage.get(pos1);
		// Comparable c2 = storage.get(pos2);
		// return c1.compareTo(c2) > 0;
		return (storage.get(pos1).compareTo(storage.get(pos2)) > 0);
	}

	/**
	 * Bubbles an item down toward the larger of its two children, if any. It starts
	 * at the root (position 0), and follows the item as it sinks. At each point it
	 * should perform several comparisons to determine what swap is necessary.
	 *
	 * First, if the current item has a right child, and that right child is larger
	 * than the current item and its left child, then swap the current item with its
	 * right child.
	 *
	 * Otherwise, if the current item has a left child, and that left child is
	 * larger than the current item, then swap the current item with its left child.
	 *
	 * Otherwise, don't swap anything. You are done.
	 *
	 * If the current position is swapped with either child, continue the process
	 * with the child position.
	 */
	private void bubbleDown() {
		int pos = 0;
		boolean done = false;
		while (!done) {
			if (hasRightChild(pos) && isBigger(rightChild(pos), leftChild(pos)) && isBigger(rightChild(pos), pos)) {
				int parent = pos;
				pos = rightChild(pos);
				swapWithRightChild(parent);
			} else if (hasLeftChild(pos) && isBigger(leftChild(pos), pos)) {
				int parent = pos;
				pos = leftChild(pos);
				swapWithLeftChild(parent);
			} else {
				done = true;
			}
		}
	}

	/**
	 * Pops largest element off heap and returns it.
	 *
	 * The last element in the heap is copied to the root, and removed from its
	 * position at the end. Then it is bubbbled down. Finally, return the value of
	 * the original root
	 *
	 * @return the former root element
	 */
	public E popTop() {
		E largest = storage.remove(0);
		if (storage.size() > 1) {
			E last = storage.remove(storage.size() - 1);
			storage.add(0, last);
			bubbleDown();
		}
		return largest;
	}

	/**
	 * Bubbles an item up until it reaches equilibrium.
	 *
	 * As long as the current item is greater than its parent, swap upwards.
	 *
	 * @param pos The position to work with
	 */
	private void bubbleUp(int pos) {
		while (isBigger(pos, parent(pos))) {
			swapWithParent(pos);
			pos = parent(pos);
		}
	}

	/**
	 * Inserts a new item and re-heapifies
	 *
	 * Add the item at the end of the heap, and bubble it up.
	 *
	 * @param item The item to insert
	 */
	public void insert(E item) {
		storage.add(item);
		bubbleUp(storage.size() - 1);
	}

	/**
	 * Sort an array list in place
	 * 
	 * @param array list to sort
	 */
	public static <T extends Comparable<T>> void heapSort(ArrayList<T> v) {
		// Make v into a heap
		Heap<T> h = new Heap<T>(v);
		h.endHeap = v.size();

		for (int i = v.size() - 1; i > 0; i--) {
			// get largest from heap
			T largest = v.get(0);

			// put largest into sorted and move last element in heap to the root
			v.set(0, v.get(i));
			v.set(i, largest);

			// reduce size of heap
			h.endHeap = h.endHeap - 1;
			// make the remaining portion a heap again
			h.bubbleDown();
		}
	}

	/** Prints heap for debugging */
	public void print() {
		int j = 1, k = 0;
		System.out.println("Heap contents:");
		for (E item : storage) {
			System.out.print(item + " ");
			k++;
			if (k >= j) {
				j = j << 1;
				k = 0;
				System.out.println("");
			}
		}
		System.out.println("");
	}
}