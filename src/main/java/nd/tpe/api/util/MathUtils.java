package nd.tpe.api.util;

public class MathUtils {
    public static float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(value, max));
    }

    public static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(value, max));
    }

    public static double easeInExpo(double x) {
        return x == (double)0.0F ? (double)0.0F : Math.pow((double)2.0F, (double)10.0F * x - (double)10.0F);
    }
}