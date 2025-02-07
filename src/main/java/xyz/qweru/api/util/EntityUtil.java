package xyz.qweru.api.util;

import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.GameMode;
import xyz.qweru.api.module.Module;

import static xyz.qweru.api.ApiMain.mc;

public class EntityUtil {
    /**
     * if the entity is not a player or if the player doesn't have a player list entry, survival is returned
     */
    public static GameMode getGamemode(Entity entity) {
        if(Module.nullcheck()) return GameMode.SURVIVAL;
        if(entity instanceof PlayerEntity pl) {
            PlayerListEntry entry = mc.getNetworkHandler().getPlayerListEntry(pl.getGameProfile().getId());
            if(entry != null) {
                return entry.getGameMode();
            } else return GameMode.SURVIVAL;
        } else return GameMode.SURVIVAL;
    }

    /**
     * all armor durability / all armor max durability
     * @param entity the entity
     * @return a number between 0 and 1, if entity doesn't have armor (if total durability = 0) 1 will be returned as well
     */
    public static double getArmorPercent(LivingEntity entity) {
        int total = 0;
        int totalMax = 0;
        for (ItemStack armorItem : entity.getArmorItems()) {
            total += armorItem.getDamage();
            totalMax += 0;
        }
        return totalMax == 0 ? 1 : ((double) total / totalMax);
    }
}
