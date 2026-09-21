package nd.tpe.api.adapter;

public interface IClientAdapter {
    boolean isFirstPerson();

    boolean isCameraMirrored();

    void updateHitResult();

    boolean isUsePressed();

    boolean isAttackPressed();

    default boolean isMousePressed() {
        return this.isUsePressed() || this.isAttackPressed();
    }

    IPlayerAdapter getPlayer();

    boolean isCameraOnPlayer();
}