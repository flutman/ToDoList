package main;

import main.model.Action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Storage {
    private static AtomicInteger currentId = new AtomicInteger();
    private static Map<Integer, Action> actions = new ConcurrentHashMap<Integer, Action>();

    public Storage() {
    }

    public static List<Action> getAllActions() {
        ArrayList<Action> actionsList = new ArrayList<>();
            actionsList.addAll(actions.values());
        return actionsList;
    }

    public static int addAction(Action action) {
        int id = currentId.incrementAndGet();
        action.setId(id);
        actions.put(id, action);
        return id;
    }

    public static Action getAction(int actionId) {
        if (actions.containsKey(actionId)) {
            return actions.get(actionId);
        }
        return null;
    }

    public static void removeAction(int actionId) {
        actions.remove(actionId);
    }

    public static void updateAction(Action action) {
        actions.put(action.getId(), action);
    }


}
