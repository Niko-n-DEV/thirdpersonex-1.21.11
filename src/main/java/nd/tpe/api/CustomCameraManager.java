package nd.tpe.api;

import nd.tpe.api.action.MouseAction;
import nd.tpe.api.adapter.IClientAdapter;
import nd.tpe.api.adapter.IMovementInputAdapter;
import nd.tpe.api.adapter.IPlayerAdapter;
import nd.tpe.api.config.CustomCameraConfig;
import nd.tpe.api.config.DefaultCustomCameraConfig;
import nd.tpe.api.util.Rotation;

public class CustomCameraManager {
    private final IClientAdapter client;
    private CustomCamera customCamera;
    private CustomCameraConfig config;
    private final DelayedActionManager delayedActions;
    private final MouseInputHandler mouseInputHandler;

    public CustomCameraManager(IClientAdapter client) {
        this.config = DefaultCustomCameraConfig.INSTANCE;
        this.delayedActions = new DelayedActionManager();
        this.mouseInputHandler = new MouseInputHandler();
        this.client = client;
    }

    public CustomCamera getCustomCamera() {
        return this.customCamera;
    }

    public boolean hasCustomCamera() {
        return this.customCamera != null;
    }

    public void setConfig(CustomCameraConfig config) {
        this.config = config;
    }

    public CustomCameraConfig getConfig() {
        return this.config;
    }

    private boolean mustHaveCustomCamera(IPlayerAdapter player) {
        boolean mustHaveCustomCamera = !this.client.isFirstPerson() && this.client.isCameraOnPlayer() && !this.client.isCameraMirrored() && (!player.isPassenger() || player.hasAllowedVehicle()) && (this.config.hasFreeCameraDuringElytraFlight() || !player.isElytraFlying());
        boolean customCameraInitialized = this.customCamera != null;
        if (mustHaveCustomCamera != customCameraInitialized) {
            if (mustHaveCustomCamera) {
                this.customCamera = new CustomCamera(this, this.client, player, this.config);
            } else {
                this.customCamera.onDisable(player);
                this.customCamera = null;
            }
        }

        return mustHaveCustomCamera;
    }

    public void onPlayerTick(IPlayerAdapter player, TickPhase phase) {
        if (this.hasCustomCamera()) {
            this.customCamera.tick(phase, player);
        }

    }

    public void onInputEvents(IPlayerAdapter player) {
        if (this.mustHaveCustomCamera(player)) {
            this.customCamera.handleMouseReset();
        }

        this.delayedActions.replayActions();
    }

    public void handleMovementInputs(IPlayerAdapter player, IMovementInputAdapter inputs, TickPhase phase) {
        if (this.mustHaveCustomCamera(player)) {
            this.customCamera.handleMovementInputs(inputs, phase);
        }

    }

    public void onRenderTickStart(IPlayerAdapter player, float partialTicks) {
        if (this.mustHaveCustomCamera(player)) {
            this.customCamera.setup(this.client, player, partialTicks);
        }
    }

    public boolean onMouseAction(IPlayerAdapter player, MouseAction action) {
        if (!this.delayedActions.isReplayingActions() && this.mustHaveCustomCamera(player)) {
            boolean delay = this.customCamera.handleMouseAction(player, this.client);
            if (delay) {
                this.delayedActions.writeAction(action);
            }

            return delay;
        } else {
            return false;
        }
    }

    public void startPlayerTurning(double turnX, double turnY) {
        if (this.hasCustomCamera()) {
            this.mouseInputHandler.start(turnX, turnY);
        }

    }

    public boolean onPlayerTurn(TickPhase phase, IPlayerAdapter player) {
        return this.hasCustomCamera() ? this.mouseInputHandler.update(this.customCamera, phase, player) : false;
    }

    public void stopPlayerTurning() {
        if (this.hasCustomCamera()) {
            this.mouseInputHandler.stop();
        }

    }

    public Rotation restorePlayerTurnValues() {
        return this.hasCustomCamera() ? this.mouseInputHandler.getUnusedInputValues() : null;
    }
}