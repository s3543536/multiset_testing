import java.io.PrintStream;
import java.util.*;

//we need comparable for sorting
import java.lang.Comparable;
public class BstMultiset<T extends Comparable<T>> extends Multiset<T> {

	public class Node<T> {
		T item;
		Node<T> parent;
		Node<T> leftBranch;
		Node<T> rightBranch;

		public Node<T>(Node<T> parent, T item) {
			this.item = item;
			this.parent = parent;
			this.leftBranch = null;
			this.rightBranch = null;
		}
	}

	public BstMultiset() {
		// Implement me!
	} // end of BstMultiset()

	public void add(T item) {
		// Implement me!
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
