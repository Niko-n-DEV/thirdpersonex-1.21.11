package nd.tpe.api.util;

import nd.tpe.api.adapter.IPlayerAdapter;
import net.minecraft.world.phys.Vec3; // class_243;

public class Rotation {
    public static final Rotation ZERO = new Rotation(0.0F, 0.0F);
    private final float yaw;
    private final float pitch;

    public Rotation(float yaw, float pitch) {
        this.yaw = yaw;
        this.pitch = MathUtils.clamp(pitch, -90.0F, 90.0F);
    }

    public void apply(IPlayerAdapter player) {
        player.setRotationYaw(this.yaw);
        player.setRotationPitch(this.pitch);
    }

    public void applySafe(IPlayerAdapter player) {
        player.setRotationYawSafe(this.yaw);
        player.setRotationPitch(this.pitch);
    }

    public void applySafeFully(IPlayerAdapter player) {
        player.setRotationYawSafe(this.yaw);
        player.setPrevRotationYaw(this.yaw);
        player.setRotationPitch(this.pitch);
        player.setPrevRotationPitch(this.pitch);
    }

    public float getYaw() {
        return this.yaw;
    }

    public float getPitch() {
        return this.pitch;
    }

    public Vec3 asDirection() {
        float f = this.pitch * ((float)Math.PI / 180F);
        float g = -this.yaw * ((float)Math.PI / 180F);
        double h = Math.cos((double)g);
        double i = Math.sin((double)g);
        double j = Math.cos((double)f);
        double k = Math.sin((double)f);
        return new Vec3(i * j, -k, h * j);
    }

    public Rotation add(Rotation rotation) {
        return this.add(rotation.yaw, rotation.pitch);
    }

    public Rotation addScaled(Rotation rotation, float scale) {
        return this.addScaled(rotation.yaw, rotation.pitch, scale);
    }

    public Rotation add(float yaw, float pitch) {
        return yaw == 0.0F && pitch == 0.0F ? this : new Rotation(this.yaw + yaw, this.pitch + pitch);
    }

    public Rotation addScaled(float yaw, float pitch, float scale) {
        return yaw == 0.0F && pitch == 0.0F ? this : new Rotation(this.yaw + yaw * scale, this.pitch + pitch * scale);
    }

    public double distanceTo(Rotation rotation) {
        double dy = (double)AngleUtils.getDelta(this.yaw, rotation.getYaw());
        double dp = (double)(rotation.getPitch() - this.pitch);
        return Math.sqrt(dy * dy + dp * dp);
    }

    public static Rotation fromPlayer(IPlayerAdapter player) {
        return new Rotation(player.getRotationYaw(), player.getRotationPitch());
    }

    public static Rotation fromDirection(double x, double y, double z) {
        double dist = Math.sqrt(x * x + z * z);
        float yaw = (float)(Math.atan2(z, x) * (double)180.0F / Math.PI) - 90.0F;
        float pitch = (float)(-(Math.atan2(y, dist) * (double)180.0F / Math.PI));
        return new Rotation(yaw, pitch);
    }

    public Rotation interpolate(Rotation target, float progress) {
        return new Rotation(AngleUtils.smoothAngle(progress, this.yaw, target.yaw), this.pitch + (target.pitch - this.pitch) * progress);
    }

    public Rotation clampedInterpolate(Rotation target, float progress, float maxAngle) {
        return new Rotation(AngleUtils.stepAngle(progress, maxAngle, this.yaw, target.yaw), AngleUtils.stepAngle(progress, maxAngle, this.pitch, target.pitch));
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            Rotation rotation = (Rotation)o;
            return AngleUtils.equals(rotation.yaw, this.yaw) && AngleUtils.equals(rotation.pitch, this.pitch);
        } else {
            return false;
        }
    }

    public boolean isZero() {
        return this.yaw == 0.0F && this.pitch == 0.0F;
    }

    public double size() {
        return Math.sqrt((double)(this.yaw * this.yaw + this.pitch * this.pitch));
    }

    public String toString() {
        return "(" + this.yaw + ", " + this.pitch + ")";
    }

    public Rotation addYaw(float yaw) {
        return yaw == 0.0F ? this : new Rotation(this.yaw + yaw, this.pitch);
    }

    public Rotation addPitch(float pitch) {
        return pitch == 0.0F ? this : new Rotation(this.yaw, this.pitch + pitch);
    }

    public Rotation withYaw(float yaw) {
        return new Rotation(yaw, this.pitch);
    }

    public Rotation withPitch(float pitch) {
        return new Rotation(this.yaw, pitch);
    }
}