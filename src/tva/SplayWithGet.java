package tva;

public class SplayWithGet<E extends Comparable<? super E>> extends BinarySearchTree<E> implements CollectionWithGet<E> {

    @Override
    public E get(E element) {
        if (element == null){
            throw new NullPointerException();
        }
        Entry searchedEntry = this.find(element, this.root);

        this.root = rearrangeToNewRoot(searchedEntry);

        return element;
    }


    private Entry rearrangeToNewRoot(Entry newRoot){
        if (newRoot.parent != null) {
            while (newRoot.parent.parent != null){  //Check if gp exists
                if (newRoot.element.compareTo(newRoot.parent.element) > 0){
                    //parent is smaller
                    if(newRoot.element.compareTo(newRoot.parent.parent.element) > 0){
                        //gp is smaller
                        zig(newRoot.parent);
                    }else{
                        //gp is larger
                        zigZag(newRoot.parent.parent);
                    }
                }else if(newRoot.element.compareTo(newRoot.parent.element) < 0){
                    //parent is larger
                    if(newRoot.element.compareTo(newRoot.parent.parent.element) > 0){
                        //gp is smaller
                        zagZig(newRoot.parent.parent);
                    }else{
                        //gp is larger
                        zag(newRoot.parent);
                    }
                }else{
                    rearrangeWhenEqualParent(newRoot);
                }
                newRoot = newRoot.parent;
                if (newRoot.parent == null){
                    break;
                }
            }

            if (newRoot.parent != null){
                if(newRoot.element.compareTo(newRoot.parent.element) > 0){
                    zig(newRoot.parent);
                }else if(newRoot.element.compareTo(newRoot.parent.element) > 0){
                    zag(newRoot.parent);
                }else{
                    rearrangeWhenEqualParent(newRoot);
                }
            }
        }

        return newRoot;

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


}
