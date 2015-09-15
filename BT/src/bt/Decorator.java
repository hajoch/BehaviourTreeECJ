package bt;

/**
 * Created by Hallvard on 14.09.2015.
 */
public abstract class Decorator<E> extends Task<E> {

    protected Task<E> child;

    protected Decorator(){}
    protected Decorator(Task<E> child){
        this.child = child;
    }

    @Override
    public void run(){
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
        this.child = child;
    }
    @Override public int getChildCount(){
        return child == null ? 0 : 1;
    }
    @Override public Task<E> getChild(int i){
        return child;
    }


    @Override public void childRunning(Task<E> focal, Task<E> nonFocal) {
        parent.childRunning(focal, this);
    }
    @Override public void childFail(Task<E> task) {
        parent.childFail(this);
    }
    @Override public void childSuccess(Task<E> task) {
        parent.childSuccess(this);
    }
}
