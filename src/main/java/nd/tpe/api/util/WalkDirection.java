package nd.tpe.api.util;

import nd.tpe.api.adapter.IMovementInputAdapter;

public enum WalkDirection {
    BACK(-180.0F, false, false, false, true),
    BACK_LEFT(-135.0F, true, false, false, true),
    LEFT(-90.0F, true, false, false, false),
    FORWARD_LEFT(-45.0F, true, false, true, false),
    FORWARD(0.0F, false, false, true, false),
    FORWARD_RIGHT(45.0F, false, true, true, false),
    RIGHT(90.0F, false, true, false, false),
    BACK_RIGHT(135.0F, false, true, false, true);

    private final float angle;
    private final boolean left;
    private final boolean right;
    private final boolean forward;
    private final boolean back;
    private final boolean diagonal;

    private WalkDirection(float angle, boolean left, boolean right, boolean forward, boolean back) {
        this.angle = angle;
        this.left = left;
        this.right = right;
        this.forward = forward;
        this.back = back;
        this.diagonal = (this.ordinal() & 1) == 1;
    }

    public float getAngle() {
        return this.angle;
    }

    public void setup(IMovementInputAdapter input) {
        this.applyKeys(input);
        input.setMoveStrafe(!this.left && !this.right ? 0.0F : (this.left ? 1.0F : -1.0F));
        input.setMoveForward(!this.forward && !this.back ? 0.0F : (this.forward ? 1.0F : -1.0F));
    }

    public void applyKeys(IMovementInputAdapter input) {
        input.setLeftKeyDown(this.left);
        input.setRightKeyDown(this.right);
        input.setForwardKeyDown(this.forward);
        input.setBackKeyDown(this.back);
    }

    public boolean isDiagonal() {
        return this.diagonal;
    }

    public float distanceTo(float angle) {
        return Math.abs(AngleUtils.normalize(angle - this.angle));
    }

    public double maxImpulse() {
        return this.diagonal ? 1.41421356237 : (double)1.0F;
    }

    public static WalkDirection byKeys(boolean left, boolean right, boolean forward, boolean back) {
        if (forward != back) {
            if (left == right) {
                return forward ? FORWARD : BACK;
            } else if (forward) {
                return left ? FORWARD_LEFT : FORWARD_RIGHT;
            } else {
                return left ? BACK_LEFT : BACK_RIGHT;
            }
        } else if (left != right) {
            return left ? LEFT : RIGHT;
        } else {
            return FORWARD;
        }
    }

    public static WalkDirection byInput(IMovementInputAdapter input) {
        return byKeys(input.isLeftKeyDown(), input.isRightKeyDown(), input.isForwardKeyDown(), input.isBackKeyDown());
    }

    public static WalkDirection approximate(float angle) {
        for(WalkDirection dir : values()) {
            if ((double)dir.distanceTo(angle) <= (double)22.5F) {
                return dir;
            }
        }

        return FORWARD;
    }

    public static WalkDirection toLeftOf(float angle) {
        angle = AngleUtils.normalize(angle);
        return byIndex(FORWARD.ordinal() + (int)Math.floor((double)(angle / 45.0F)));
    }

    public static WalkDirection toRightOf(float angle) {
        angle = AngleUtils.normalize(angle);
        return byIndex(FORWARD.ordinal() + (int)Math.ceil((double)(angle / 45.0F)));
    }

    public static WalkDirection byIndex(int index) {
        return values()[index % values().length];
    }
}
