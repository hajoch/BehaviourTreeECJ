package bt.composite;

import bt.Task;

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

}
