package edu.isu.cs2235.structures.impl;

import edu.isu.cs2235.structures.Queue;

public class LinkedQueue<E> implements Queue<E> {

    private DoublyLinkedList<E> data = new DoublyLinkedList<E>();

    public LinkedQueue(LinkedQueue<E> copySource) {
        this.data = copySource.data;
    }

    public LinkedQueue() {}

    /**
     * @return The number of elements in the queue
     */
    @Override
    public int size() {
        return data.size();
    }

    /**
     * @return tests whether the queue is empty.
     */
    @Override
    public boolean isEmpty() {
        return data.isEmpty();
    }

    /**
     * Inserts an element at the end of the queue.
     *
     * @param element Element to be inserted.
     */
    @Override
    public void offer(E element) {
        data.addLast(element);
    }

    /**
     * @return The value first element of the queue (with out removing it), or
     * null if empty.
     */
    @Override
    public E peek() {
        return data.first();
    }

    /**
     * @return The value of the first element of the queue (and removes it), or
     * null if empty.
     */
    @Override
    public E poll() {
        return data.removeFirst();
    }

    /**
     * Prints the contents of the queue starting at top, one item per line. Note
     * this method should not empty the contents of the queue.
     */
    @Override
    public void printQueue() {
        data.printList();
    }

    /**
     * Tranfers the contents of this queue into the provided queue. The contents
     * of this queue are to found in reverse order at the top of the provided
     * queue. This queue should be empty once the transfer is completed. Note
     * that if the provided queue is null, nothing is to happen.
     *
     * @param into The new queue onto which the reversed order of contents from
     *             this queue are to be transferred to the top of, unless the provided queue
     *             is null.
     */
    @Override
    public void transfer(Queue into) {
        if(into == null)
            return;

        while(this.size() > 0)
            into.offer(data.removeLast());
    }

    /**
     * Reverses the contents of this queue.
     */
    @Override
    public void reverse() {
        for(int i = this.size() - 1; i >= 0; i--)
            data.insert(this.poll(), i);
    }

    /**
     * Merges the contents of the provided queue onto the bottom of this queue.
     * The order of both queues must be preserved in the order of this queue
     * after the method call. Furthermore, the provided queue must still contain
     * its original contents in their original order after the method is
     * complete. If the provided queue is null, no changes should occur.
     *
     * @param from Queue whose contents are to be merged onto the bottom of
     *             this queue.
     */
    @Override
    public void merge(Queue<E> from) {
        if(from == null)
            return;

        LinkedQueue<E> bufferQueue = new LinkedQueue<>();
        E bufferObj = null;

        while(from.size() > 0) {
            bufferObj = from.poll();
            this.offer(bufferObj);
            bufferQueue.offer(bufferObj);
        }

        while(bufferQueue.size() > 0)
            from.offer(bufferQueue.poll());
    }
}
