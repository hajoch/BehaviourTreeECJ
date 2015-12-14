package bt.utils;

import ec.gp.GPData;

/**
 * Created by Hallvard on 29.09.2015.
 */
public class BooleanData extends GPData {
    public boolean result;
    public void copyTo(final GPData gpd) {
        ((BooleanData)gpd).result = result;
    }
}
