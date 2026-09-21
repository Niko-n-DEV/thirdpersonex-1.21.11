package nd.tpe.mixin;

import nd.tpe.ThirdPersonEx;
import nd.tpe.api.TickPhase;
import nd.tpe.impl.PlayerAdapter;
import net.minecraft.class_1297;
import net.minecraft.class_746;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({class_1297.class})
public class EntityMixin {
    @Inject(
            method = {"turn"},
            at = {@At("HEAD")}
    )
    public void preTurnHook(CallbackInfo ci) {
        class_1297 entity = (class_1297)this;
        if (entity instanceof class_746) {
            ThirdPersonEx.getCameraManager().onPlayerTurn(TickPhase.START, new PlayerAdapter((class_746)entity));
        }

    }

    @Inject(
            method = {"turn"},
            at = {@At("TAIL")}
    )
    public void postTurnHook(CallbackInfo ci) {
        class_1297 entity = (class_1297)this;
        if (entity instanceof class_746 player) {
            if (ThirdPersonEx.getCameraManager().onPlayerTurn(TickPhase.END, new PlayerAdapter(player)) && player.method_5854() != null) {
                player.method_5854().method_5644(player);
            }
        }

    }
}