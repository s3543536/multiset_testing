import java.io.PrintStream;
import java.util.*;

//we need comparable for sorting
import java.lang.Comparable;
public class BstMultiset<T extends Comparable<T>> extends Multiset<T> {

	public class Node<T> {
		public T item;
		public Node<T> parent;
		public Node<T> left == null;
		public Node<T> right == null;

		public Node<T>(Node<T> parent, T item) {
			this.item = item;
			this.parent = parent;
		}

		public boolean isLeaf() {
			return left == null && right == null;
		}

		public Node<T> leftMost() {
			return (left == null ? right : left)
		}
	}

	public Node<T> trunk = null;

	public BstMultiset() {
	} // end of BstMultiset()

	private Node<T> getLeftMostLeaf(Node<T> branch) {
		Node<T> to_check = branch;
		
		if(branch == null) {
			return null;
		}

		do {
			branch = to_check;
			to_check = branch.leftMost();
		} while(to_check != null);
		return branch;
	}

	public void add(T item) {
		//TODO Implement me!
		if(trunk == null) {
			//empty
			trunk = new Node<T>(null, item);
		} else {

		}
	} // end of add()

	public int search(T item) {
		// Implement me!

		// default return, please override when you implement this method
		return 0;
	} // end of add()


	public void removeOne(T item) {
		// Implement me!
	} // end of removeOne()
	
	
	public void removeAll(T item) {
		// Implement me!
	} // end of removeAll()


	public void print(PrintStream out) {
		// Implement me!
	} // end of print()

} // end of class BstMultiset
