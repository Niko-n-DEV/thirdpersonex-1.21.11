package nd.tpe.mixin;

import nd.tpe.ThirdPersonEx;
import nd.tpe.api.util.Rotation;
import net.minecraft.client.Minecraft; // class_310;
import net.minecraft.client.MouseHandler; // class_312;
import net.minecraft.client.player.LocalPlayer; // class_746;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(
        value = {MouseHandler.class},
        priority = 100000
)
public class MouseFixupMixin {
    @Shadow
    @Mutable
    private double accumulatedDX;
    @Shadow
    @Mutable
    private double accumulatedDY;

    @Inject(
            method = {"turnPlayer"},
            at = {@At("HEAD")}
    )
    public void preTurnPlayer(CallbackInfo ci) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            Rotation turnValues = ThirdPersonEx.getCameraManager().restorePlayerTurnValues();
            if (turnValues != null) {
                this.accumulatedDX = (double)turnValues.getYaw();
                this.accumulatedDY = (double)turnValues.getPitch();
            }

        }
    }
}
