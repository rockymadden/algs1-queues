import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
import java.util.NoSuchElementException;

// Write a generic data type for a deque and a randomized queue. The goal of this assignment is to
// implement elementary data structures using arrays and linked lists, and to introduce you to
// generics and iterators.
//
// Dequeue. A double-ended queue or deque (pronounced “deck”) is a generalization of a stack and a
// queue that supports adding and removing items from either the front or the back of the data
// structure. Create a generic data type Deque that implements the following API:
//
// Corner cases.  Throw the specified exception for the following corner cases:

// Throw an IllegalArgumentException if the client calls either addFirst() or addLast() with a null
// argument.
// Throw a java.util.NoSuchElementException if the client calls either removeFirst() or removeLast
// when the deque is empty.
// Throw a java.util.NoSuchElementException if the client calls the next() method in the iterator
// when there are no more items to return.
// Throw an UnsupportedOperationException if the client calls the remove() method in the iterator.
// Unit testing.  Your main() method must call directly every public constructor and method to help
// verify that they work as prescribed (e.g., by printing results to standard output).

// Performance requirements.  Your deque implementation must support each deque operation
// (including construction) in constant worst-case time. A deque containing n items must use at
// most 48n + 192 bytes of memory. Additionally, your iterator implementation must support each
// operation (including construction) in constant worst-case time.

public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int n;

    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        n = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }

        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        first.prev = null;

        if (last == null) {
            last = first;
        } else {
            oldFirst.prev = first;
        }

        n++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }

        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.prev = oldLast;

        if (first == null) {
            first = last;
        } else {
            oldLast.next = last;
        }

        n++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }

        Item item = first.item;
        first = first.next;

        if (first == null) {
            last = null;
        } else {
            first.prev = null;
        }

        n--;

        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }

        Item item = last.item;
        last = last.prev;

        if (last == null) {
            first = null;
        } else {
            last.next = null;
        }

        n--;

        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current;

        public DequeIterator() {
            current = Deque.this.first;
        }

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("DequeIterator has no next");
            }

            Item item = current.item;
            current = current.next;

            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException("DequeIterator does not support remove");
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();

        deque.addFirst(1);
        deque.addLast(2);
        deque.addFirst(3);
        deque.addLast(4);
        deque.addFirst(5);
        deque.addLast(6);
        deque.addFirst(7);
        deque.addLast(8);
        
        // [7, 5, 3, 1, 2, 4, 6, 8]
        StdOut.println(deque.size() + " items: ");
        for (Integer item : deque) {
            StdOut.print(item + ", ");
        }
        StdOut.println();

        deque.removeFirst();
        deque.removeLast();
        deque.removeFirst();
        deque.removeFirst();
        deque.removeLast();
        
        // [1, 2, 4]
        StdOut.println(deque.size() + " items: ");
        for (Integer item : deque) {
            StdOut.print(item + ", ");
        }
        StdOut.println();

        deque.removeLast();
        deque.removeLast();

        // [1]
        StdOut.println(deque.size() + " items: ");
        for (Integer item : deque) {
            StdOut.print(item + ", ");
        }
        StdOut.println();

        deque.removeLast();

        // []
        StdOut.println(deque.size() + " items: ");
        for (Integer item : deque) {
            StdOut.print(item + ", ");
        }
        StdOut.println();
    }
}
