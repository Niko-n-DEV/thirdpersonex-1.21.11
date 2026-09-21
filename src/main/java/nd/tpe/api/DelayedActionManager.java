package nd.tpe.api;

import nd.tpe.api.action.MouseAction;
import java.util.ArrayDeque;
import java.util.Queue;

public class DelayedActionManager {
    private final Queue<MouseAction> actionQueue = new ArrayDeque();
    private boolean replayingActions = false;

    public void writeAction(MouseAction action) {
        this.actionQueue.add(action);
    }

    public void replayActions() {
        this.replayingActions = true;

        try {
            while(!this.actionQueue.isEmpty()) {
                ((MouseAction)this.actionQueue.remove()).play();
            }
        } finally {
            this.replayingActions = false;
        }

    }

    public boolean isReplayingActions() {
        return this.replayingActions;
    }
}