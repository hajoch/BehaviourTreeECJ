package bt;

/**
 * Created by Hallvard on 14.09.2015.
 */
public class BehaviourTree<E> extends Task<E> {

    private Task<E> rootTask;
    private E blackboard;

    /**
     * Behaviour tree with no roottask or blackboard object
     */
    public BehaviourTree() {
        this(null, null);
    }

    /**
     * Behaviour tree with no blackboard object
     * @param rootTask Root task of the behaviour tree
     */
    public BehaviourTree(Task<E> rootTask) {
        this(rootTask, null);
    }

    /**
     * Blackboard object
     * @param rootTask  Root task of the behaviour tree
     * @param blackboard Blackboard object
     */
    public BehaviourTree(Task<E> rootTask, E blackboard) {
        this.rootTask = rootTask;
        this.blackboard = blackboard;
        this.tree = this;
    }

    /**
     * This method is the one to be called when the game needs to make decitions!
     */
    public void step() {
        if(runningTask != null) {
            runningTask.run();
        } else {
            rootTask.setParent(this);
            rootTask.start();
            rootTask.run();
        }
    }

    public E getBlackboard() {
        return blackboard;
    }
    public void setBlackboard(E blackboard) {
        this.blackboard = blackboard;
    }

    @Override
    public void addChild(Task<E> child) {
        if(this.rootTask != null)
            throw new IllegalStateException("The behaviour tree cannot have more than one roottask!");
        this.rootTask = child;
    }

    @Override
    public Task<E> getChild(int i) {
        if(rootTask != null && i == 0)
            return rootTask;
        throw new IndexOutOfBoundsException("Index out of bounds or roottask does not exist");
    }

    @Override
    public int getChildCount() {
        return rootTask == null ? 0 : 1;
    }



    @Override
    public void start() {}

    @Override
    public void end() {}

    @Override
    public void run() {}
}
