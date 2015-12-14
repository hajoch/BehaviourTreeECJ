package bt.utils;

import bt.Decorator;
import bt.Task;

/**
 * Created by Hallvard on 11.12.2015.
 */
public abstract class LoopDecorator<E> extends Decorator<E> {

    protected boolean loop;

    public LoopDecorator(){}
    public LoopDecorator(Task<E> child){
        super(child);
    }

    public boolean contidtion() {
        return loop;
    }

    @Override
    public void run() {
        loop = true;
        while(contidtion()) {
            if(child.taskState != TaskState.RUNNING) {
                child.setParent(this);
                child.start();
            }
            child.run();
        }
    }

    @Override
    public void childRunning(Task<E> focal, Task<E> nonFocal) {
        super.childRunning(focal, nonFocal);
        loop = false;
    }
}
