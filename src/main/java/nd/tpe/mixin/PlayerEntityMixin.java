package nd.tpe.mixin;

import nd.tpe.ThirdPersonEx;
import nd.tpe.api.TickPhase;
import nd.tpe.impl.PlayerAdapter;
import net.minecraft.world.entity.player.Player; // class_1657;
import net.minecraft.client.player.LocalPlayer; // class_746;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({Player.class})
public class PlayerEntityMixin {
    @Inject(
            method = {"tick"},
            at = {@At("HEAD")}
    )
    public void onPrePlayerTick(CallbackInfo ci) {
        Player player = (Player) (Object) this;
        if (player instanceof LocalPlayer) {
            ThirdPersonEx.getCameraManager().onPlayerTick(new PlayerAdapter(player), TickPhase.START);
        }

    }

    @Inject(
            method = {"tick"},
            at = {@At("TAIL")}
    )
    public void onPostPlayerTick(CallbackInfo ci) {
        Player player = (Player) (Object) this;
        if (player instanceof LocalPlayer) {
            ThirdPersonEx.getCameraManager().onPlayerTick(new PlayerAdapter(player), TickPhase.END);
        }

    }
}