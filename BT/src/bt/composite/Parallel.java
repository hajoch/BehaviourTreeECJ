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

    //TODO complete policy option!

    private Boolean[] runningTasks;
    private boolean success; //is true by default!
    private boolean noRunningTasks;
    private int currentChildIndex;

    private Policy policy;

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
        this.policy = Policy.ANY_SUCCEED;
    }

    /**
     * Optional chained constructor for setting the return policy,
     * consisting of how many children that need to success for
     * the parallel node to succeed itself.
     * @param policy  Number of children that must succeed.
     * @return              The object itself, for chaining purposes.
     */
    public Parallel policy(Policy policy){
        this.policy = policy;
        return this;
    }

    @Override
    public void start() {
        super.start();
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
        policy.childSucceedPolicy(this);
    }


    @Override
    public void childFail(Task<E> task) {
        runningTasks[currentChildIndex] = false;
        policy.childFailPolicy(this);
    }
    @Override
    public void reset() {
        super.reset();
        if(runningTasks != null)
            for(int i = 0; i<runningTasks.length; i++)
                runningTasks[i] = false;
        success = noRunningTasks = true;
    }

    /**
     * Succeed and fail policies
     */
    public enum Policy {
        ANY_SUCCEED() {
            @Override public void childFailPolicy(Parallel<?> parallel) {
                if(parallel.noRunningTasks) {
                    parallel.fail();
                }
            }
            @Override public void childSucceedPolicy(Parallel<?> parallel) {
                parallel.success();
                for(Task t : parallel.childTasks) {
                    if(t.taskState == TaskState.RUNNING) {
                        t.cancel();
                    }
                }
            }
        },
        ALL_SUCCEED() {
            @Override public void childFailPolicy(Parallel<?> parallel) {
                parallel.success = false;
                if(parallel.noRunningTasks && parallel.currentChildIndex == parallel.childTasks.size()-1)
                    parallel.fail();
            }
            @Override public void childSucceedPolicy(Parallel<?> parallel) {
                if(parallel.noRunningTasks && parallel.currentChildIndex == parallel.childTasks.size()-1) {
                    if(parallel.success)
                        parallel.success();
                    else
                        parallel.fail();
                }
            }
        };
        public abstract void childFailPolicy(Parallel<?> parallel);
        public abstract void childSucceedPolicy(Parallel<?> parallel);
    };


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
    public int expectedChildren() {
        return 2;
    }
}
