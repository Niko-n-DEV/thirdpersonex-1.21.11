package nd.tpe.api.config;

public interface CustomCameraConfig {
    String AIM_ON_INTERACT_DESC = "Align player to camera on left & right clicks";
    String AIM_DURATION_DESC = "How long player will be aligned to camera after left & right clicks";
    int AIM_DURATION_MIN = 10;
    int AIM_DURATION_MAX = 200;
    String FOLLOW_YAW_DESC = "Angle in degrees within the player will slightly follow camera yaw (while standing still)";
    int FOLLOW_YAW_MIN = 0;
    int FOLLOW_YAW_MAX = 90;
    String FREE_CAMERA_DURING_ELYTRA_FLIGHT_DESC = "Does camera should rotate freely during elytra flight";
    String SKIP_THIRD_PERSON_FRONT_VIEW_DESC = "Completely remove third-person front view";
    String PLAYER_ROTATION_SPEED_DESC = "How fast player changes movement direction in third-person";
    int PLAYER_ROTATION_SPEED_MIN = 10;
    int PLAYER_ROTATION_SPEED_MAX = 100;
    String PITCH_CHANGE_SPEED_DESC = "How fast player pitch follows camera pitch in third-person";
    int PITCH_CHANGE_SPEED_MIN = 10;
    int PITCH_CHANGE_SPEED_MAX = 100;

    boolean shouldAimPlayerOnInteract();

    int getAimDuration();

    int getFollowYaw();

    boolean hasFreeCameraDuringElytraFlight();

    boolean skipThirdPersonFrontView();

    int getPlayerRotationSpeed();

    int getPitchChangeSpeed();
}