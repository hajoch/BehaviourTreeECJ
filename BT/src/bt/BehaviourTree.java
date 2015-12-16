package bt;

import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;

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

        if(rootTask.taskState != TaskState.RUNNING) {
            rootTask.setParent(this);
            rootTask.start();
        }
        rootTask.run();

        /*
        if(runningTask != null) {
            runningTask.run();
        } else {
            rootTask.setParent(this);
            rootTask.start();
            rootTask.run();
        }
        */
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
    public void reset() {
        super.reset();
        tree = this;
    }

    @Override
    public void childRunning(Task<E> focal, Task<E> nonFocal) {
        running();
    }
    @Override
    public void childFail(Task<E> task) {
        fail();
    }
    @Override
    public void childSuccess(Task<E> task) {
        success();
    }

    @Override
    public void start() {}
    @Override
    public void end() {}
    @Override
    public void run() {}


    @Override
    public String toString() {
        return "tree";
    }

    @Override
    public void eval(EvolutionState evolutionState, int i, GPData gpData, ADFStack adfStack, GPIndividual gpIndividual, Problem problem) {
        //TODO What to do here? :/
    }

}
