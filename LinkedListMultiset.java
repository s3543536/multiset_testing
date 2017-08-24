import java.io.PrintStream;
import java.util.*;

//we need comparable for sorting
import java.lang.Comparable;

public class LinkedListMultiset<T extends Comparable<T>> extends Multiset<T> {

	public class Node<T> {
		public T item;
		public Node<T> next;
		public Node<T> prev;
		public int instances;

		public Node(T item) {
			instances = 1;
			this.item = item;
			this.next = null;
			this.prev = null;
		}

	}

	public Node<T> head;
	public Node<T> tail;
	private int count;

	public LinkedListMultiset() {
		count = 0;
		head = null;
		tail = null;
	} // end of LinkedListMultiset()


	public void add(T item) {
		//System.out.println("asdf");
		if(count < 1) {
			//empty
			head = new Node<T>(item);
			tail = head;
			count++;
		} else {
			//not empty
			Node<T> current = head;
			while(current != null && current.item.compareTo(item) != 0) {
				current = current.next;
			}

			if(current == null) {
				//insert at head
				//insertBefore(head, item);
				Node<T> newnode = new Node<T>(item);
				tail.next = newnode;
				newnode.prev = tail;
				tail = newnode;
				count++;
			} else {
				//current.item is equal, add to current
				current.instances++;
			}
		}
	} // end of add()

	private void insertBefore(Node<T> after, T to_insert) {
		Node<T> item = new Node<T>(to_insert);
		if(after != head) {
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
		count++;
	}

	private Node<T> find(T item) {
		Node<T> current = head;
		while(current != null && current.item.compareTo(item) != 0) {
			current = current.next;
		}
		return current;
	}

	public int search(T item) {
		Node<T> current = head;
		while(current != null && current.item.compareTo(item) != 0) {
			current = current.next;
		}

		if(current == null) {
			//System.out.printf("%s %d\n", item, 0);
			return 0;
		}
		//System.out.printf("%s %d\n", current.item, current.instances);

		return current.instances;
	} // end of add()


	public void removeOne(T item) {
		Node<T> current = find(item);
		if(current == null) {
			return;
		}
		if(--current.instances <= 0) {
			remove(current);
		}
	} // end of removeOne()

	private void remove(Node<T> current) {
		if(count == 1) {
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
		count--;
	}

	public void removeAll(T item) {
		Node<T> current = find(item);
		if(current == null) {
			return;
		}
		remove(current);
	} // end of removeAll()

	public void print(PrintStream out) {
		Node<T> current = head;
		while(current != null) {
			//out.printf("%s%s%d\n", current.item, printDelim, current.instances);
			out.println(""+current.item + printDelim + current.instances);
			current = current.next;
		}
	} // end of print()

} // end of class LinkedListMultiset
