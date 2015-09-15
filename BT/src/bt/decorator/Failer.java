package bt.decorator;

import bt.Decorator;
import bt.Task;

/**
 * Created by Hallvard on 14.09.2015.
 *
 *
 * @Depricated THIS DECORATOR IS REALLY NOT NEEDED SINCE WE CAN USE AN INVERTER ON THE SUCCEEDER
 */
public class Failer<E> extends Decorator<E> {

    public Failer(){}
    public Failer(Task<E> task) {
        super(task);
    }

    @Override public void childSuccess(Task<E> task) {
        parent.childFail(this);
    }
    @Override public void childFail(Task<E> task) {
        parent.childFail(this);
    }
}
