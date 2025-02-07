package xyz.qweru.api.event;

/**
 * Prevents cancelling the event, idk how else to call this
 */
public class ImmutableEvent extends Event {
    @Override
    public boolean isCancelled() {
        return false;
    }
}
