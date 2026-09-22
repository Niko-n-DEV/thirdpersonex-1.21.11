package nd.tpe.impl;

import nd.tpe.api.adapter.IPlayerAdapter;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.VehicleEntity;

public record PlayerAdapter(Player player) implements IPlayerAdapter {
    public float getRotationYaw() {

        return this.player.method_36454();
    }

    public float getRotationPitch() {

        return this.player.method_36455();
    }

    public float getPrevRotationYaw() {

        return this.player.field_5982;
    }

    public float getPrevRotationPitch() {

        return this.player.field_6004;
    }

    public void setRotationYaw(float value) {
        this.player.setYRot(value);
//        this.player.method_36456(value);
    }

    public void setRotationPitch(float pitch) {

        this.player.method_36457(pitch);
    }

    public void setPrevRotationYaw(float yaw) {

        this.player.field_5982 = yaw;
    }

    public void setPrevRotationPitch(float pitch) {

        this.player.field_6004 = pitch;
    }

    public void setVehicleYaw(float value) {
        this.player.field_6241 = value;
        this.player.field_6259 = value;
        class_1297 vehicle = this.player.method_5854();
        if (vehicle != null) {
            vehicle.method_36456(value);
            vehicle.field_5982 = value;
        }

    }

    public Vec3 getPosition() {
        return this.player.();
    }

    public boolean isPassenger() {
        return this.player.method_5765();
    }

    public boolean isUsingItem() {
        return this.player.method_6115();
    }

    public boolean hasAllowedVehicle() {
        class_1297 vehicle = this.player.method_5854();
        return vehicle instanceof class_1498 || vehicle instanceof class_1452;
    }

    public boolean isElytraFlying() {
        return this.player.method_6128();
    }
}