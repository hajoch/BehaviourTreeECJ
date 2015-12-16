package bt;

import ec.gp.GPNode;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Hallvard on 14.09.2015.
 */
public abstract class Task<E> extends GPNode {

    protected Task<E> parent;
    protected Task<E> runningTask;
    protected ArrayList<Task<E>> childTasks;
    protected BehaviourTree<E> tree;

    public enum TaskState {
        SUCCEEDED(Color.GREEN),
        FAILED(Color.RED),
        RUNNING(Color.BLUE),
        NEUTRAL(Color.WHITE),
        CANCELLED(Color.PINK);

        public final Color color;
        private TaskState(Color color) {
            this.color = color;
        }
    }

    public TaskState taskState = TaskState.NEUTRAL;

    public abstract void start();
    public abstract void end();
    public abstract void run();


    /**
     * Recursively resets the task so it can restart safely on the next run.
     */
    public void reset() {
        runningTask = null;
    //    if(taskState == TaskState.RUNNING) cancel();
        for(Task<E> child : childTasks) {
            child.reset();
        }
        taskState = TaskState.NEUTRAL;
/*
        //TODO hmmm, should I really reset this.. Have to check that
        tree = null;
        parent = null;
*/
    }

    public final void cancel() {
        cancelFollowingChildren(0);
        taskState = TaskState.CANCELLED;
        end();
    }

    protected void cancelFollowingChildren(int index) {
        for(int i = index; i<getChildCount(); i++) {
            Task<E> child = getChild(i);
            if(child.taskState == TaskState.RUNNING)
                child.cancel();
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
    public Task<E> getParent() {
        return parent;
    }

    /**
     * Will be called to signal parent that this task has to run again.
     */
    public final void running() {
        taskState = TaskState.RUNNING;
        if(null != parent)
            parent.childRunning(this, this);
    }

    /**
     * Will be called to signal that the task is finished running and have succeeded/failed
     */
    public void success() {
        taskState = TaskState.SUCCEEDED;
        end();
        if(parent != null)
            parent.childSuccess(this);
    }
    public void fail() {
        taskState = TaskState.FAILED;
        end();
        if(parent != null)
            parent.childFail(this);
    }

    /**
     * Called when one of the childTasks respectively succeed or fail
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
     * @param nonFocal the reporter task (usually one of its childTasks)
     */
    public void childRunning(Task<E> focal, Task<E> nonFocal) {
        this.runningTask = focal;
    }

    // Child handlers
    public void addChild(Task<E> child) {
        childTasks.add(child);
    }
    public Task<E> getChild(int i) {
        return childTasks.get(i);
    }
    public int getChildCount() {
        return childTasks.size();
    }


}
