package bt.decorator;

import bt.Decorator;
import bt.Task;
import bt.utils.BooleanData;
import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;

/**
 * Created by Hallvard on 14.09.2015.
 */
public class Inverter<E> extends Decorator<E> {

    public Inverter(){}
    public Inverter(Task<E> task) {
        super(task);
    }

    @Override public void childFail(Task<E> task) {
        taskState = TaskState.SUCCEEDED;
        parent.childSuccess(this);
    }
    @Override public void childSuccess(Task<E> task) {
        taskState = TaskState.FAILED;
        parent.childFail(this);
    }

    //ECJ
    @Override
    public void eval(EvolutionState evolutionState, int i, GPData gpData, ADFStack adfStack, GPIndividual gpIndividual, Problem problem) {
        BooleanData dat = (BooleanData)gpData;
        children[0].eval(evolutionState,i,gpData,adfStack,gpIndividual,problem);
        dat.result = !dat.result;
    }
}
