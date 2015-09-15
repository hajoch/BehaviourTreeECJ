package bt.decorator;

import bt.Decorator;
import bt.Task;

/**
 * Created by Hallvard on 14.09.2015.
 */
public class Succeeder<E> extends Decorator<E> {

    public Succeeder(){}
    public Succeeder(Task<E> task) {
        super(task);
    }

    @Override public void childSuccess(Task<E> task) {
        parent.childSuccess(this);
    }
    @Override public void childFail(Task<E> task) {
        parent.childSuccess(this);
    }
}
