package nd.tpe.api.adapter;

import nd.tpe.api.util.AngleUtils;
import nd.tpe.api.util.Rotation;
import net.minecraft.class_243;

public interface IPlayerAdapter {
    float getRotationYaw();

    float getRotationPitch();

    float getPrevRotationYaw();

    float getPrevRotationPitch();

    void setRotationYaw(float var1);

    void setRotationPitch(float var1);

    void setPrevRotationYaw(float var1);

    void setPrevRotationPitch(float var1);

    void setVehicleYaw(float var1);

    class_243 getPosition();

    boolean isPassenger();

    boolean isUsingItem();

    boolean hasAllowedVehicle();

    boolean isElytraFlying();

    default void setRotationYawSafe(float value) {
        float fixedValue = AngleUtils.wrapAngle(this.getRotationYaw(), value);
        this.setRotationYaw(fixedValue);
        if (this.isPassenger()) {
            this.setVehicleYaw(fixedValue);
        }

    }

    default Rotation getRotation() {
        return new Rotation(this.getRotationYaw(), this.getRotationPitch());
    }
}