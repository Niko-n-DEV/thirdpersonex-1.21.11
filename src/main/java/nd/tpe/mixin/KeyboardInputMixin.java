package nd.tpe.mixin;

import nd.tpe.ThirdPersonEx;
import nd.tpe.api.TickPhase;
import nd.tpe.api.adapter.IMovementInputAdapter;
import nd.tpe.api.adapter.IPlayerAdapter;
import nd.tpe.impl.MovementInputAdapter;
import nd.tpe.impl.PlayerAdapter;
import net.minecraft.class_310;
import net.minecraft.class_743;
import net.minecraft.class_744;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({class_743.class})
public class KeyboardInputMixin {
    @Inject(
            method = {"tick"},
            at = {@At("TAIL")}
    )
    public void tickHook(CallbackInfo ci) {
        if (ThirdPersonEx.getCameraManager().hasCustomCamera()) {
            IPlayerAdapter player = new PlayerAdapter(class_310.method_1551().field_1724);
            IMovementInputAdapter inputs = new MovementInputAdapter((class_744)this);
            ThirdPersonEx.getCameraManager().handleMovementInputs(player, inputs, TickPhase.START);
        }

    }
}