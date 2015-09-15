package bt.decorator;

import bt.Decorator;
import bt.Task;

/**
 * Created by Hallvard on 14.09.2015.
 */
public class Inverter<E> extends Decorator<E> {

    public Inverter(){}
    public Inverter(Task<E> task) {
        super(task);
    }

    @Override public void childFail(Task<E> task) {
        parent.childSuccess(this);
    }
    @Override public void childSuccess(Task<E> task) {
        parent.childFail(this);
    }
}
