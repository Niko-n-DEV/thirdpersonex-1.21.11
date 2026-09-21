package nd.tpe.api;

import nd.tpe.api.adapter.IClientAdapter;
import nd.tpe.api.adapter.IMovementInputAdapter;
import nd.tpe.api.adapter.IPlayerAdapter;
import nd.tpe.api.config.CustomCameraConfig;
import nd.tpe.api.util.AngleUtils;
import nd.tpe.api.util.Rotation;
import net.minecraft.class_243;

public class CustomCamera {
    private final CustomCameraManager manager;
    private final CustomCameraConfig config;
    private final IClientAdapter client;
    private float followYaw;
    private Rotation cameraRotation;
    private Rotation playerRotation;
    private class_243 lastTickPlayerPos;
    private float targetKeyboardInputYaw;
    private float keyboardInputYaw = 0.0F;
    private Rotation mouseInput;
    private boolean wasMoving;
    private boolean isMoving;
    private boolean delayMouseActions;
    private long aimTicks;

    public CustomCamera(CustomCameraManager manager, IClientAdapter client, IPlayerAdapter player, CustomCameraConfig config) {
        this.mouseInput = Rotation.ZERO;
        this.wasMoving = false;
        this.isMoving = false;
        this.aimTicks = 0L;
        this.manager = manager;
        this.config = config;
        this.client = client;
        this.resetToPlayerView(player);
        this.lastTickPlayerPos = player.getPosition();
    }

    public void handleMovementInputs(IMovementInputAdapter input, TickPhase phase) {
        this.isMoving = input.isMoving();
        if (this.aimTicks <= 0L && this.isMoving) {
            if (phase == TickPhase.START) {
                this.targetKeyboardInputYaw = input.getInputDirection();
                input.redirect(0.0F);
            } else if (input.getMoveForward() <= 0.0F || input.getMoveStrafe() != 0.0F) {
                this.targetKeyboardInputYaw = input.getRawDirection();
                input.redirect(0.0F);
            }

        }
    }

    private void doAim() {
        this.aimTicks = (long)this.config.getAimDuration();
    }

    public void handlePlayerTurn(float deltaYaw, float deltaPitch) {
        this.mouseInput = this.mouseInput.add(deltaYaw, deltaPitch);
    }

    public void tick(TickPhase phase, IPlayerAdapter player) {
        class_243 currentPos = player.getPosition();
        boolean mousePressed = this.client.isMousePressed();
        if (phase == TickPhase.START) {
            if (!player.isPassenger() && !this.lastTickPlayerPos.equals(currentPos)) {
                this.resetToPlayerView(player);
            }

            this.wasMoving = this.isMoving;
            if (mousePressed && this.config.shouldAimPlayerOnInteract()) {
                this.doAim();
            } else if (this.aimTicks > 0L) {
                --this.aimTicks;
                if (this.aimTicks == 0L) {
                    this.keyboardInputYaw = 0.0F;
                }
            }
        } else {
            this.lastTickPlayerPos = currentPos;
            if (this.isMoving) {
                if (this.aimTicks <= 0L && !this.wasMoving) {
                    this.keyboardInputYaw = -AngleUtils.normalize(this.cameraRotation.getYaw() - this.playerRotation.getYaw());
                }

                float rotationSpeed = (float)this.config.getPlayerRotationSpeed() / 100.0F;
                this.keyboardInputYaw = AngleUtils.smoothAngle(rotationSpeed, this.keyboardInputYaw, this.targetKeyboardInputYaw);
            }

            if (!this.isMoving && !mousePressed) {
                float pitchChangeSpeed = (float)this.config.getPitchChangeSpeed() / 100.0F;
                this.playerRotation = this.playerRotation.withPitch(AngleUtils.smoothAngle(pitchChangeSpeed, this.playerRotation.getPitch(), this.cameraRotation.getPitch()));
            }
        }

    }

    public void setup(IClientAdapter client, IPlayerAdapter player, float partialTicks) {
        this.cameraRotation = this.cameraRotation.add(this.mouseInput);
        if (this.aimTicks > 0L) {
            this.playerRotation = this.cameraRotation;
        } else if (this.isMoving) {
            this.playerRotation = this.cameraRotation.addYaw(this.keyboardInputYaw);
        } else if (this.config.getFollowYaw() > 0 && !client.isMousePressed()) {
            if (Math.signum(this.mouseInput.getYaw()) != Math.signum(this.followYaw)) {
                this.followYaw = 0.0F;
            }

            this.followYaw += this.mouseInput.getYaw();
            if (Math.abs(this.followYaw) <= (float)this.config.getFollowYaw()) {
                this.playerRotation = this.playerRotation.addYaw((float)((double)this.mouseInput.getYaw() * ((double)1.0F - this.easeInExpo((double)(Math.abs(this.followYaw) / (float)this.config.getFollowYaw())))));
            }
        }

        this.playerRotation.applySafe(player);
        this.mouseInput = Rotation.ZERO;
    }

    public float getYaw() {
        return this.cameraRotation.getYaw();
    }

    public float getPitch() {
        return this.cameraRotation.getPitch();
    }

    public void handleMouseReset() {
        this.delayMouseActions = false;
    }

    public boolean handleMouseAction(IPlayerAdapter player, IClientAdapter client) {
        if (this.delayMouseActions) {
            return true;
        } else if (this.aimTicks == 0L && this.config.shouldAimPlayerOnInteract()) {
            this.doAim();
            this.cameraRotation.applySafe(player);
            client.updateHitResult();
            this.resetToPlayerView(player);
            this.delayMouseActions = true;
            return true;
        } else {
            return false;
        }
    }

    private void resetToPlayerView(IPlayerAdapter player) {
        this.playerRotation = player.getRotation();
        this.cameraRotation = this.playerRotation;
        this.followYaw = 0.0F;
    }

    private double easeInExpo(double x) {
        return x == (double)0.0F ? (double)0.0F : Math.pow((double)2.0F, (double)10.0F * x - (double)10.0F);
    }

    public void onDisable(IPlayerAdapter player) {
        this.cameraRotation.applySafeFully(player);
    }

    public Rotation getCameraRotation() {
        return this.cameraRotation;
    }

    public Rotation getPlayerRotation() {
        return this.playerRotation;
    }

    public CustomCamera setCameraRotation(Rotation cameraRotation) {
        this.cameraRotation = cameraRotation;
        return this;
    }
}