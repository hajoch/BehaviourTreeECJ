package bt.decorator;

import bt.Decorator;
import bt.Task;

/**
 * Created by Hallvard on 14.09.2015.
 */
public class UntilSucceed<E> extends Decorator<E> {

    public UntilSucceed() {}
    public UntilSucceed(Task<E> task) {
        super(task);
    }

    @Override public void childSuccess(Task<E> task) {
        parent.childSuccess(this);
    }
    @Override public void childFail(Task<E> task) {
        start();
        run();
    }
}
