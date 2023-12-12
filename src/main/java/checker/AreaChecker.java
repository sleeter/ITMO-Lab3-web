package checker;

import entity.DotEntity;

public class AreaChecker {
    public static boolean check(DotEntity dot) {
        float x = dot.getX();
        float y = dot.getY();
        float r = dot.getR();
        return isCircle(x, y, r) || isTriangle(x, y, r) || isRectangle(x, y, r);
    }
    private static boolean isCircle(float x, float y, float r) {
        return x >= 0 && y >= 0 && x * x + y * y <= r * r / 4;
    }
    private static boolean isTriangle(float x, float y, float r) {
        return x <= 0 && y >= 0 && y <= x + r / 2;
    }
    private static boolean isRectangle(float x, float y, float r) {
        return x <= 0 && y <= 0 && x >= -r && y >= -r / 2;
    }
}
