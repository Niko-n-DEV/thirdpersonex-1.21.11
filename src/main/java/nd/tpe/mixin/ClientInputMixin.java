package nd.tpe.mixin;

import nd.tpe.api.adapter.MutableClientInput;
import net.minecraft.class_241;
import net.minecraft.class_744;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({class_744.class})
public class ClientInputMixin implements MutableClientInput {
    @Shadow
    protected class_241 field_55868;

    public void betterThirdPerson$setMoveVector(class_241 vector) {
        this.field_55868 = vector.method_35581();
    }
}
