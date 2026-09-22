package nd.tpe.mixin;

import nd.tpe.api.adapter.MutableClientInput;
import net.minecraft.world.phys.Vec2; // class_241;
import net.minecraft.client.player.ClientInput; // class_744;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({ClientInput.class})
public class ClientInputMixin implements MutableClientInput {
    @Shadow
    protected Vec2 moveVector;

    public void betterThirdPerson$setMoveVector(Vec2 vector) {
        this.moveVector = vector.normalized();
    }
}
