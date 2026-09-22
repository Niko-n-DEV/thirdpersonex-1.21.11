package nd.tpe.mixin;

import nd.tpe.ThirdPersonEx;
import nd.tpe.api.TickPhase;
import nd.tpe.impl.PlayerAdapter;
import net.minecraft.world.entity.Entity; // class_1297;
import net.minecraft.client.player.LocalPlayer; // class_746;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({Entity.class})
public class EntityMixin {
    @Inject(
            method = {"turn"},
            at = {@At("HEAD")}
    )
    public void preTurnHook(CallbackInfo ci) {
        Entity entity = (Entity) (Object) this;
        if (entity instanceof LocalPlayer) {
            ThirdPersonEx.getCameraManager().onPlayerTurn(TickPhase.START, new PlayerAdapter((LocalPlayer)entity));
        }

    }

    @Inject(
            method = {"turn"},
            at = {@At("TAIL")}
    )
    public void postTurnHook(CallbackInfo ci) {
        Entity entity = (Entity) (Object) this;
        if (entity instanceof LocalPlayer player) {
            if (ThirdPersonEx.getCameraManager().onPlayerTurn(TickPhase.END, new PlayerAdapter(player)) && player.getVehicle() != null) {
                player.getVehicle().onPassengerTurned(player);
            }
        }

    }
}