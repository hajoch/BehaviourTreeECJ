package bt;

/**
 * Created by Hallvard on 14.09.2015.
 */
public abstract class Leaf<E> extends Task<E> {

    //ECJ
    public int expectedChildren() {
        return 0;
    }

    @Override
    public void start() {
    }

    @Override
    public void end() {

    }

    @Override
    public void run() {

    }

    @Override
    public int getChildCount() {
        return 0;
    }

    //LEAF_TASK CANNOT HAVE CHILDREN
    @Override
    public void addChild(Task<E> child) {
        throw new UnsupportedOperationException("Leaf task cannot have any childTasks");
    }
    @Override
    public Task<E> getChild(int i) {
        throw new IndexOutOfBoundsException("A bt.leaf node cannot have any childTasks");
    }
}
