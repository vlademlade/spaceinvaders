package project.util;

import java.util.AbstractSequentialList;
import java.util.ListIterator;

//TODO rework this class/exercise
public class MyLinkedList<T> extends AbstractSequentialList<T> {

    private Node<T> head = new Node<>(null);
    private Node<T> tail = new Node<>(null);

    public MyLinkedList() {
        head.previous = head;
        head.next = tail;
        tail.previous = head;
        tail.next = tail;
    }

    @Override
    public boolean add(T t) {
        Node<T> n = new Node<>(t);
        n.next = tail;
        n.previous = tail.previous;
        n.previous.next = n;
        tail.previous = n;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        Node<T> n = head;
        while (n.next != tail) {
            n = n.next;
            if (n.content.equals(o)) {
                n.previous.next = n.next;
                n.next.previous = n.previous;
                return true;
            }
        }
        return false;
    }

    @Override
    public T get(int index) {
        Node<T> n = head;
        while (index-- >= 0) {
            n = n.next;
        }
        return n.content;
    }

    @Override
    public int size() {
        int size = 0;
        Node<T> n = head;
        while (n.next != n.next.next) {
            n = n.next;
            size++;
        }
        return size;
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        Node<T> n = head;
        while (index-- > 0) {
            n = n.next;
        }
        return new Iterator<>(n);
    }
}
