package defcon.tasks;

import bt.leaf.Action;
import defcon.Defcon;

/**
 * Created by Hallvard on 15.09.2015.
 */

public class DestroyJonatanTask extends Action<Defcon> {

    @Override
    public void run() {
        Defcon defcon = getBlackboard();
        if(defcon.isDefcon1()) {
            defcon.destroyJonatan();
        }
    }
}
