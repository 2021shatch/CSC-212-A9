import java.util.ArrayDeque;
import java.util.ArrayList;

/**
 * Generic class to implement binary search trees.
 *
 * @Sabrina Hatch, Anh Ngyuen, Pratima Niroula
 * @version Spring 2022
 */
public class BST<E extends Comparable<E>>
		extends BinaryTree<E> implements BST_Ops<E> {

	/** leaf constructor */
	public BST(E s) {
		super(s);
	}

	/** This constructor creates a branch node */
	public BST(E s, BinaryTree<E> left, BinaryTree<E> right) {
		super(s);
		this.setLeft(left);
		this.setRight(right);
	}

	/** This constructor creates a deep copy of the entire tree structure */
	public BST(BinaryTree<E> tree) {
		super(tree.getData());
		this.setLeft(tree.getLeft());
		this.setRight(tree.getRight());
	}

	/** @override set left */
	public void setLeft(BinaryTree<E> left) {
		if ((left == null) || (left instanceof BST)) {
			super.setLeft(left);
		} else {
			throw new UnsupportedOperationException();
		}
	}

	/** @override get left */
	public BST<E> getLeft() {
		return (BST<E>) super.getLeft();
	}

	/** @override set right */
	public void setRight(BinaryTree<E> right) {
		if ((right == null) || (right instanceof BST)) {
			super.setRight(right);
		} else {
			throw new UnsupportedOperationException();
		}
	}

	/** @override get right */
	public BST<E> getRight() {
		return (BST<E>) super.getRight();
	}

	/**
	 * Returns the node of the given element, or null if not found
	 *
	 * @param query The element to search
	 * @return the node of the given element, or null if not found
	 */
	public BST<E> lookup(E data) {
		BST<E> cur = this;
		while (cur != null) {
			if (cur.getData().compareTo(data) == 0) {
				return cur;
			}
			// current node > data so send to left
			else if (cur.getData().compareTo(data) == 1) {
				cur = cur.getLeft();
			} else {
				cur = cur.getRight();
			}
		}
		return null;
	}

	/**
	 * Returns the parent of the given element's node, or null if not found
	 *
	 * @param query The element to search
	 * @return the node of the given element, or null if not found
	 */
	public BST<E> lookupParent(E data) {
		BST<E> cur = this;
		BST<E> curParent = this;
		while (cur != null) {
			if (cur.getData().compareTo(data) == 0) {
				return curParent;
			}
			// current node > data so send to left
			else if (cur.getData().compareTo(data) == 1) {
				curParent = cur;
				cur = cur.getLeft();
			} else {
				curParent = cur;
				cur = cur.getRight();
			}
		}
		return null;
	}

	/**
	 * Inserts a new node into the tree
	 *
	 * @param data The element to insert
	 */
	public void insert(E newData) {
		BST<E> cur = this;
		while (cur != null) {
			// you've gotten to the very bottom of the tree
			if (cur.getData().compareTo(newData) == 1) {
				if (cur.getLeft() != null) {
					cur = cur.getLeft();
				} else {
					cur.setLeft(new BST<E>(newData));
				}
			}
			// current node < data so send to the right
			else {
				if (cur.getRight() != null) {
					cur = cur.getRight();
				} else {
					cur.setRight(new BST<E>(newData));
				}
			}
		}
	}

	/**
	 * Deletes the specified element from the tree
	 * Returns the modified tree because the root node
	 * may have changed
	 * 
	 * @param evictee The element to delete
	 * @return tree as modified
	 */
	public BST<E> deleteWithCopyLeft(E evictee) {
		// make a copy of the tree
		BST<E> copyTree = this;
		// find node to remove
		BST<E> node = this.lookup(evictee);
		// edge case for when the node to look up does not exist
		if (node == null) {
			System.out.println("Element not found.");
			// if the node is a leaf, find it's parent
		} else if (node.isLeaf()) {
			BST<E> parent = this.lookupParent(evictee);
			// if there the parent does not have any children, it's a leaf set equal to null
			if (parent == null) {
				this.setData(null);
				return copyTree;
			}
			// if the node needs to be removed, set it's parent equal to null
			if (parent.getLeft().getData().compareTo(node.getData()) == 0) {
				parent.setLeft(null);
			} else {
				parent.setRight(null);
			}
			// edge case for when there's only a child to the left
		} else if ((node.getLeft() != null) && (node.getRight() != null)) {
			node.setData(node.getLeft().getData());
			node.setLeft(node.getLeft().getLeft());
			// edge case for when there's only a child to the right
		} else if ((node.getRight() != null) && (node.getLeft() != null)) {
			node.setData(node.getRight().getData());
			node.setRight(node.getRight().getRight());
		} else {
			// move up child to removed node's place
			E nextNode = node.getRight().getData();
			this.deleteWithCopyLeft(nextNode);
			node.setData(nextNode);
		}
		return copyTree;
	}

	/**
	 * Apply left rotation
	 * Returns the modified tree because the root node
	 * may have changed
	 *
	 * @return tree as modified
	 */
	public BST<E> rotateLeft() {
		if (this.getRight() != null) {
			BST<E> newroot = this.getRight(); // new root set to node to the right
			this.setRight(newroot.getLeft()); // sets right node to the node that's to the left of the new root
			newroot.setLeft(this); // set left of the new root to the previous
			return newroot; // return the new root
		}
		return this;
	}

	/**
	 * Apply right rotation
	 * Returns the modified tree because the root node
	 * may have changed
	 *
	 * @return tree as modified
	 */
	public BST<E> rotateRight() {
		if (this.getLeft() != null) {
			BST<E> newroot = this.getLeft();
			this.setLeft(newroot.getRight());
			newroot.setRight(this);
			return newroot;
		}
		return this;
	}

}
