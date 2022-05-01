package demo.jdk;

import java.util.Stack;

public class CustomizeLinkedList<E> {

    private static CustomizeLinkedList<String> listed;

    public CustomizeLinkedList() {
    }

    public static void main(String[] args) {

        listed = new CustomizeLinkedList<String>();
        listed.addToTail("A");
        listed.addToTail("B");
        listed.addToTail("C");
        listed.addToTail("D");

        Stack stack = new Stack();

        System.out.println("=== size: " + listed.size);
        listed.node(listed.size);
    }

    public void addToTail(E element) {
        Node<E> currentLast = last;
        Node<E> newAdded = new Node<E>(currentLast, element, null);
        last = newAdded;
        if (currentLast == null) {
            first = newAdded;
        } else {
            currentLast.next = newAdded;
        }
        size ++;
    }

    public void node(int index) {
        Node<E> x = first;
        for (int i = 0; i < index; i ++) {
            System.out.println("the index: " + x.item);
            x = x.next;
        }
    }


    public void removeLast() {
        Node<E> currentLast = last;
        Node<E> newLast = currentLast.prev;
        currentLast.item = null;
        currentLast.prev = null;
        last = newLast;
        if (newLast == null) {
            first = null;
        } else {
            newLast.next = null; // set as last pointer
        }
        size --;
    }

    public void addToHeader(E element) {
        Node<E> currentFist = first;
        Node<E> newAdded = new Node<E>(null, element, currentFist);
        if (currentFist == null) {
            last = newAdded;
        } else {
            currentFist.prev = newAdded;
        }
        size ++;
    }

    Node first;
    Node last;
    int size;

    //双向链表
    public static class Node<E> {
        E item;
        Node<E> prev;
        Node<E> next;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.prev = prev;
            this.next = next;
        }
    }


}
