package bt.utils.graphics;

import bt.Decorator;
import bt.Leaf;
import bt.Task;
import bt.leaf.Action;
import bt.leaf.Condition;

import java.awt.*;

/**
 * Created by Hallvard on 26.11.2015.
 */
public class TaskRep {
    public enum Type {
        DECORATOR,
        COMPOSITE,
        ACTION,
        CONDITION,
        OTHER
    }

    public final static Dimension MARGIN = new Dimension(50, 30);
    public final static int TEXT_MARGIN = 10;
    public final static int WIDTH = 50;
    public final static int HEIGHT = 30;

    public final int DEPTH;
    public final int ROW;

    public int X;
    public int Y;

    private final TaskRep parent;
    private final Task task;
    private final Type type;

    private Task.TaskState state = Task.TaskState.NEUTRAL;

    public TaskRep(Task task, TaskRep parent, int depth, int row) {
        this.DEPTH = depth;
        this.ROW = row;
        this.task = task;
        this.parent = parent;

        X = ROW*(WIDTH+MARGIN.width);
        Y = DEPTH*(HEIGHT+TEXT_MARGIN+MARGIN.height);
        state = task.taskState;

        if      (task instanceof Decorator)
            type = Type.DECORATOR;
        else if (task instanceof bt.Composite)
            type = Type.COMPOSITE;
        else if(task instanceof Condition)
            type = Type.CONDITION;
        else if (task instanceof Action)
            type = Type.ACTION;
        else
            type = Type.OTHER;
    }

    public void offset(int x, int y) {
        X += x;
        Y += y;
    }

    protected void paintComponent(Graphics g) {
        state = task.taskState;

        g.setColor(Color.BLACK);
        g.drawString(task.getClass().getSimpleName(), X, Y+HEIGHT+TEXT_MARGIN);

        if(state == Task.TaskState.RUNNING)
            g.setColor(Color.RED);
        if(null != parent)
            g.drawLine(parent.X+WIDTH/2, parent.Y+HEIGHT+TEXT_MARGIN+5, X+WIDTH/2, Y);

        g.setColor(state.color);
        switch (type) {
            case DECORATOR: {
                g.fillPolygon(new int[]{X, X +WIDTH / 2, X + WIDTH, X + WIDTH / 2}, new int[]{Y + HEIGHT / 2, Y , Y+ HEIGHT / 2, Y+ HEIGHT}, 4);
                break;
            }
            case COMPOSITE: {
                g.fillOval(X, Y, WIDTH, HEIGHT);
                break;
            }
            case ACTION: {
                g.fillRoundRect(X,Y,WIDTH, HEIGHT, HEIGHT/2, WIDTH/2);
                break;
            }
            case CONDITION: { // TODO FIX
                g.fillRect(X,Y, WIDTH, HEIGHT);
                g.setColor(Color.BLACK);
                Font tmp = g.getFont();
                g.setFont(new Font("Helvetica", Font.PLAIN, 20));
                g.drawString("?",X+(WIDTH/2)-5, Y+HEIGHT-7);
                g.setFont(tmp);
            }
            case OTHER:
                g.fillPolygon(new int[]{X+ WIDTH/4,X+ WIDTH/2,X+ WIDTH/4,X+ WIDTH/2}, new int[]{Y+ HEIGHT/2, Y, Y+ HEIGHT/2, Y+ HEIGHT}, 4);
        }
    }
}
