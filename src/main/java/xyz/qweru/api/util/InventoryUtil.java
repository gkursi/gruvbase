package xyz.qweru.api.util;

import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import net.minecraft.block.BlockState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;

import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

import static xyz.qweru.api.ApiMain.mc;

public class InventoryUtil {

    public static final int HOTBAR_START = 0;
    public static final int HOTBAR_END = 9;
    public static final int INVENTORY_START = 9;
    public static final int INVENTORY_END = 36;

    /**
     * @param min inclusive
     * @param max exclusive
     */
    public static int iterate(ItemConsumer fn, int min, int max) {
        PlayerInventory inv = mc.player.getInventory();
        for (int i = min; i < max; i++) {
            if(fn.consume(inv.getStack(i))) return i;
        }
        return -1;
    }

    public static int getBestTool(BlockState state, boolean onlyHotbar) {
        int i = -1;
        PlayerInventory inv = mc.player.getInventory();

        double breakSpeed = -1;
        for (int ii = 0; ii < (onlyHotbar ? HOTBAR_END : INVENTORY_END); ii++) {
            ItemStack stack = inv.getStack(ii);
            double speed = stack.getMiningSpeedMultiplier(state);
            if(speed > breakSpeed) {
                breakSpeed = speed;
                i = ii;
            }
        }

        if(breakSpeed == inv.getMainHandStack().getMiningSpeedMultiplier(state)) return inv.selectedSlot; // return the currently selected slot
        return i;
    }

    public static int getItem(Item item, boolean onlyHotbar) {
        return iterate(i -> i.getItem().equals(item), HOTBAR_START, onlyHotbar ? HOTBAR_END : INVENTORY_END);
    }

    public static int getItemCount(Predicate<ItemStack> filter) {
        AtomicInteger count = new AtomicInteger();
        iterate(itemStack -> {
            if(filter.test(itemStack)) count.getAndIncrement();
            return false;
        }, HOTBAR_START, INVENTORY_END);
        return count.get();
    }

    public static int getEnchantmentLevel(ItemStack itemStack, RegistryKey<Enchantment> enchantment) {
        if (itemStack.isEmpty()) return 0;
        Object2IntMap<RegistryEntry<Enchantment>> itemEnchantments = new Object2IntArrayMap<>();
        getEnchantments(itemStack, itemEnchantments);
        return getEnchantmentLevel(itemEnchantments, enchantment);
    }

    public static int getEnchantmentLevel(Object2IntMap<RegistryEntry<Enchantment>> itemEnchantments, RegistryKey<Enchantment> enchantment) {
        for (Object2IntMap.Entry<RegistryEntry<Enchantment>> entry : Object2IntMaps.fastIterable(itemEnchantments)) {
            if (entry.getKey().matchesKey(enchantment)) return entry.getIntValue();
        }
        return 0;
    }

    public static void getEnchantments(ItemStack itemStack, Object2IntMap<RegistryEntry<Enchantment>> enchantments) {
        enchantments.clear();

        if (!itemStack.isEmpty()) {
            Set<Object2IntMap.Entry<RegistryEntry<Enchantment>>> itemEnchantments = itemStack.getItem() == Items.ENCHANTED_BOOK
                    ? itemStack.get(DataComponentTypes.STORED_ENCHANTMENTS).getEnchantmentEntries()
                    : itemStack.getEnchantments().getEnchantmentEntries();

            for (Object2IntMap.Entry<RegistryEntry<Enchantment>> entry : itemEnchantments) {
                enchantments.put(entry.getKey(), entry.getIntValue());
            }
        }
    }

    public interface ItemConsumer {
        boolean consume(ItemStack stack);
    }
}
