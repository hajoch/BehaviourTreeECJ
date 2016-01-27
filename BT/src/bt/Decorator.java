package bt;

/**
 * Created by Hallvard on 14.09.2015.
 */
public abstract class Decorator<E> extends Task<E> {

    public int expectedChildren() {
        return 1;
    }

    protected Task<E> child;

    protected Decorator(){}
    protected Decorator(Task<E> child){
        this.child = child;
    }

    @Override
    public void reset() {
        runningTask = null;
        if(taskState == TaskState.RUNNING) cancel();
        child.reset();
        taskState = TaskState.NEUTRAL;
    }

    @Override
    protected void cancelFollowingChildren(int index) {
        if(index == 0)
            if(child.taskState == TaskState.RUNNING)
                cancel();
    }

    @Override
    public void run(){
        if(taskState != TaskState.RUNNING) {
            child.setParent(this);
            child.start();
        }
        child.run();
    }
    @Override
    public void end() {
        child.end();
    }
    @Override
    public void start() {
        child.setParent(this);
        child.start();
    }


    @Override public void addChild(Task<E> child) {
        if(this.child != null)
            throw new IllegalStateException("A decorator cannot have more than one child");
        this.child = child;
    }
    @Override public int getChildCount(){
        return child == null ? 0 : 1;
    }
    @Override public Task<E> getChild(int i){
        return child;
    }

    @Override public void childRunning(Task<E> focal, Task<E> nonFocal) {
        running();
    }
    @Override public void childSuccess(Task<E> task) {
        success();
    }
    @Override public void childFail(Task<E> task) {
        fail();
    }

    //ECJ
    @Override public String toString() {
        return getStandardName()+"("+(null == children[0] ? "" : children[0].toString())+")";
    }

    //After Implementation
    @Override public String humanToString() {
        return getStandardName()+"("+(null == child ? "" : child.humanToString())+")";
    }
}
