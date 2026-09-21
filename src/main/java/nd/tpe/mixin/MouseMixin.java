package nd.tpe.mixin;

import nd.tpe.ThirdPersonEx;
import net.minecraft.class_310;
import net.minecraft.class_312;
import net.minecraft.class_746;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(
        value = {class_312.class},
        priority = -1000
)
public class MouseMixin {
    @Shadow
    private double field_1789;
    @Shadow
    private double field_1787;

    @Inject(
            method = {"turnPlayer"},
            at = {@At("HEAD")}
    )
    public void preChangeLookDirection(CallbackInfo ci) {
        class_746 player = class_310.method_1551().field_1724;
        if (player != null) {
            ThirdPersonEx.getCameraManager().startPlayerTurning(this.field_1789, this.field_1787);
        }
    }

    @Inject(
            method = {"turnPlayer"},
            at = {@At("TAIL")}
    )
    public void postChangeLookDirection(CallbackInfo ci) {
        class_746 player = class_310.method_1551().field_1724;
        if (player != null) {
            ThirdPersonEx.getCameraManager().stopPlayerTurning();
        }
    }
}