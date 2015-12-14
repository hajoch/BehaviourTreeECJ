package bt.composite;

import bt.Composite;
import bt.Task;
import bt.utils.BooleanData;
import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;

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
            runningTasks = new Boolean[childTasks.size()];
        else
            for(int i = 0; i < runningTasks.length; i++)
                runningTasks[i] = false;
        success = true;
    }
    @Override
    public void run() {
        noRunningTasks = true;
        for(currentChildIndex = 0; currentChildIndex < childTasks.size(); currentChildIndex++) {
            Task<E> child = childTasks.get(currentChildIndex);
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
    public void childRunning(Task<E> focal, Task<E> nonFocal) {
        runningTasks[currentChildIndex] = true;
        noRunningTasks = false;
        parent.childRunning(this, this);
    }
    @Override
    public void childSuccess(Task<E> task) {
        runningTasks[currentChildIndex] = false;
        if(noRunningTasks && currentChildIndex == childTasks.size()-1) {
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
        if(noRunningTasks && currentChildIndex == childTasks.size()-1)
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

    //ECJ
    @Override
    public void eval(EvolutionState evolutionState, int i, GPData gpData, ADFStack adfStack, GPIndividual gpIndividual, Problem problem) {
        BooleanData dat = (BooleanData)gpData;
        boolean x = true;
        for(GPNode child : children) {
            child.eval(evolutionState, i, gpData, adfStack, gpIndividual, problem);
            if(!dat.result) x = false;
        }
        dat.result = x;
    }
    @Override
    public String toString() {
        return "parallel"+super.toString();
    }

    @Override
    public int expectedChildren() {
        return 2;
    }
}
