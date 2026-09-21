package nd.tpe.integration.modmenu;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import nd.tpe.integration.cloth.ClothModConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.class_437;

public class ModMenuIntegration implements ModMenuApi {
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return (parent) -> (class_437)AutoConfig.getConfigScreen(ClothModConfig.class, parent).get();
    }
}
