package bt.leaf;

import bt.Leaf;
import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;

/**
 * Created by Hallvard on 14.09.2015.
 */
public abstract class Action<E> extends Leaf<E> {

    public Action () {}

    public abstract TaskState execute();

    @Override
    public final void run() {
        TaskState res = execute();
        if(null == res)
            throw new IllegalStateException("null not a valid status for a Action node");
        switch (res) {
            case SUCCEEDED:
                success();
                return;
            case FAILED:
                fail();
                return;
            case RUNNING:
                running();
                return;
            default:
                throw new IllegalStateException("Invalid status returned by: "+res.name());
        }
    }
}
