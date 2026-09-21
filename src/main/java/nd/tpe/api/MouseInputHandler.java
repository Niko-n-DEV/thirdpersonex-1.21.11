package nd.tpe.api;

import nd.tpe.api.adapter.IPlayerAdapter;
import nd.tpe.api.util.MathUtils;
import nd.tpe.api.util.Rotation;

public class MouseInputHandler {
    private boolean reading = false;
    private double inputX = (double)0.0F;
    private double inputY = (double)0.0F;
    private float prevYaw = 0.0F;
    private float prevPitch = 0.0F;

    public boolean update(CustomCamera camera, TickPhase phase, IPlayerAdapter player) {
        if (phase == TickPhase.START) {
            this.inputX = (double)0.0F;
            this.inputY = (double)0.0F;
            this.prevYaw = player.getRotationYaw();
            this.prevPitch = player.getRotationPitch();
            player.setRotationPitch(0.0F);
            player.setPrevRotationPitch(player.getPrevRotationPitch() - this.prevPitch);
            return false;
        } else {
            float deltaYaw = player.getRotationYaw() - this.prevYaw;
            float deltaPitch = player.getRotationPitch();
            if (deltaYaw != 0.0F || deltaPitch != 0.0F) {
                camera.handlePlayerTurn(deltaYaw, deltaPitch);
                player.setRotationYaw(player.getRotationYaw() - deltaYaw);
                player.setRotationPitch(player.getRotationPitch() - deltaPitch);
                player.setPrevRotationYaw(player.getPrevRotationYaw() - deltaYaw);
                player.setPrevRotationPitch(player.getPrevRotationPitch() - deltaPitch);
            }

            player.setRotationPitch(MathUtils.clamp(player.getRotationPitch() + this.prevPitch, -90.0F, 90.0F));
            player.setPrevRotationPitch(MathUtils.clamp(player.getPrevRotationPitch() + this.prevPitch, -90.0F, 90.0F));
            return deltaYaw != 0.0F || deltaPitch != 0.0F;
        }
    }

    public void start(double inputX, double inputY) {
        this.reading = true;
        this.inputX = inputX;
        this.inputY = inputY;
    }

    public Rotation getUnusedInputValues() {
        return !this.reading || this.inputX == (double)0.0F && this.inputY == (double)0.0F ? null : new Rotation((float)this.inputX, (float)this.inputY);
    }

    public void stop() {
        this.reading = false;
    }
}