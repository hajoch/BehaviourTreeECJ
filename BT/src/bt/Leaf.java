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
    public void start() {}
    @Override
    public void end() {}

    /**
     * Default naming convention, utilizing the
     * same algorithm as used by the TreeInterpreter
     * @return       Leaf-node identifier
     */
    @Override
    public String toString() {
        String sn = getClass().getSimpleName();
        return Character.toLowerCase(sn.charAt(0))
                + (sn.length() > 1 ? sn.substring(1): "");
    }

    @Override
    public int getChildCount() {
        return 0;
    }

    //A LEAF-TASK CANNOT HAVE CHILDREN
    @Override
    public void addChild(Task<E> child) {
        throw new UnsupportedOperationException("Leaf task cannot have any childTasks");
    }
    @Override
    public Task<E> getChild(int i) {
        throw new IndexOutOfBoundsException("A bt.leaf node cannot have any childTasks");
    }
}
