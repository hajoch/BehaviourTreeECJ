package bt.utils.graphics;

import bt.BehaviourTree;
import bt.Task;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by Hallvard on 26.11.2015.
 */
public class LiveBT extends JPanel {

    //TODO Updating single transmission
    //TODO terminate transmissions
    //TODO currently max 20 rows

    static JFrame window;
//    static LiveBT live;
    static Dimension dim = new Dimension(100,100);

    static ArrayList<LiveBT> transmissions = new ArrayList<>();

    private final int MARGIN = 10;
    private HashMap<Task, TaskRep> nodes = new HashMap<>();

    public LiveBT(BehaviourTree bt) {
        Task root = bt.getChild(0);
        generate(root, null, 0);

        //Cosmetics: centering of the tree and window fitting
        int maxX = 0, maxY = 0, maxO = Arrays.stream(rows).max().getAsInt();
        for(TaskRep n : nodes.values()) {
            maxX = n.X > maxX ? n.X : maxX;
            maxY = n.Y > maxY ? n.Y : maxY;
            n.offset(calcOffset(n.ROW, rows[n.DEPTH], maxO)+MARGIN, MARGIN);
        }
        dim = new Dimension(maxX+TaskRep.WIDTH+TaskRep.MARGIN.width, maxY+TaskRep.HEIGHT+TaskRep.MARGIN.height+30+MARGIN);
    }

    private int calcOffset(int pos, int rows, int max) {
        if(max == rows) return 0;

        int w = TaskRep.WIDTH;
        int mw = TaskRep.MARGIN.width;
        return (((max*(w+mw))-(rows*(w+mw)))/(rows+1))*(pos+1);
    }

    public int[] rows = new int[20];

    private void generate(Task task, TaskRep parent, int depth){
        TaskRep rep = new TaskRep(task, parent, depth, rows[depth]++);
        nodes.put(task, rep);

        final int chldrn = task.getChildCount();
        if(chldrn==0)    return;

        for(int i=0; i<chldrn; i++) {
            generate(task.getChild(i), rep, depth+1);
        }
    }

    @Override
    public void paint(Graphics g0) {
        super.paintComponent(g0);
        final Graphics2D g = (Graphics2D)g0.create();
        // Painting the graphics with Anti-aliasing
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        try {
            nodes.values().forEach(c -> c.paintComponent(g));
        } finally {
            g.dispose();
        }
    }

    public static void startTransmission(BehaviourTree bt) {
        LiveBT live = new LiveBT(bt);
        transmissions.add(live);
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setBounds(20, 20, dim.width, dim.height);
        window.getContentPane().add(live);
        window.setVisible(true);
    }

    public static void draw() {
        if(transmissions.isEmpty())
            return;
        transmissions.forEach(LiveBT::repaint);
    }
}
