package bt.composite;

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
 * Created by Hallvard on 23.02.2016.
 */
public class RandomSelector<E> extends Selector<E> {

    public RandomSelector() {
        super();
    }
    public RandomSelector(Task<E>... tasks) {
        super(new ArrayList<>(Arrays.asList(tasks)));
    }
    public RandomSelector(ArrayList<Task<E>> tasks) {
        super(tasks);
    }

    @Override
    public void start(){
        super.start();
        deterministic = false;
    }

    //ECJ
    @Override
    public void eval(EvolutionState evolutionState, int i, GPData gpData, ADFStack adfStack, GPIndividual gpIndividual, Problem problem) {
        BooleanData dat = (BooleanData)gpData;
        for(GPNode child : children) {
            child.eval(evolutionState,i,gpData,adfStack,gpIndividual,problem);
            if(dat.result) return;
        }

    }

}
