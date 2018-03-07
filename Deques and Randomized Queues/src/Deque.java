import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Shuanglin 2017-04-09
 */
public class Deque<Item> implements Iterable<Item> {
	private int size;
	private Node first;
	private Node last;

	private class Node {
		private Item item;
		private Node previous;
		private Node next;
	}
	public Deque() {
		first = null;
		last = null;
		size = 0;
	}

	public boolean isEmpty() {
		return (first == null || last == null);
	}

	public int size() {
		return size;
	}
	public void addFirst(Item item) {
		if (item == null)
			throw new NullPointerException("can't add null item");
		Node oldfirst = first;
		first = new Node();
		first.item = item;
		first.previous = null;
		if (isEmpty()) {
			last = first;
			first.next = null;
		} else {
			oldfirst.previous = first;
			first.next = oldfirst;
		}
		size++;
	}
	public void addLast(Item item) {
		if (item == null)
			throw new NullPointerException("can't add null item");
		Node oldlast = last;
		last = new Node();
		last.item = item;
		last.next = null;
		if (isEmpty()) {
			first = last;
			last.previous = null;
		} else {
			oldlast.next = last;
			last.previous = oldlast;
		}
		size++;
	}
	public Item removeFirst() {
		if (isEmpty())
			throw new NoSuchElementException("no item");
		Item item = first.item;
		first = first.next;
		if (isEmpty()) {
			last = first;
		} else {
			first.previous = null;
		}
		size--;
		return item;

	}
	public Item removeLast() {
		if (isEmpty())
			throw new NoSuchElementException("no item");
		Item item = last.item;
		last = last.previous;
		if (isEmpty())
			first = last;
		else
			last.next = null;
		size--;
		return item;

	}
	public Iterator<Item> iterator() {
		return new DequeIterator();
	}

	private class DequeIterator implements Iterator<Item> {
		private Node current = first;
		public boolean hasNext() {
			return current != null;
		}
		public void remove() {
			throw new UnsupportedOperationException();
		}
		public Item next() {
			if (!hasNext())
				throw new NoSuchElementException();
			Item item = current.item;
			current = current.next;
			return item;
		}

	}
	public static void main(String[] args) {
		Deque<Integer> deque = new Deque<Integer>();
		int N = 10;
		for (int i = 0; i < N; i++) {
			deque.addLast(Integer.valueOf(i));
		}
		while (!deque.isEmpty()) {
			System.out.println(deque.removeFirst());
		}
	}

}
