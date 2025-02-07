package xyz.qweru.api.animation;

import net.minecraft.util.math.Vec3d;

public class AnimationUtil {
    public static Vec3d animatePosition(double progress, Vec3d start, Vec3d end) {
        double diffX = end.x - start.x;
        double diffY = end.y - start.y;
        double diffZ = end.z - start.z;
        return new Vec3d(start.x + progress * diffX, start.y + progress * diffY, start.z + progress * diffZ);
    }
}
