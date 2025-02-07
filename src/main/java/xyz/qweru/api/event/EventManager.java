package xyz.qweru.api.event;

import java.util.ArrayList;
import java.util.Iterator;

import static xyz.qweru.gruvhack.Gruvhack.LOGGER;

/**
 * best event system ong
 * @param <T> event
 */
public class EventManager<T extends Event> {
    private final ArrayList<PriorityCallback<T>> events = new ArrayList<>();

    /**
     * @return the id (hashcode) of the callback, used for unsubscribing
     */
    public int subscribe(Callback<T> callback, int priority) {
        int i;
        for (i = 0; i < events.size(); i++) {
            if(events.get(i).priority < priority) break;
        }
        events.add(i, new PriorityCallback<>(priority, callback));

        // has a 1/4000000000 chance of being the same, bugs will never be reproducible :3
        return callback.hashCode();
    }

    public int subscribe(Callback<T> callback) {
        return subscribe(callback, 0);
    }

    /**
     * @param id the id provided when subscribing
     */
    public void unsubscribe(int id) {
        Iterator<PriorityCallback<T>> iterator = events.iterator();
        int matchCount = 0;
        while (iterator.hasNext()) {
            PriorityCallback<T> next = iterator.next();
            if(next.callback.hashCode() == id) {
                iterator.remove();
                matchCount++;
            }
        }
        if(matchCount > 1) LOGGER.error("More than 1 id matched when removing callbacks!! This will likely cause bugs."); // to identify the bug if it does happen
    }

    /**
     * calls the event. if any of the callbacks cancels the event, execution is stopped
     */
    public T call(T context) {
        for (PriorityCallback<T> priorityCallback : events) {
            priorityCallback.callback.call(context);
            if(context.isCancelled()) break;
        }
        return context;
    }

    record PriorityCallback<T extends Event>(int priority, Callback<T> callback) {}

    @FunctionalInterface
    public interface Callback<T extends Event> {
        void call(T obj);
    }

}
