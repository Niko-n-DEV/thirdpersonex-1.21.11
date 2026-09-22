package nd.tpe.impl;

import nd.tpe.api.adapter.IClientAdapter;
import nd.tpe.api.adapter.IPlayerAdapter;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;

public class ClientAdapter implements IClientAdapter {
    public static final ClientAdapter INSTANCE = new ClientAdapter();

    private ClientAdapter() {
    }

    public boolean isFirstPerson() {
//        return class_310.method_1551().field_1690.method_31044().method_31034();
        return Minecraft.getInstance().options.getCameraType().isFirstPerson();
    }

    public boolean isCameraOnPlayer() {
//        return class_310.method_1551().method_1560() == null || class_310.method_1551().method_1560() == class_310.method_1551().field_1724;
        return Minecraft.getInstance().getCameraEntity() == null || Minecraft.getInstance().getCameraEntity() == Minecraft.getInstance().player;
    }

    public boolean isCameraMirrored() {
        return Minecraft.getInstance().options.getCameraType().isMirrored();
//        return class_310.method_1551().field_1690.method_31044().method_31035();
    }

    public IPlayerAdapter getPlayer() {
        return new PlayerAdapter(Minecraft.getInstance().player);
//        return new PlayerAdapter(class_310.method_1551().field_1724);
    }

    public boolean isUsePressed() {
        return Minecraft.getInstance().options.keyUse.consumeClick();
//        return class_310.method_1551().field_1690.field_1904.method_1434();
    }

    public boolean isAttackPressed() {
        return Minecraft.getInstance().options.keyAttack.consumeClick();
//        return class_310.method_1551().field_1690.field_1886.method_1434();
    }

    public void updateHitResult() {
        Minecraft.getInstance().gameRenderer.updateCamera(DeltaTracker.ONE);
//        class_310.method_1551().field_1773.method_3190(1.0F);
    }
}