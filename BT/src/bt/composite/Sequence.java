package bt.composite;

import bt.Composite;
import bt.Task;

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
        if(++childIndex < children.size())
            run();
        else
            success();
    }
    @Override
    public void childFail(Task<E> task) {
        fail();
    }
}
