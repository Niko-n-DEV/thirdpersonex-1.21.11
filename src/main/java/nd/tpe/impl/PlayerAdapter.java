package nd.tpe.impl;

import nd.tpe.api.adapter.IPlayerAdapter;
import net.minecraft.world.entity.Entity; // class_1297
import net.minecraft.world.entity.animal.pig.Pig; // class_1452
import net.minecraft.world.entity.animal.equine.Horse; // class_1498 - должен быть horse, но его нет.
import net.minecraft.world.entity.player.Player; // class_1657
import net.minecraft.world.phys.Vec3; // class_243

public record PlayerAdapter(Player player) implements IPlayerAdapter {
    public float getRotationYaw() {

        return this.player.getYRot();
    }

    public float getRotationPitch() {

        return this.player.getXRot();
    }

    public float getPrevRotationYaw() {

        return this.player.yRotO;
    }

    public float getPrevRotationPitch() {

        return this.player.xRotO;
    }

    public void setRotationYaw(float value) {
        this.player.setYRot(value);
//        this.player.method_36456(value);
    }

    public void setRotationPitch(float pitch) {

        this.player.setXRot(pitch);
    }

    public void setPrevRotationYaw(float yaw) {

        this.player.yRotO = yaw;
    }

    public void setPrevRotationPitch(float pitch) {

        this.player.xRotO = pitch;
    }

    public void setVehicleYaw(float value) {
        this.player.yRotO = value;
        this.player.setYRot(value);

        Entity vehicle = this.player.getVehicle();
        if (vehicle != null) {
            vehicle.setYRot(value);
            vehicle.yRotO = value;
        }

    }

    public Vec3 getPosition() {
        return this.player.position();
    }

    public boolean isPassenger() {
        return this.player.isPassenger();
    }

    public boolean isUsingItem() {
        return this.player.isUsingItem();
    }

    public boolean hasAllowedVehicle() {
        Entity vehicle = this.player.getVehicle();
        return vehicle instanceof Horse || vehicle instanceof Pig;
    }

    public boolean isElytraFlying() {
        return this.player.isFallFlying();
    }
}