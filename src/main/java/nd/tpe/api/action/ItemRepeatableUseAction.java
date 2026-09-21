package nd.tpe.api.action;

import nd.tpe.api.adapter.IClientAdapter;
import java.util.function.IntSupplier;

public class ItemRepeatableUseAction extends MouseAction {
    private final IClientAdapter client;
    private final IntSupplier itemUseCooldown;

    public ItemRepeatableUseAction(IClientAdapter client, IntSupplier itemUseCooldown, Runnable action) {
        super(action);
        this.client = client;
        this.itemUseCooldown = itemUseCooldown;
    }

    public void play() {
        if (this.client.isUsePressed() && this.itemUseCooldown.getAsInt() == 0 && !this.client.getPlayer().isUsingItem()) {
            super.play();
        }

    }
}