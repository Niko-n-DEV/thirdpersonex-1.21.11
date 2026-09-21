package nd.tpe.api.config;

public class DefaultCustomCameraConfig implements CustomCameraConfig {
    public static final CustomCameraConfig INSTANCE = new DefaultCustomCameraConfig();

    private DefaultCustomCameraConfig() {
    }

    public boolean shouldAimPlayerOnInteract() {
        return true;
    }

    public int getAimDuration() {
        return 40;
    }

    public int getFollowYaw() {
        return 45;
    }

    public boolean hasFreeCameraDuringElytraFlight() {
        return false;
    }

    public boolean skipThirdPersonFrontView() {
        return false;
    }

    public int getPlayerRotationSpeed() {
        return 50;
    }

    public int getPitchChangeSpeed() {
        return 65;
    }
}