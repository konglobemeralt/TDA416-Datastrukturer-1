package tva;

import java.util.NoSuchElementException;

/**
 * Sorted linked collection that puts elements in size order based on compareTo-function when they are added.
 * Based on the LinkedCollection class given for this exercise.
 * @param <E>
 */

public class SLCWithGet<E extends Comparable<? super E>> extends LinkedCollection<E> implements CollectionWithGet<E> {


    /**
     * Adds an element to the collection.
     * The element will be sorted into the right place
     * in the list. If the list is empty it will be
     * added as the head
     *
     * @param element the object to add into the list
     * @return true if the object has been added to the list.
     * @throws NullPointerException if parameter <tt>element<tt> is null.
     */
    @Override
    public boolean add(E element) {
        if (element == null) {
            throw new NullPointerException();
        }else if (this.isEmpty() || element.compareTo(head.element)<=0) {
            head = new Entry(element, head);
            return true;
        }else{
            for ( Entry p = head; p != null; p = p.next ){
                if (p.next == null || element.compareTo(p.next.element)<0) {
                    Entry newEntry = new Entry(element,p.next);
                    p.next = newEntry;
                    return true;
                }

            }
            return false;
        }
    }
    /**
     * searches the list for the element given as argument
     * and returns it if it is found
     *
     * @param e the object to be retrieved from the list
     * @return the object e if it exists in the list. returns null if the object wasn't found
     * @throws NullPointerException if parameter <tt>e <tt> is null.
     */
    @Override
    public E get(E e) {
        if(e!=null) {
            for (Entry p = head; p != null; p = p.next) {
                if (e == p) {
                    return p.element;
                }else if(p.element.compareTo(e) >= 0){
                    break;
                }
            }
        }else{
            throw new NullPointerException();
        }
        return null;
    }
}