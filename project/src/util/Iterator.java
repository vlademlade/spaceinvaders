package project.util;

import java.util.ListIterator;

public class Iterator<T> implements ListIterator<T> {
    private Node<T> current;

    Iterator(Node<T> current) {
        this.current = current;
    }

    @Override
    public boolean hasNext() {
        return current.next != current.next.next;
    }

    @Override
    public T next() {
        if (!hasNext()) {
            throw new Error("Out of the list !");
        }
        current = current.next;
        return current.content;
    }

    @Override
    public boolean hasPrevious() {
        return current.previous != current.previous.previous;
    }

    @Override
    public T previous() {
        if (!hasPrevious()) {
            throw new Error("Out of the list !");
        }
        current = current.previous;
        return current.content;
    }

    @Override
    public int nextIndex() {
        int idx = 1;
        Node<T> n = current;
        while (n.previous != n.previous.previous) {
            n = n.previous;
            idx++;
        }
        return idx;
    }

    @Override
    public int previousIndex() {
        return nextIndex() - 2;
    }

    @Override
    public void remove() {
        current.previous.next = current.next;
        current.next.previous = current.previous;
        current = current.previous;
    }

    @Override
    public void set(T e) {
        current.content = e;
    }

    @Override
    public void add(T e) {
        Node<T> n = new Node<>(e);
        n.previous = current.previous;
        n.next = current;
        current.previous = n;
        n.previous.next = n;
    }
}
