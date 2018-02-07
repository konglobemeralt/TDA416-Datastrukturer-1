package tva;

public class SplayWithGet<E extends Comparable<? super E>> extends BinarySearchTree<E> implements CollectionWithGet<E> {

    @Override
    public E get(E element) {
        Entry searchedEntry = this.find(element, this.root);

        while (searchedEntry != root){  //TODO Null checks
            if (searchedEntry.element.compareTo(searchedEntry.parent.element) > 1){
                //parent is smaller
                if(searchedEntry.element.compareTo(searchedEntry.parent.parent.element) > 1){
                    //gp is smaller
                    //TODO preform zig
                }else{
                    //gp is larger
                    //TODO preform zigzag
                }
            }else if(searchedEntry.element.compareTo(searchedEntry.parent.element) < 1){
                //parent is larger
                if(searchedEntry.element.compareTo(searchedEntry.parent.parent.element) > 1){
                    //gp is smaller
                    //TODO preform zagzig
                }else{
                    //gp is larger
                    //TODO preform zag
                }
            }else{
                //same size !? TODO What todo?
            }
        }

        // check parent och gp
        // parent och gp större = zag                       rotateRight( parent )
        // parent och gp mindre = zig                       rotateLeft( parent )
        // parent större och gp mindre = zagzig             doubleRotateLeft( gp )
        // parent mindre och gp större = zigzag             doubleRotateRight( gp )
        return null;
    }
}
