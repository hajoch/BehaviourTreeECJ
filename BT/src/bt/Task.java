package bt;

import java.util.ArrayList;

/**
 * Created by Hallvard on 14.09.2015.
 */
public abstract class Task<E> {

    protected Task<E> parent;
    protected Task<E> runningTask;
    protected ArrayList<Task<E>> children;
    protected BehaviourTree<E> tree;

    public abstract void start();
    public abstract void end();
    public abstract void run();

    /**
     * Recursively resets the task so it can restart safely on the next run.
     */
    public void reset() {
        runningTask = null;
        for(Task<E> child : children) {
            child.reset();
        }
    }

    /**
     * Get blackboard object
     * @return Blackboard Object
     */
    public E getBlackboard() {
        if(tree == null)
            throw new IllegalStateException("bt.Task has never been run");
        return tree.getBlackboard();
    }

    /**
     * Set parent task
     * @param parent The parent of the task
     */
    public void setParent(Task<E> parent) {
        this.parent = parent;
        this.tree = parent.tree;
    }

    /**
     * Will be called to signal parent that this task has to run again.
     */
    public final void running() {
        parent.childRunning(this, this);
    }

    /**
     * Will be called to signal that the task is finished running and have succeeded/failed
     */
    public void success() {
        end();
        parent.childSuccess(this);
    }
    public void fail() {
        end();
        parent.childFail(this);
    }

    /**
     * Called when one of the children respectively succeed or fail
     * @param task  task that succeeded/failed
     */
    public void childFail(Task<E> task) {
        this.runningTask = null;
    }
    public void childSuccess(Task<E> task) {
        this.runningTask = null;
    }

    /**
     *  Children uses this method to signal the ancestor that needs to run again.
     *
     * @param focal the running task (that needs to run again)
     * @param nonFocal the reporter task (usually one of its children)
     */
    public void childRunning(Task<E> focal, Task<E> nonFocal) {
        this.runningTask = focal;
    }


    // Child handlers
    public void addChild(Task<E> child) {
        children.add(child);
    }
    public Task<E> getChild(int i) {
        return children.get(i);
    }
    public int getChildCount() {
        return children.size();
    }


}
