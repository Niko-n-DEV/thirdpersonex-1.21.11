package nd.tpe.mixin;

import nd.tpe.ThirdPersonEx;
import net.minecraft.client.Minecraft; // class_310;
import net.minecraft.client.MouseHandler; // class_312;
import net.minecraft.client.player.LocalPlayer; // class_746;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(
        value = {MouseHandler.class},
        priority = -1000
)
public class MouseMixin {
    @Shadow
    private double accumulatedDX;
    @Shadow
    private double accumulatedDY;

    @Inject(
            method = {"turnPlayer"},
            at = {@At("HEAD")}
    )
    public void preChangeLookDirection(CallbackInfo ci) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            ThirdPersonEx.getCameraManager().startPlayerTurning(this.accumulatedDX, this.accumulatedDY);
        }
    }

    @Inject(
            method = {"turnPlayer"},
            at = {@At("TAIL")}
    )
    public void postChangeLookDirection(CallbackInfo ci) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            ThirdPersonEx.getCameraManager().stopPlayerTurning();
        }
    }
}