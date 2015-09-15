package bt.composite;

import bt.Composite;
import bt.Task;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Hallvard on 14.09.2015.
 */
public class Parallel<E> extends Composite<E> {

    private Boolean[] runningTasks;
    private boolean success; //is true by default!
    private boolean noRunningTasks;
    private int currentChildIndex;

    public Parallel() {
        this(new ArrayList<Task<E>>());
    }
    public Parallel(Task<E>... tasks) {
        this(new ArrayList<Task<E>>(Arrays.asList(tasks)));
    }
    public Parallel(ArrayList<Task<E>> tasks) {
        super(tasks);
        this.success = true;
        this.noRunningTasks = true;
    }

    @Override
    public void start() {
        if(runningTasks == null)
            runningTasks = new Boolean[children.size()];
        else
            for(int i = 0; i < runningTasks.length; i++)
                runningTasks[i] = false;
        success = true;
    }

    @Override
    public void run() {
        noRunningTasks = true;
        for(currentChildIndex = 0; currentChildIndex < children.size(); currentChildIndex++) {
            Task<E> child = children.get(currentChildIndex);
            if(runningTasks[currentChildIndex])
                child.run();
            else {
                child.setParent(this);
                child.start();
                child.run();
            }
        }
    }

    @Override
    public void end() {
    }

    @Override
    public void childRunning(Task<E> focal, Task<E> nonFocal) {
        runningTasks[currentChildIndex] = true;
        noRunningTasks = false;
        parent.childRunning(this, this);
    }

    @Override
    public void childSuccess(Task<E> task) {
        runningTasks[currentChildIndex] = false;
        if(noRunningTasks && currentChildIndex == children.size()-1) {
            if (success)
                success();
            else
                fail();
        }
    }

    @Override
    public void childFail(Task<E> task) {
        runningTasks[currentChildIndex] = false;
        success = false;
        if(noRunningTasks && currentChildIndex == children.size()-1)
            fail();
    }

    @Override
    public void reset() {
        super.reset();
        if(runningTasks != null)
            for(int i = 0; i<runningTasks.length; i++)
                runningTasks[i] = false;
        success = true;
    }
}
