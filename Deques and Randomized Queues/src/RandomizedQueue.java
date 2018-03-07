import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

/**
 * @author Shuanglin 2017-04-08
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
	private Item[] randomq;
	private int size;
	public RandomizedQueue() {
		randomq = (Item[]) new Object[2];
		size = 0;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public int size() {
		return size;
	}
	public void enqueue(Item item) {
		if (item == null)
			throw new NullPointerException("can't be null item");
		if (size == randomq.length)
			resize(2 * randomq.length);
		randomq[size++] = item;
	}
	public Item dequeue() {
		if (isEmpty())
			throw new NoSuchElementException("no item");
		int random_index = StdRandom.uniform(size);
		Item temp = randomq[random_index];
		randomq[random_index] = randomq[size - 1];
		Item item = temp;
		randomq[size - 1] = null;
		size--;
		if (size > 0 && size == randomq.length / 4)
			resize(randomq.length / 2);
		return item;
	}
	public Item sample() {
		if (isEmpty())
			throw new NoSuchElementException("no item");
		return randomq[StdRandom.uniform(size)];
	}
	public Iterator<Item> iterator() {
		return new RandomizedIterator();
	}

	private void resize(int capacity) {
		Item[] temp = (Item[]) new Object[capacity];
		for (int i = 0; i < size; i++)
			temp[i] = randomq[i];
		randomq = temp;
	}
	private class RandomizedIterator implements Iterator<Item> {
		int index;
		int[] randomarray = new int[size];
		RandomizedIterator() {
			for (int i = 0; i < randomarray.length; i++) {
				randomarray[i] = i;
			}
			StdRandom.shuffle(randomarray);
		}
		public boolean hasNext() {
			return index < size;
		}

		public Item next() {
			if (!hasNext())
				throw new NoSuchElementException("no item");
			return randomq[randomarray[index++]];
		}

		public void remove() {
			throw new UnsupportedOperationException();

		}

	}
	public static void main(String[] args) {
		RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
		int N = 10;
		for (int i = 0; i < N; i++) {
			queue.enqueue(Integer.valueOf(i));
		}
		for (Integer s : queue)
			System.out.println(s);
		while (!queue.isEmpty()) {
			System.out.println(queue.dequeue());
		}

	}
}
