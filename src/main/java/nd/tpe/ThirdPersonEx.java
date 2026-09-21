package nd.tpe;

import nd.tpe.api.CustomCameraManager;
import nd.tpe.impl.ClientAdapter;

public class ThirdPersonEx  {
	public static final String MOD_ID = "thirdpersonex";

    private static CustomCameraManager manager;

    public static CustomCameraManager getCameraManager() {
        return manager;
    }

    static {
        manager = new CustomCameraManager(ClientAdapter.INSTANCE);
    }

}
