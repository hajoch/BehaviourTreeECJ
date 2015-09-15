package defcon;

/**
 * Created by Hallvard on 14.09.2015.
 */
public class Defcon {

    public Defcon() {}

    public void destroyJonatan() {
        System.out.println("Jonatan got rekt!");
    }

    public boolean isDefcon5() {
        return true;
    }
    public boolean isDefcon4() {
        return isDefcon5() && Math.random() >= 0.8;
    }
    public boolean isDefcon3() {
        return isDefcon4() && Math.random() >= 0.8;
    }
    public boolean isDefcon2() {
        return isDefcon3() && Math.random() >= 0.8;
    }
    public boolean isDefcon1() {
        return isDefcon2() && Math.random() >= 0.8;
    }
}
