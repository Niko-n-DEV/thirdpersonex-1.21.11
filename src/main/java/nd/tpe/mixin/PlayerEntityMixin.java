package nd.tpe.mixin;

import nd.tpe.ThirdPersonEx;
import nd.tpe.api.TickPhase;
import nd.tpe.impl.PlayerAdapter;
import net.minecraft.class_1657;
import net.minecraft.class_746;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({class_1657.class})
public class PlayerEntityMixin {
    @Inject(
            method = {"tick"},
            at = {@At("HEAD")}
    )
    public void onPrePlayerTick(CallbackInfo ci) {
        class_1657 player = (class_1657)this;
        if (player instanceof class_746) {
            ThirdPersonEx.getCameraManager().onPlayerTick(new PlayerAdapter(player), TickPhase.START);
        }

    }

    @Inject(
            method = {"tick"},
            at = {@At("TAIL")}
    )
    public void onPostPlayerTick(CallbackInfo ci) {
        class_1657 player = (class_1657)this;
        if (player instanceof class_746) {
            ThirdPersonEx.getCameraManager().onPlayerTick(new PlayerAdapter(player), TickPhase.END);
        }

    }
}