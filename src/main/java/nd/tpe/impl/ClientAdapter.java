package nd.tpe.impl;

import nd.tpe.api.adapter.IClientAdapter;
import nd.tpe.api.adapter.IPlayerAdapter;
import net.minecraft.class_310;

public class ClientAdapter implements IClientAdapter {
    public static final ClientAdapter INSTANCE = new ClientAdapter();

    private ClientAdapter() {
    }

    public boolean isFirstPerson() {
        return class_310.method_1551().field_1690.method_31044().method_31034();
    }

    public boolean isCameraOnPlayer() {
        return class_310.method_1551().method_1560() == null || class_310.method_1551().method_1560() == class_310.method_1551().field_1724;
    }

    public boolean isCameraMirrored() {
        return class_310.method_1551().field_1690.method_31044().method_31035();
    }

    public IPlayerAdapter getPlayer() {
        return new PlayerAdapter(class_310.method_1551().field_1724);
    }

    public boolean isUsePressed() {
        return class_310.method_1551().field_1690.field_1904.method_1434();
    }

    public boolean isAttackPressed() {
        return class_310.method_1551().field_1690.field_1886.method_1434();
    }

    public void updateHitResult() {
        class_310.method_1551().field_1773.method_3190(1.0F);
    }
}