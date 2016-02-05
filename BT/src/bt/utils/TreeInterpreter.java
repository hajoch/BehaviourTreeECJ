package bt.utils;

import bt.BehaviourTree;
import bt.Task;
import bt.composite.*;
import bt.decorator.*;
import com.sun.istack.internal.NotNull;

import java.util.HashMap;
import java.util.Optional;

/**
 * Created by Hallvard on 26.01.2016.
 */
public class TreeInterpreter<E> {

    private E blackboard;

    protected final HashMap<String, Class<? extends Task>> NODE_MAP = new HashMap<>();

    {
        @SuppressWarnings("unchecked")
        Class<? extends Task>[] btClasses = new Class[]{
                Parallel.class, Selector.class, Sequence.class,
                Failer.class, Inverter.class, Succeeder.class,
                UntilFail.class, UntilSucceed.class
        };
        translate(btClasses);
    }

    public TreeInterpreter(E blackboard) {
        this.blackboard = blackboard;
    }

    public Optional<BehaviourTree<E>> create(Class<? extends Task>[] leafNodes, String rep) {
        BehaviourTree<E> tree = new BehaviourTree<>();
        tree.setBlackboard(blackboard);
        translate(leafNodes);

        StringBuilder delta = new StringBuilder();
        Task<E> current = tree;
        for(char c : rep.replace(" ", "").toCharArray()) {
            if(Character.isAlphabetic(c) || c == '_') {
                delta.append(c);
                continue;
            }
            Optional<Task<E>> next = Optional.empty();
            if(delta.length() != 0) {
                try {
                    next = Optional.of(getNode(delta.toString()));
                } catch (ClassCastException e) {
                    return Optional.empty();
                }
            }
            if(next.isPresent())
                current.addChild(next.get());
            switch (c) {
                case ']':
                case ')': {
                    current = current.getParent();
                    break;
                }
                case '[':
                case '(': {
                    next.get().setParent(current);
                    current = next.get();
                }
            }
            delta.setLength(0);
        }
        return Optional.of(tree);
    }

    @SuppressWarnings("unchecked")
    private Task<E> getNode(String name) {
        Class<? extends Task> c = NODE_MAP.get(name);
        Optional<Task<E>> node;
        try {
            node = Optional.ofNullable(c.newInstance());
        } catch (InstantiationException|IllegalAccessException e) {
            e.printStackTrace();
            throw new ClassCastException(e.getMessage());
        }
        assert node.isPresent();
        return node.orElseThrow(() -> new ClassCastException("Something wierd went down in getNode()"));
    }

    protected void translate(Class<? extends Task>[] nodes) {
        for (Class<? extends Task> n : nodes) {
            String sn = n.getSimpleName();
            String alias = Character.toLowerCase(sn.charAt(0))
                    + (sn.length() > 1 ? sn.substring(1): "");
            NODE_MAP.put(alias, n);
        }
    }

}
