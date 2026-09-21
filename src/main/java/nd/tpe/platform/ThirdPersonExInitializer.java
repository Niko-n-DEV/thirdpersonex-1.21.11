package nd.tpe.platform;

import nd.tpe.ThirdPersonEx;
import nd.tpe.integration.cloth.ClothModConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class ThirdPersonExInitializer implements ModInitializer {
    public void onInitialize() {
        if (FabricLoader.getInstance().isModLoaded("cloth-config")) {
            ThirdPersonEx.getCameraManager().setConfig(ClothModConfig.create());
        }

    }
}
