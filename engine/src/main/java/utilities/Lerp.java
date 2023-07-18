package utilities;

public class Lerp {

    // Interpolates between point a and point b
    public static float twoPoints(float a, float b, float f) {
        return a + f * (b - a);
    }

    // Perform a simple linear lerp between a and b
    public static float simpleLinear(float a, float b, float lambda) {
        return a + lambda * (b - a);
    }

    // Interpolates a value within a range.
    public static float range(float a, float l, float h) {
        return l + a * (h - l);
    }
}
