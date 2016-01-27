package bt;

import ec.gp.GPNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * Created by Hallvard on 14.09.2015.
 */
public abstract class Composite<E> extends Task<E> {

    public boolean deterministic = true; //TODO

    protected int childIndex;

    public Composite(ArrayList<Task<E>> tasks) {
        this.childTasks = tasks;
    }

    @Override
    public void childRunning(Task<E> focal, Task<E> nonFocal) {
        super.childRunning(focal, nonFocal);
        running();
    }

    @Override
    public void run() {
        if(runningTask != null)
            runningTask.run();
        else {
            if(childIndex < childTasks.size()) {
                if(!deterministic) { //TODO Not used at this point
                    final int lastChild = childTasks.size() - 1;
                    if(childIndex < lastChild) Collections.swap(childTasks, childIndex, Math.random() >= 0.5 ? childIndex : lastChild);
                }
                runningTask = childTasks.get(childIndex);
                runningTask.setParent(this);
                runningTask.start();
                run();
            } else
                end(); //Should not happen...
        }
    }

    @Override
    public void start() {
        this.childIndex = 0;
        runningTask = null;
    }

    @Override
    public void reset() {
        super.reset();
        this.childIndex = 0;
    }

    @Override
    public void end() {
        // Just to avoid it being needed in the extending classes.
    }

    @Override
    public String humanToString() {
        return getStandardName()+"["+childTasks.stream().map(Task::humanToString).collect(Collectors.joining(", "))+"]";
    }

    //ECJ
    @Override
    public String toString() {
        return getStandardName()+Arrays.toString(children);
    }

    public int expectedChildren() {
        return 3;
    }


}
