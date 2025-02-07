package xyz.qweru.api.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

import static xyz.qweru.api.ApiMain.mc;

public class TargetUtil {
    public static @Nullable Entity findClosestEntity(Vec3d init, double range, Predicate<Entity> predicate) {
        double d = Double.MAX_VALUE;
        Entity entity = null;
        for (Entity e : mc.world.getEntities()) {
            double distance = init.distanceTo(e.getPos());
            if(predicate.test(e) && distance <= range && distance < d) {
                entity = e;
                d = distance;
            }
        }
        return entity;
    }

    public static @Nullable Entity findClosestEntity(double range, Predicate<Entity> predicate) {
        return findClosestEntity(mc.player.getPos(), range, predicate);
    }
}
