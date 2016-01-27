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
 * Created by Hallvard on 15.09.2015.
 */
public class Sequence<E> extends Composite<E> {

    /**
     * Contructors
     */
    public Sequence(){
        this(new ArrayList<Task<E>>());
    }

    public Sequence(Task<E>... tasks) {
        this(new ArrayList<Task<E>>(Arrays.asList(tasks)));
    }

    public Sequence(ArrayList<Task<E>> tasks) {
        super(tasks);
    }

    @Override
    public void childSuccess(Task<E> task) {
        super.childSuccess(task);
        if(++childIndex < childTasks.size())
            run();
        else
            success();
    }
    @Override
    public void childFail(Task<E> task) {
        super.childFail(task);
        fail();
    }

    //ECJ
    @Override
    public void eval(EvolutionState evolutionState, int i, GPData gpData, ADFStack adfStack, GPIndividual gpIndividual, Problem problem) {
        BooleanData dat = (BooleanData)gpData;
        for(GPNode child : children) {
            child.eval(evolutionState,i,gpData,adfStack,gpIndividual,problem);
            if(!dat.result) return;
        }
    }
}
