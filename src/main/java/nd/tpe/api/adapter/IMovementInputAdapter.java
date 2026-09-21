package nd.tpe.api.adapter;

import nd.tpe.api.util.AngleUtils;
import nd.tpe.api.util.WalkDirection;

public interface IMovementInputAdapter {
    boolean isLeftKeyDown();

    boolean isRightKeyDown();

    boolean isForwardKeyDown();

    boolean isBackKeyDown();

    void setLeftKeyDown(boolean var1);

    void setRightKeyDown(boolean var1);

    void setForwardKeyDown(boolean var1);

    void setBackKeyDown(boolean var1);

    float getMoveForward();

    float getMoveStrafe();

    void setMoveForward(float var1);

    void setMoveStrafe(float var1);

    default boolean isMoving() {
        return this.isForwardKeyDown() != this.isBackKeyDown() || this.isLeftKeyDown() != this.isRightKeyDown();
    }

    default float getInputDirection() {
        return WalkDirection.byInput(this).getAngle();
    }

    default float getRawDirection() {
        return AngleUtils.normalize((float)Math.toDegrees(Math.atan2((double)(-this.getMoveStrafe()), (double)this.getMoveForward())));
    }

    default void setMoveDirection(float angle) {
        float moveImpulse = Math.max(Math.abs(this.getMoveForward()), Math.abs(this.getMoveStrafe()));
        double angleRad = Math.toRadians((double)angle);
        this.setMoveForward((float)Math.cos(angleRad) * moveImpulse);
        this.setMoveStrafe((float)(-Math.sin(angleRad)) * moveImpulse);
    }

    default void redirect(float angle) {
        this.setMoveDirection(angle);
        WalkDirection.approximate(angle).applyKeys(this);
    }
}