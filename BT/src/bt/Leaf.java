package bt;

/**
 * Created by Hallvard on 14.09.2015.
 */
public abstract class Leaf<E> extends Task<E> {
    @Override
    public void start() {

    }

    @Override
    public void end() {

    }

    @Override
    public void run() {

    }

    //LEAF_TASK CANNOT HAVE CHILDREN
    @Override
    public void addChild(Task<E> child) {
        throw new UnsupportedOperationException("Leaf task cannot have any children");
    }
    @Override
    public Task<E> getChild(int i) {
        throw new IndexOutOfBoundsException("A bt.leaf node cannot have any children");
    }
}
