package bt.decorator;

import bt.Decorator;
import bt.Task;

/**
 * Created by Hallvard on 14.09.2015.
 */
public class UntilFail<E> extends Decorator<E> {

    public UntilFail() {}
    public UntilFail(Task<E> task) {
        super(task);
    }

    @Override public void childSuccess(Task<E> task) {
        start();
        run();
    }
    @Override public void childFail(Task<E> task) {
        parent.childFail(this);
    }
}
