import java.io.PrintStream;
import java.util.*;

public class LinkedListMultiset<T> extends Multiset<T>
{

	public class Node<T> {
		public T item;
		public Node<T> next;
		public Node<T> prev;

		public Node(T item) {
			this.item = item;
			this.next = null;
			this.prev = null;
		}

		public Node(T item, Node<T> next, Node<T> prev) {
			this(item);
			this.next = next;
			this.prev = prev;
		}

	}

	public Node<T> head;
	public Node<T> tail;
	private int instances;

	public int getInstances() {
		return instances;
	}

	public LinkedListMultiset() {
		// Implement me!
		instances = 0;
	} // end of LinkedListMultiset()


	// Implement me!
	public void add(T item) {
		if(instances < 1) {
			//empty
			head = new Node<T>(item);
			tail = item;
		} else {
			//not empty
			Node<T> current = head;
			while(current != tail && current.item < item) {
				current = current.next;
			}
			insertBefore(current, item);
		}
	} // end of add()

	private void insertBefore(Node<T> after, T to_insert) {
		Node<T> item = new Node<T>(to_insert);
		if(after.prev != null) {
			//not at head
			item.prev = after.prev;
			item.next = after;
			after.prev.next = item;
			after.prev = item;
		} else {
			//at head
			item.next = after;
			after.prev = item;
			head = item;
		}
		instances++;
	}

	private Node<T> find(T item) {
		Node<T> current = head;
		while(current.item != item && current != tail) {
			current = current.next;
		}
		if(current.item == item) {
			return current;
		}
		return null;
	}

	public int search(T item) {
		// Implement me!
		Node<T> current = head;
		while(current.item != item && current != tail) {
			current = current.next;
		}
		if(current.item == item) {
			return current;
		}
		return null;

		// default return, please override when you implement this method
		return 0;
	} // end of add()


	public void removeOne(T item) {
		Node<T> current = find(item);
		if(current == null) {
			return;
		}

		remove(current);
	} // end of removeOne()

	private void remove(Node<T> current) {
		if(instances == 1) {
			//only 1
			head = null;
			tail = null;
		} else {
			if(current == tail) {
				//tail
				current.prev.next = null;
				tail = current.prev;
				current.prev = null;
			} else if(current == head) {
				//head
				head = current.next;
				current.next.prev = null;
				current.next = null;
			} else {
				//neither head nor tail
				current.prev.next = current.next;
				current.next.prev = current.prev;
				current.next = null;
				current.prev = null;
			}
		}
		instances--;
	}


	public void removeAll(T item) {
		Node<T> current = null;
		current = find(item);
		while(current != null) {
			remove(current);
			current = find(item);
		}
	} // end of removeAll()


	public void print(PrintStream out) {
		// Implement me!
	} // end of print()

} // end of class LinkedListMultiset
