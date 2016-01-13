package bt.leaf;

import bt.Leaf;
import bt.utils.BooleanData;
import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;

/**
 * Created by Hallvard on 14.09.2015.
 */
public abstract class Condition<E> extends Leaf<E> {

    protected abstract boolean condition();

    @Override
    public void run() {
        if(condition())
            success();
        else fail();
    }

    @Override
    public void eval(EvolutionState evolutionState, int i, GPData gpData, ADFStack adfStack, GPIndividual gpIndividual, Problem problem) {
        BooleanData dat = (BooleanData)gpData;
        dat.result = (Math.random() > 0.5);
    }

}
