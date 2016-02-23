package bt;

import ec.EvolutionState;
import ec.gp.GPIndividual;
import ec.gp.GPNode;
import ec.util.Parameter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * Created by Hallvard on 14.09.2015.
 */
public abstract class Composite<E> extends Task<E> {

    public boolean deterministic = true;

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
        if (runningTask != null)
            runningTask.run();
        else {
            if (childIndex < childTasks.size()) {
                if (!deterministic) {
                    final int lastChild = childTasks.size() - 1;
                    if (childIndex < lastChild)
                        Collections.shuffle(childTasks.subList(childIndex, lastChild));
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
        return getStandardName() + "[" + childTasks.stream().map(Task::humanToString).collect(Collectors.joining(", ")) + "]";
    }

    //ECJ
    @Override
    public String toString() {
        return getStandardName() + Arrays.toString(children);
    }

    /*
        public int expectedChildren() {
            return 3;
        }*/
    public void checkConstraints(final EvolutionState state,
                                 final int tree,
                                 final GPIndividual typicalIndividual,
                                 final Parameter individualBase) {
        super.checkConstraints(state, tree, typicalIndividual, individualBase);
        if (children.length < 2)
            state.output.error("Incorrect number of children for node " +
                    toStringForError() + " at " +
                    individualBase);
    }


}
