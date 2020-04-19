import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Object[] items;
    private int first;
    private int last;
    private int n;
    private int pow;

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = new Object[1];
        first = 0;
        last = 0;
        pow = 0;
        n = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }

        n++;

        if (last + 1 < n) {
            last++;
        }

        if (last == (int) Math.pow(2, pow)) {
            Object[] oldItems = items;
            items = new Object[(int) Math.pow(2, ++pow)];

            for (int i = 0; i < oldItems.length; i++) {
                items[i] = oldItems[i];
            }

            oldItems = null;
        }

        items[last] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("RandomizedQueue is empty");
        }

        n--;

        int random = StdRandom.uniform(first + 1 <= last ? first + 1 : first, last + 1);

        Object tmp = items[first];
        Item item = (Item) items[random];
        items[random] = tmp;
        items[first] = null;

        if (first + 1 <= last) {
            first++;
        }

        if (pow > 1 && n == (int) Math.pow(2, (pow - 2))) {
            Object[] oldItems = items;
            items = new Object[(int) Math.pow(2, --pow)];

            for (int i = 0; i < n; i++) {
                items[i] = oldItems[i + first];
            }

            first = 0;
            last = n - 1;
        }

        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("RandomizedQueue is empty");
        }

        int r = StdRandom.uniform(first, last + 1);
        Item item = (Item) items[r];

        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private final int[] copy;
        private int current;

        public RandomizedQueueIterator() {
            copy = new int[RandomizedQueue.this.n];

            for (int i = 0; i < copy.length; i++) {
                copy[i] = i + RandomizedQueue.this.first;
            }

            current = 0;
        }

        public boolean hasNext() {
            return current < copy.length;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("RandomizedQueueIterator has no next");
            }

            int random = StdRandom.uniform(current + 1 < copy.length ? current + 1 : current, copy.length);
            int idxCurrent = copy[current];
            int idxRandom = copy[random];
            copy[current] = idxRandom;
            copy[random] = idxCurrent;
            Item item = (Item) RandomizedQueue.this.items[idxRandom];

            current++;

            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException("RandomizedQueueIterator does not support remove");
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> q = new RandomizedQueue<Integer>();

        StdOut.println("isEmpty: " + q.isEmpty());
        StdOut.println("enqueue: " + 1);
        q.enqueue(1);
        StdOut.println("size   : " + q.size());
        StdOut.println("enqueue: " + 2);
        q.enqueue(2);
        StdOut.println("size   : " + q.size());
        StdOut.println("enqueue: " + 3);
        q.enqueue(3);
        StdOut.println("size   : " + q.size());
        StdOut.println("enqueue: " + 4);
        q.enqueue(4);
        StdOut.println("size   : " + q.size());
        StdOut.println("isEmpty: " + q.isEmpty());

        StdOut.println("dequeue: " + q.dequeue());
        StdOut.println("size   : " + q.size());
        StdOut.println("dequeue: " + q.dequeue());
        StdOut.println("size   : " + q.size());
        StdOut.println("dequeue: " + q.dequeue());
        StdOut.println("size   : " + q.size());
        StdOut.println("dequeue: " + q.dequeue());
        StdOut.println("size   : " + q.size());
        StdOut.println("isEmpty: " + q.isEmpty());

        StdOut.println("enqueue: " + 1);
        q.enqueue(1);
        StdOut.println("size   : " + q.size());
        StdOut.println("enqueue: " + 2);
        q.enqueue(2);
        StdOut.println("size   : " + q.size());
        StdOut.println("enqueue: " + 3);
        q.enqueue(3);
        StdOut.println("size   : " + q.size());
        StdOut.println("enqueue: " + 4);
        q.enqueue(4);
        StdOut.println("size   : " + q.size());
        StdOut.println("isEmpty: " + q.isEmpty());

        StdOut.println("sample : " + q.sample());
        StdOut.println("sample : " + q.sample());
        StdOut.println("sample : " + q.sample());
        StdOut.println("sample : " + q.sample());
        StdOut.println("sample : " + q.sample());
        StdOut.println("sample : " + q.sample());
        StdOut.println("sample : " + q.sample());
        StdOut.println("sample : " + q.sample());
        StdOut.println("size   : " + q.size());
        StdOut.println("isEmpty: " + q.isEmpty());

        StdOut.println("dequeue: " + q.dequeue());
        StdOut.println("size   : " + q.size());
        StdOut.println("dequeue: " + q.dequeue());
        StdOut.println("size   : " + q.size());
        StdOut.println("dequeue: " + q.dequeue());
        StdOut.println("size   : " + q.size());
        StdOut.println("dequeue: " + q.dequeue());
        StdOut.println("size   : " + q.size());
        StdOut.println("isEmpty: " + q.isEmpty());

        StdOut.println("enqueue: " + 1);
        q.enqueue(1);
        StdOut.println("size   : " + q.size());
        StdOut.println("enqueue: " + 2);
        q.enqueue(2);
        StdOut.println("size   : " + q.size());
        StdOut.println("enqueue: " + 3);
        q.enqueue(3);
        StdOut.println("size   : " + q.size());
        StdOut.println("enqueue: " + 4);
        q.enqueue(4);
        StdOut.println("size   : " + q.size());
        StdOut.println("isEmpty: " + q.isEmpty());

        StdOut.println(q.size() + " iterator: ");
        for (Integer item : q) {
            StdOut.print(item + ", ");
        }
        StdOut.println();

        StdOut.println(q.size() + " iterator: ");
        for (Integer item : q) {
            StdOut.print(item + ", ");
        }
        StdOut.println();
    }
}
