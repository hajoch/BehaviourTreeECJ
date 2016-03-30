package bt.decorator;

import bt.Decorator;
import bt.Task;
import bt.utils.BooleanData;
import bt.utils.LoopDecorator;
import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;

/**
 * Created by Hallvard on 14.09.2015.
 */
public class UntilSucceed<E> extends LoopDecorator<E> {

    public UntilSucceed() {}
    public UntilSucceed(Task<E> task) {
        super(task);
    }

    @Override public void childSuccess(Task<E> task) {
        success();
//        loop = false;
    }
    @Override public void childFail(Task<E> task) {
        child.reset();
//        loop = true;
    }

    //ECJ
    @Override
    public void eval(EvolutionState evolutionState, int i, GPData gpData, ADFStack adfStack, GPIndividual gpIndividual, Problem problem) {
        BooleanData dat = (BooleanData)gpData;
        do {
            children[0].eval(evolutionState,i,gpData,adfStack,gpIndividual,problem);
        } while(!dat.result);
    }
}
