package tva;

/**
 * SplayTree-class which rearrange the tree to make the latest searched
 * entry become the root of the tree. 
 * Based on the BinarySearchTree class given for this exercise.
 * @param <E>
 */

public class SplayWithGet<E extends Comparable<? super E>> extends BinarySearchTree<E> implements CollectionWithGet<E> {

    /**
     * Retrieves the given element from the list if it exists, aswell as rearrange the tree
     *
     * @param element the element to be retrieved from the list
     * @returns the object element if it exists in the list, otherwise it returns null.
     * @throws NullPointerException if parameter <tt>element <tt> is null.
     */
    @Override
    public E get(E element) {
        if (element == null){
            throw new NullPointerException();
        }
        Entry searchedEntry = this.find(element, this.root);
        if(searchedEntry==null){
            return null;
        }

        E searchedEntryElement = searchedEntry.element;

        splay(searchedEntry);

        return searchedEntryElement;
    }

    @Override
    protected Entry find( E elem, Entry t ) {
        if ( t == null )
            return null;
        else {
            int jfr = elem.compareTo( t.element );
            if ( jfr  < 0 ) {
                if (t.left == null){
                    return t;
                }
                return find(elem, t.left);
            }
            else if ( jfr > 0 ) {
                if (t.right == null) {
                    return t;
                }
                return find(elem, t.right);
            }
            else
                return t;
        }
    }

    /**
     * Rearranges the tree to make the given Entry newRoot become the
     * new root.
     *
     * @param newRoot the element to be retrieved from the list
     * @returns the entry which has become the new root.
     */
    private void splay(Entry newRoot){
        if (newRoot.parent != null) {
            while (newRoot.parent.parent != null){  //Check if grandparent exists
                //System.out.println( "NewRoot before" + newRoot.toString());
                int grandParentComp = newRoot.element.compareTo(newRoot.parent.parent.element);
                int parentComp = newRoot.element.compareTo(newRoot.parent.element);

                if (parentComp > 0){
                    //parent is smaller
                    if(grandParentComp > 0){
                        //grandparent is smaller
                        zigZig(newRoot.parent.parent);
                        newRoot = newRoot.parent.parent;
                    }else{
                        //grandparent is larger
                        zigZag(newRoot.parent.parent);
                        newRoot = newRoot.parent;
                    }
                }else if(parentComp < 0){
                    //parent is larger
                    if(grandParentComp > 0){
                        //grandparent is smaller
                        zagZig(newRoot.parent.parent);
                        newRoot = newRoot.parent;
                    }else{
                        //grandparent is larger
                        zagZag(newRoot.parent.parent);
                        newRoot = newRoot.parent.parent;
                    }
                }else{
                    rearrangeWhenEqualParent(newRoot);
                    newRoot = newRoot.parent;
                }

                //System.out.println( "NewRoot after" + newRoot.toString());

                if (newRoot.parent == null){
                    break;
                }
            }

            if (newRoot.parent != null){
                int jmf = newRoot.element.compareTo(newRoot.parent.element);
                if(jmf > 0){
                    zig(newRoot.parent);
                }else if(jmf < 0){
                    zag(newRoot.parent);
                }else{
                    rearrangeWhenEqualParent(newRoot);
                }
                newRoot = newRoot.parent;
            }
        }

        root = newRoot;

    }

    private void rearrangeWhenEqualParent(Entry child){
        if (isRightChild(child)){
            zig(child.parent);
        }else{
            zag(child.parent);
        }
    }

    private boolean isRightChild(Entry child){
        return child.parent.right == child;
    }


    /* Rotera 1 steg i hogervarv, dvs
              x'                 y'
             / \                / \
            y'  C   -->        A   x'
           / \                    / \
          A   B                  B   C
    */
    private void zag( Entry x ) {
        Entry   y = x.left;
        E    temp = x.element;
        x.element = y.element;
        y.element = temp;
        x.left    = y.left;
        if ( x.left != null )
            x.left.parent   = x;
        y.left    = y.right;
        y.right   = x.right;
        if ( y.right != null )
            y.right.parent  = y;
        x.right   = y;
    } //   rotateRight
    // ========== ========== ========== ==========

    /* Rotera 1 steg i vanstervarv, dvs
              x'                 y'
             / \                / \
            A   y'  -->        x'  C
               / \            / \
              B   C          A   B
    */
    private void zig( Entry x ) {
        Entry  y  = x.right;
        E temp    = x.element;
        x.element = y.element;
        y.element = temp;
        x.right   = y.right;
        if ( x.right != null )
            x.right.parent  = x;
        y.right   = y.left;
        y.left    = x.left;
        if ( y.left != null )
            y.left.parent   = y;
        x.left    = y;
    } //   rotateLeft
    // ========== ========== ========== ==========

    /* Rotera 2 steg i hogervarv, dvs
              x'                  z'
             / \                /   \
            y'  D   -->        y'    x'
           / \                / \   / \
          A   z'             A   B C   D
             / \
            B   C
    */
    private void zigZag( Entry x ) {
        Entry   y = x.left,
                z = x.left.right;
        E       e = x.element;
        x.element = z.element;
        z.element = e;
        y.right   = z.left;
        if ( y.right != null )
            y.right.parent = y;
        z.left    = z.right;
        z.right   = x.right;
        if ( z.right != null )
            z.right.parent = z;
        x.right   = z;
        z.parent  = x;
    }  //  doubleRotateRight
    // ========== ========== ========== ==========

    /* Rotera 2 steg i vanstervarv, dvs
               x'                  z'
              / \                /   \
             A   y'   -->       x'    y'
                / \            / \   / \
               z   D          A   B C   D
              / \
             B   C
     */
    private void zagZig( Entry x ) {
        Entry  y  = x.right,
                z  = x.right.left;
        E      e  = x.element;
        x.element = z.element;
        z.element = e;
        y.left    = z.right;
        if ( y.left != null )
            y.left.parent = y;
        z.right   = z.left;
        z.left    = x.left;
        if ( z.left != null )
            z.left.parent = z;
        x.left    = z;
        z.parent  = x;
    } //  doubleRotateLeft

    /*
               x'                  z'
              / \                /   \
             A   y'   -->       y'    D
                / \            / \
               B   z'         x'  C
                  / \        / \
                 C   D      A   B
     */

    private void zigZig(Entry x){
        //System.out.println("zigZig");
        Entry        y = x.right;
        Entry        z = y.right;
        System.out.println( "x: " + x.toString()+ "  y: " + y.toString()+ "  z: " + z.toString());
        Entry        A = x.left;
        Entry        B = y.left;
        Entry        C = z.left; //Error
        Entry        D = z.right;

        E temp = z.element;
        z.element = x.element;
        x.element = temp;

        x.left = y; //element z in x
        y.left = z; //element x in z

        x.right = D;
        if (D != null){
            D.parent = x;
        }

        y.right = C;
        if (C != null){
            C.parent = y;
        }

        z.right = B;
        if (B != null){
            B.parent = z;
        }

        z.left = A;
        if (A != null){
            A.parent = z;
        }
    }

    /*
                 x'               z'
               /   \             / \
              y'    D     -->   A   y'
             / \                   / \
            z'  C                 B   x'
           / \                       / \
          A   B                     C   D
     */

    private void zagZag(Entry x){
        //System.out.println("zagZag");
        Entry        y = x.left;
        Entry        z = y.left;
        System.out.println( "x: " + x.toString()+ "  y: " + y.toString()+ "  z: " + z.toString());
        Entry        A = z.left; //Error
        Entry        B = z.right;
        Entry        C = y.right;
        Entry        D = x.right;

        E temp = z.element;
        z.element = x.element;
        x.element = temp;

        x.right = y; //element z in x
        y.right = z; //element x in z

        x.left = A;
        if (A != null){
            A.parent = x;
        }

        y.left = B;
        if (B != null){
            B.parent = y;
        }

        z.left = C;
        if (C != null){
            C.parent = z;
        }

        z.right = D;
        if (D != null){
            D.parent = z;
        }
    }

}
