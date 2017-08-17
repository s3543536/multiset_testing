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
		instances++;
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
	}
	
	
	public int search(T item) {
		// Implement me!		
		
		// default return, please override when you implement this method
		return 0;
	} // end of add()
	
	
	public void removeOne(T item) {
		// Implement me!
		Node<T> current = head;
		while(current.item != item && current != tail) {
			current = current.next;
		}
		
		if(instances == 1) {
			if(current.item == item) {
				//only 1
				head = null;
				tail = null;
			} else {
				//item not in list
				return;
			}
		} else {
			if(current == tail) {
				if(current.item == item) {
					current.prev.next = null;
					tail = current.prev;
					current.prev = null;
				} else {
					//item not in list
					return;
				}
			} else if(current == head) {
				//head
				head = current.next;
				current.next.prev = null;
				current.next = null;
			} else {
				//not head or tail
				current.prev.next = current.next;
				current.next.prev = current.prev;
				current.next = null;
				current.prev = null;
			}
		}
		instances--;
	} // end of removeOne()
	
	
	public void removeAll(T item) {
		// Implement me!
	} // end of removeAll()
	
	
	public void print(PrintStream out) {
		// Implement me!
	} // end of print()
	
} // end of class LinkedListMultiset
