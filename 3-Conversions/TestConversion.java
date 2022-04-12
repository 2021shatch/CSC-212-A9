import java.util.*;

public class TestConversion {
	public static void testArrayToTree(){
		Integer[] arr = {-2,3,9};
		BinaryTree<Integer> tree = new BinaryTree <Integer>(arr);
		System.out.println(tree.toString());
	}

	public static void testTreeToDLL(){
		Integer[] arr = {1,2,3,4};
		BinaryTree<Integer> tree = new BinaryTree <Integer>(arr);
		System.out.println(tree.toString());
		DLL<Integer> list = new DLL<Integer>(tree);
		list.check();
		System.out.println(list.toString());
	}
}