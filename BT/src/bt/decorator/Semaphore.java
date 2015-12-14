package bt.decorator;

import bt.Decorator;
import bt.Task;
import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;

/**
 * Created by Hallvard on 26.11.2015.
 */
public class Semaphore<E> extends Decorator<E> {

    public Semaphore(){}
    public Semaphore(Task<E> task) {
        super(task);
    }

    @Override
    public void eval(EvolutionState evolutionState, int i, GPData gpData, ADFStack adfStack, GPIndividual gpIndividual, Problem problem) {

    }

    @Override
    public void start() {

    }
}
