package nd.tpe.integration.cloth;

import nd.tpe.api.config.CustomCameraConfig;
import nd.tpe.api.config.DefaultCustomCameraConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry.BoundedDiscrete;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(
        name = "ThirdPersonEx"
)
public class ClothModConfig implements ConfigData, CustomCameraConfig {
    @Comment("Align player to camera on left & right clicks")
    public boolean aimOnInteract;
    @Comment("How long player will be aligned to camera after left & right clicks")
    @BoundedDiscrete(
            min = 10L,
            max = 200L
    )
    public int aimDuration;
    @Comment("Angle in degrees within the player will slightly follow camera yaw (while standing still)")
    @BoundedDiscrete(
            min = 0L,
            max = 90L
    )
    public int followYaw;
    @Comment("Does camera should rotate freely during elytra flight")
    public boolean freeCameraDuringElytraFlight;
    @Comment("Completely remove third-person front view")
    public boolean skipThirdPersonFrontView;
    @Comment("How fast player changes movement direction in third-person")
    @BoundedDiscrete(
            min = 10L,
            max = 100L
    )
    public int playerRotationSpeed;
    @Comment("How fast player pitch follows camera pitch in third-person")
    @BoundedDiscrete(
            min = 10L,
            max = 100L
    )
    public int pitchChangeSpeed;

    public ClothModConfig() {
        this.aimOnInteract = DefaultCustomCameraConfig.INSTANCE.shouldAimPlayerOnInteract();
        this.aimDuration = DefaultCustomCameraConfig.INSTANCE.getAimDuration();
        this.followYaw = DefaultCustomCameraConfig.INSTANCE.getFollowYaw();
        this.freeCameraDuringElytraFlight = DefaultCustomCameraConfig.INSTANCE.hasFreeCameraDuringElytraFlight();
        this.skipThirdPersonFrontView = DefaultCustomCameraConfig.INSTANCE.skipThirdPersonFrontView();
        this.playerRotationSpeed = DefaultCustomCameraConfig.INSTANCE.getPlayerRotationSpeed();
        this.pitchChangeSpeed = DefaultCustomCameraConfig.INSTANCE.getPitchChangeSpeed();
    }

    public static CustomCameraConfig create() {
        AutoConfig.register(ClothModConfig.class, JanksonConfigSerializer::new);
        return (CustomCameraConfig)AutoConfig.getConfigHolder(ClothModConfig.class).getConfig();
    }

    public boolean shouldAimPlayerOnInteract() {
        return this.aimOnInteract;
    }

    public int getAimDuration() {
        return this.aimDuration;
    }

    public int getFollowYaw() {
        return this.followYaw;
    }

    public boolean hasFreeCameraDuringElytraFlight() {
        return this.freeCameraDuringElytraFlight;
    }

    public boolean skipThirdPersonFrontView() {
        return this.skipThirdPersonFrontView;
    }

    public int getPlayerRotationSpeed() {
        return this.playerRotationSpeed;
    }

    public int getPitchChangeSpeed() {
        return this.pitchChangeSpeed;
    }
}