package nd.tpe.api.util;

public class AngleUtils {
    public static float smoothAngle(float partialTicks, float prevAngle, float currentAngle) {
        double anglesDiff = ((double)currentAngle - (double)prevAngle + (double)180.0F) % (double)360.0F - (double)180.0F;
        return (float)((double)prevAngle + (double)partialTicks * (anglesDiff < (double)-180.0F ? anglesDiff + (double)360.0F : anglesDiff));
    }

    public static float wrapAngle(float prevAngle, float currentAngle) {
        double anglesDiff = ((double)currentAngle - (double)prevAngle + (double)180.0F) % (double)360.0F - (double)180.0F;
        return (float)((double)prevAngle + (anglesDiff < (double)-180.0F ? anglesDiff + (double)360.0F : anglesDiff));
    }

    public static float getDelta(float prevAngle, float currentAngle) {
        double anglesDiff = ((double)currentAngle - (double)prevAngle + (double)180.0F) % (double)360.0F - (double)180.0F;
        return (float)(anglesDiff < (double)-180.0F ? anglesDiff + (double)360.0F : anglesDiff);
    }

    public static float normalize(float angle) {
        float result = angle % 360.0F;
        if (result >= 180.0F) {
            result -= 360.0F;
        }

        if (result < -180.0F) {
            result += 360.0F;
        }

        return result;
    }

    public static float stepAngle(float partialTicks, float maxAngle, float prevAngle, float currentAngle) {
        double deltaAngle = (double)(partialTicks * getDelta(prevAngle, currentAngle));
        return prevAngle + MathUtils.clamp((float)deltaAngle, -maxAngle, maxAngle);
    }

    public static boolean equals(float a1, float a2) {
        return Math.abs(getDelta(a1, a2)) < 0.001F;
    }
}
