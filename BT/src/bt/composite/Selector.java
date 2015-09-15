package bt.composite;

import bt.Composite;
import bt.Task;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Hallvard on 15.09.2015.
 */
public class Selector<E> extends Composite<E> {

    /**
     * Constructors
     */
    public Selector() {
        this(new ArrayList<Task<E>>());
    }
    public Selector(Task<E>... tasks) {
        this(new ArrayList<Task<E>>(Arrays.asList(tasks)));
    }
    public Selector(ArrayList<Task<E>> tasks) {
        super(tasks);
    }


    @Override
    public void childSuccess(Task<E> task) {
        success();
    }
    @Override
    public void childFail(Task<E> task) {
        super.childFail(task);
        if(++childIndex < children.size())
            run();
        else
            fail();
    }
}
