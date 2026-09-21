package nd.tpe.mixin;

import nd.tpe.ThirdPersonEx;
import nd.tpe.api.util.Rotation;
import net.minecraft.class_310;
import net.minecraft.class_312;
import net.minecraft.class_746;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(
        value = {class_312.class},
        priority = 100000
)
public class MouseFixupMixin {
    @Shadow
    @Mutable
    private double field_1789;
    @Shadow
    @Mutable
    private double field_1787;

    @Inject(
            method = {"turnPlayer"},
            at = {@At("HEAD")}
    )
    public void preTurnPlayer(CallbackInfo ci) {
        class_746 player = class_310.method_1551().field_1724;
        if (player != null) {
            Rotation turnValues = ThirdPersonEx.getCameraManager().restorePlayerTurnValues();
            if (turnValues != null) {
                this.field_1789 = (double)turnValues.getYaw();
                this.field_1787 = (double)turnValues.getPitch();
            }

        }
    }
}
