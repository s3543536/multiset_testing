import java.io.PrintStream;
import java.util.*;

//we need comparable for sorting
import java.lang.Comparable;
public class BstMultiset<T extends Comparable<T>> extends Multiset<T> {

	public class Node<T extends Comparable<T>> implements Comparable<Node<T>> {
		public T item;
		public Node<T> parent;
		public Node<T> left = null;
		public Node<T> right = null;
		public int count = 0;

		public Node(Node<T> parent, T item) {
			this.item = item;
			this.parent = parent;
			count++;
		}

		public void add(T item) {
			add(new Node<T>(this, item));
		}

		public void add(Node<T> item) {
			switch(compareTo(item)) {
				case -1:
					//add right
					right = item;
					break;
				case 0:
					//add to current
					count++;
					break;
				case 1:
					//add left
					left = item;
					break;
				default:
					System.err.println("error, node.add is broken");
					break;
			}
		}

		public boolean isLeaf() {
			return left == null && right == null;
		}

		public Node<T> leftMost() {
			return (left == null ? right : left);
		}

		public int compareTo(T item) {
			return this.item.compareTo(item);
		}

		public int compareTo(Node<T> item) {
			return this.item.compareTo(item.item);
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
		if(trunk == null) {
			//empty
			trunk = new Node<T>(null, item);
		} else {
			int comp = trunk.compareTo(item);
			Node<T> current = trunk;

			while(true) {
				switch(current.compareTo(item)) {
					case -1:
						//current is smaller, add right
						if(current.right == null) {
							current.add(item);
							return;
						} else {
							current = current.right;
						}
						break;
					case 0:
						//equal, add here
						current.add(item);
						return;
					case 1:
						//current is greater, add left
						if(current.left == null) {
							current.add(item);
							return;
						} else {
							current = current.left;
						}
						break;
					default:
						System.err.println("ERROR: add failed");
						break;
				}
			}
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
