package nd.tpe.mixin;

import nd.tpe.ThirdPersonEx;
import net.minecraft.world.entity.Entity; // class_1297;
import net.minecraft.client.Camera; // class_4184;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({Camera.class})
public abstract class CameraMixin {
    @Redirect(
            method = {"setup"},
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/Entity;getViewYRot(F)F"
            )
    )
    private float getYawHook(Entity entity, float tickDelta) {
        if (ThirdPersonEx.getCameraManager().hasCustomCamera()) {
            return ThirdPersonEx.getCameraManager().getCustomCamera().getYaw();
        } else {
            return entity != null ? entity.getViewYRot(tickDelta) : 0.0F;
        }
    }

    @Redirect(
            method = {"setup"},
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/Entity;getViewXRot(F)F"
            )
    )
    private float getPitchHook(Entity entity, float tickDelta) {
        if (ThirdPersonEx.getCameraManager().hasCustomCamera()) {
            return ThirdPersonEx.getCameraManager().getCustomCamera().getPitch();
        } else {
            return entity != null ? entity.getViewXRot(tickDelta) : 0.0F;
        }
    }
}