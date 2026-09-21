package nd.tpe.mixin;

import nd.tpe.ThirdPersonEx;
import net.minecraft.class_1297;
import net.minecraft.class_4184;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({class_4184.class})
public abstract class CameraMixin {
    @Redirect(
            method = {"setup"},
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/Entity;getViewYRot(F)F"
            )
    )
    private float getYawHook(class_1297 entity, float tickDelta) {
        if (ThirdPersonEx.getCameraManager().hasCustomCamera()) {
            return ThirdPersonEx.getCameraManager().getCustomCamera().getYaw();
        } else {
            return entity != null ? entity.method_5705(tickDelta) : 0.0F;
        }
    }

    @Redirect(
            method = {"setup"},
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/Entity;getViewXRot(F)F"
            )
    )
    private float getPitchHook(class_1297 entity, float tickDelta) {
        if (ThirdPersonEx.getCameraManager().hasCustomCamera()) {
            return ThirdPersonEx.getCameraManager().getCustomCamera().getPitch();
        } else {
            return entity != null ? entity.method_5695(tickDelta) : 0.0F;
        }
    }
}