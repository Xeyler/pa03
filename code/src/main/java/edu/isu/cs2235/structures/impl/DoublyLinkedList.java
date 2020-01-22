package edu.isu.cs2235.structures.impl;

import edu.isu.cs2235.structures.List;

/***
 * @author Brigham Campbell
 * @param <E> The object type to store in the list
 */
public class DoublyLinkedList<E> implements List<E> {

    // Utility singly-linked Node class from
    // Data Structures and Algorithms in Java
    // pg. 154
    private static class Node<E> {
        private E element;
        private Node<E> prev;
        private Node<E> next;
        public Node(E e, Node<E> p, Node<E> n) {
            element = e;
            prev = p;
            next = n;
        }
        public E getElement() { return element; }
        public Node<E> getPrev() { return prev; }
        public Node<E> getNext() { return next; }
        public void setPrev(Node<E> p) { prev = p; }
        public void setNext(Node<E> n) { next = n; }
    }

    private int numberOfNodes;
    private Node<E> first;
    private Node<E> last;

    /**
     * @return first element in the list or null if the list is empty.
     */
    @Override
    public E first() {
        if(numberOfNodes > 0) {
            return first.element;
        }
        return null;
    }

    /**
     * @return last element in the list or null if the list is empty.
     */
    @Override
    public E last() {
        if(numberOfNodes > 0) {
            return last.element;
        }
        return null;
    }

    /**
     * Adds the provided element to the end of the list, only if the element is
     * not null.
     *
     * @param element Element to be added to the end of the list.
     */
    @Override
    public void addLast(E element) {
        if(element != null) {
            Node<E> newNode = new Node<E>(element, null, null);
            // NOTE: If there aren't any pre-existing nodes, the new node is the first and last
            if(numberOfNodes > 0) {
                last.setNext(newNode);
                newNode.setPrev(last);
                last = newNode;
            } else {
                first = newNode;
                last = newNode;
            }
            numberOfNodes++;
        }
    }

    /**
     * Adds the provided element to the front of the list, only if the element
     * is not null.
     *
     * @param element Element to be added to the front of the list.
     */
    @Override
    public void addFirst(E element) {
        if(element != null) {
            Node<E> newNode = new Node<E>(element, null, null);
            // NOTE: If there aren't any pre-existing nodes, the new node is the first and last
            if(numberOfNodes > 0) {
                first.setPrev(newNode);
                newNode.setNext(first);
                first = newNode;
            } else {
                first = newNode;
                last = newNode;
            }
            numberOfNodes++;
        }
    }

    /**
     * Removes the element at the front of the list.
     *
     * @return Element at the front of the list, or null if the list is empty.
     */
    @Override
    public E removeFirst() {
        if(numberOfNodes > 1) {
            E retval = first.element;
            first.next.setPrev(null);
            first = first.next;

            numberOfNodes--;
            return retval;
        } else if(numberOfNodes == 1) {
            E retval = first.element;
            first = null;
            last = null;

            numberOfNodes--;
            return retval;
        }
        return null;
    }

    /**
     * Removes the element at the end of the list.
     *
     * @return Element at the end of the list, or null if the list is empty.
     */
    @Override
    public E removeLast() {
        // This is much easier than the singly-linked list implementation
        if(numberOfNodes > 1) {
            E retval = last.element;
            last.prev.setNext(null);
            last = last.prev;

            numberOfNodes--;
            return retval;
        } else if(numberOfNodes == 1) {
            E retval = last.element;
            first = null;
            last = null;

            numberOfNodes--;
            return retval;
        }
        return null;
    }

    /**
     * Inserts the given element into the list at the provided index. The
     * element will not be inserted if either the element provided is null or if
     * the index provided is less than 0. If the index is greater than or equal
     * to the current size of the list, the element will be added to the end of
     * the list.
     *
     * @param element Element to be added (as long as it is not null).
     * @param index   Index in the list where the element is to be inserted.
     */
    @Override
    public void insert(E element, int index) {
        if(element != null && index >= 0) {
            if(index >= numberOfNodes) { // don't traverse, just slap it on the end
                addLast(element);
                return;
            }
            if(index == 0) { // don't traverse, just add it to the beginning
                addFirst(element);
                return;
            }

            // traverse to find the desired insertion location
            Node<E> before = first;
            for(int i = 0; i < index; i++) {
                before = before.next;
            }
            Node<E> newNode = new Node<E>(element, before.prev, before);
            before.prev.setNext(newNode);
            before.setPrev(newNode);

            numberOfNodes++;
        }
    }

    /**
     * Removes the element at the given index and returns the value.
     *
     * @param index Index of the element to remove
     * @return The value of the element at the given index, or null if the index
     * is greater than or equal to the size of the list or less than 0.
     */
    @Override
    public E remove(int index) {
        if(index >= 0 && index < numberOfNodes) {
            if(index == numberOfNodes - 1) { //don't traverse, just remove the last one
                return removeLast();
            }
            if(index == 0) { // don't traverse, just remove the first one
                return removeFirst();
            }

            // traverse to find the desired node to remove
            Node<E> before = first;
            for(int i = 0; i < index; i++) {
                before = before.next;
            }
            E retval = before.prev.element;
            if(before.prev.prev != null)
                before.prev.prev.setNext(before);
            before.setPrev(before.prev.prev);
            return retval;
        }
        return null;
    }

    /**
     * Retrieves the value at the specified index. Will return null if the index
     * provided is less than 0 or greater than or equal to the current size of
     * the list.
     *
     * @param index Index of the value to be retrieved.
     * @return Element at the given index, or null if the index is less than 0
     * or greater than or equal to the list size.
     */
    @Override
    public E get(int index) {
        if(index >= 0 && index < numberOfNodes) { // if we're out of bounds, return null
            Node<E> current = first;
            for(int i = 0; i < index; i++) {
                current = current.next;
            }
            return current.element;
        }
        return null;
    }

    /**
     * @return The current size of the list. Note that 0 is returned for an
     * empty list.
     */
    @Override
    public int size() {
        return numberOfNodes;
    }

    /**
     * @return true if there are no items currently stored in the list, false
     * otherwise.
     */
    @Override
    public boolean isEmpty() {
        return numberOfNodes == 0;
    }

    /**
     * Prints the contents of the list in a single line separating each element
     * by a space to the default System.out
     */
    @Override
    public void printList() {
        Node<E> current = first;
        while(current != null) {
            System.out.println(current.element);
            current = current.next;
        }
    }
}
