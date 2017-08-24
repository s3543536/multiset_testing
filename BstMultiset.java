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

		public int compareTo(T item) {
//			System.out.printf("%s.compareTo(%s) = %d\n", this.item, item, this.item.compareTo(item));
			return this.item.compareTo(item);
		}

		public int compareTo(Node<T> item) {
//			System.out.printf("%s.compareTo(%s) = %d\n", this.item, item.item, this.item.compareTo(item.item));
			return this.item.compareTo(item.item);
		}

		public Node<T> leftMost() {
			if(left != null) {
				return left;
			}
			return right;
		}
	}

	public Node<T> trunk;

	public BstMultiset() {
		trunk = null;
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
		//System.out.println("adding " + item);
		if(trunk == null) {
			//empty
			trunk = new Node<T>(null, item);
		} else {
			int comp;
			Node<T> current = trunk;

			while(true) {
				comp = current.compareTo(item);
				if(current.compareTo(item) < 0) {
					//current is smaller, add right
					if(current.right == null) {
						current.right = new Node<T>(current, item);
						//System.out.println("count: " + current.right.count);
						return;
					} else {
						current = current.right;
					}
				} else if(current.compareTo(item) == 0) {
					//equal, add here
					current.count++;
					//System.out.println("count: " + current.count);
					return;
				} else if(current.compareTo(item) > 0) {
					//current is greater, add left
					if(current.left == null) {
						current.left = new Node<T>(current, item);
						//System.out.println("count: " + current.left.count);
						return;
					} else {
						current = current.left;
					}
				}
			}
		}
	} // end of add()

	private Node<T> find(T item) {
		Node<T> current = trunk;
		int comp;
		//System.out.println("searching...");

		while(current != null && current.item.compareTo(item) != 0) {
			//System.out.printf("searching for: %s, looking at: %s\n", item, current.item);
			comp = current.item.compareTo(item);
			if(comp > 0) {
				//current is greater
				current = current.left;
			} else if(comp < 0) {
				//current is lesser
				current = current.right;
			}
		}

		if(current != null) {
			return current;
		}
		return null;
	}

	public int search(T item) {
		Node<T> current = find(item);

		if(current != null) {
			//System.out.println("found " + current.item);
			//System.out.println("count " + current.count);
			return current.count;
		}
		//System.out.println("found nothing");

		return 0;
	} // end of add()


	public void removeOne(T item) {
		Node<T> current = find(item);
		if(current == null) {
			return;
		}

		current.count--;
		if(current.count < 1) {
			removeAll(current.item);
		}
	} // end of removeOne()
	
	
	public void removeAll(T item) {
		Node<T> current = find(item);
		if(current == null) {
			return;
		}
		//System.out.println("removing all " + item);

		//current has no children
		if(current.left == null && current.right == null) {
			if(trunk == current) {
				trunk = null;
				return;
			}
			if(current.item.compareTo(current.parent.item) < 0) {
				//current is lesser
				current.parent.left = null;
			} else {
				//current is greater
				current.parent.right = null;
			}
		}

		//current has left and right child
		if(current.left != null && current.right != null) {
			Node<T> leftmost = getLeftMostLeaf(current.right);

			//detach leftmost leaf
			if(leftmost.parent.left == leftmost) {
				//leftmost is left of parent
				leftmost.parent.left = null;
			} else {
				//leftmost is right of parent
				leftmost.parent.right = null;
			}
			leftmost.parent = null;

			//turn current into leftmost
			current.item = leftmost.item;
			current.count = leftmost.count;
			return;
		}

		//current only has left child
		if(current.left != null) {
			current.left.parent = current.parent;

			if(current == trunk) {
				trunk = current.left;
				return;
			}

			if(current == current.parent.left) {
				//current is left
				current.parent.left = current.left;
			} else {
				//current is right
				current.parent.right = current.left;
			}
			return;
		}

		//current only has right child
		if(current.right != null) {
			current.right.parent = current.parent;

			if(current == trunk) {
				trunk = current.right;
				return;
			}

			if(current == current.parent.left) {
				//current is left
				current.parent.left = current.right;
			} else {
				//current is right
				current.parent.right = current.right;
			}
			return;
		}

	} // end of removeAll()


	public void print(PrintStream out) {
		Node<T> current;
		ArrayList<Node<T>> queue = new ArrayList<Node<T>>();

		//System.out.println("printing out contents");

		queue.add(trunk);

		while(queue.size() > 0) {
			current = queue.get(0);
			queue.remove(0);
			
			if(current.left != null) {
				queue.add(current.left);
			}
			if(current.right != null) {
				queue.add(current.right);
			}

			out.println(""+current.item + printDelim + current.count);
			//System.out.println(""+current.item + printDelim + current.count);
		}

		/*while(current != null) {
			//out.printf("%s%s%d\n", current.item, printDelim, current.instances);
			out.println(""+current.item + printDelim + current.instances);
			current = current.next;
		}*/
	} // end of print()

} // end of class BstMultiset
