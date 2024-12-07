package org.masonio.enthrallment;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

import static java.util.Map.entry;

/**
 * Enum that helps with different item groups and contains helper methods
 */
public enum ItemGroup {
    SWORD, PICKAXE, AXE, HELMET, CHESTPLATE, LEGGINGS, BOOTS, SHERD;

    private static final Map<Material, ItemGroup> itemGroups = Map.<Material, ItemGroup>ofEntries(
            //Swords
            entry(Material.WOODEN_SWORD, ItemGroup.SWORD),
            entry(Material.STONE_SWORD, ItemGroup.SWORD),
            entry(Material.GOLDEN_SWORD, ItemGroup.SWORD),
            entry(Material.IRON_SWORD, ItemGroup.SWORD),
            entry(Material.DIAMOND_SWORD, ItemGroup.SWORD),
            entry(Material.NETHERITE_SWORD, ItemGroup.SWORD),
            //Pickaxes
            entry(Material.WOODEN_PICKAXE, ItemGroup.PICKAXE),
            entry(Material.STONE_PICKAXE, ItemGroup.PICKAXE),
            entry(Material.GOLDEN_PICKAXE, ItemGroup.PICKAXE),
            entry(Material.IRON_PICKAXE, ItemGroup.PICKAXE),
            entry(Material.DIAMOND_PICKAXE, ItemGroup.PICKAXE),
            entry(Material.NETHERITE_PICKAXE, ItemGroup.PICKAXE),
            //Axes
            entry(Material.WOODEN_AXE, ItemGroup.AXE),
            entry(Material.STONE_AXE, ItemGroup.AXE),
            entry(Material.GOLDEN_AXE, ItemGroup.AXE),
            entry(Material.IRON_AXE, ItemGroup.AXE),
            entry(Material.DIAMOND_AXE, ItemGroup.AXE),
            entry(Material.NETHERITE_AXE, ItemGroup.AXE),
            //Helmets
            entry(Material.LEATHER_HELMET, ItemGroup.HELMET),
            entry(Material.GOLDEN_HELMET, ItemGroup.HELMET),
            entry(Material.IRON_HELMET, ItemGroup.HELMET),
            entry(Material.TURTLE_HELMET, ItemGroup.HELMET),
            entry(Material.DIAMOND_HELMET, ItemGroup.HELMET),
            entry(Material.NETHERITE_HELMET, ItemGroup.HELMET),
            //Chestplates
            entry(Material.LEATHER_CHESTPLATE, ItemGroup.CHESTPLATE),
            entry(Material.GOLDEN_CHESTPLATE, ItemGroup.CHESTPLATE),
            entry(Material.IRON_CHESTPLATE, ItemGroup.CHESTPLATE),
            entry(Material.DIAMOND_CHESTPLATE, ItemGroup.CHESTPLATE),
            entry(Material.NETHERITE_CHESTPLATE, ItemGroup.CHESTPLATE),
            //Leggings
            entry(Material.LEATHER_LEGGINGS, ItemGroup.LEGGINGS),
            entry(Material.GOLDEN_LEGGINGS, ItemGroup.LEGGINGS),
            entry(Material.IRON_LEGGINGS, ItemGroup.LEGGINGS),
            entry(Material.DIAMOND_LEGGINGS, ItemGroup.LEGGINGS),
            entry(Material.NETHERITE_LEGGINGS, ItemGroup.LEGGINGS),
            //Boots
            entry(Material.LEATHER_BOOTS, ItemGroup.BOOTS),
            entry(Material.GOLDEN_BOOTS, ItemGroup.BOOTS),
            entry(Material.IRON_BOOTS, ItemGroup.BOOTS),
            entry(Material.DIAMOND_BOOTS, ItemGroup.BOOTS),
            entry(Material.NETHERITE_BOOTS, ItemGroup.BOOTS),
            //Sherds
            entry(Material.ANGLER_POTTERY_SHERD, ItemGroup.SHERD),
            entry(Material.ARCHER_POTTERY_SHERD, ItemGroup.SHERD),
            entry(Material.ARMS_UP_POTTERY_SHERD, ItemGroup.SHERD),
            entry(Material.BLADE_POTTERY_SHERD, ItemGroup.SHERD),
            entry(Material.BREWER_POTTERY_SHERD, ItemGroup.SHERD),
            entry(Material.BURN_POTTERY_SHERD, ItemGroup.SHERD),
            entry(Material.DANGER_POTTERY_SHERD, ItemGroup.SHERD),
            entry(Material.EXPLORER_POTTERY_SHERD, ItemGroup.SHERD),
            entry(Material.FLOW_POTTERY_SHERD, ItemGroup.SHERD),
            entry(Material.FRIEND_POTTERY_SHERD, ItemGroup.SHERD),
            entry(Material.GUSTER_POTTERY_SHERD, ItemGroup.SHERD),
            entry(Material.HEART_POTTERY_SHERD, ItemGroup.SHERD),
            entry(Material.HEARTBREAK_POTTERY_SHERD, ItemGroup.SHERD),
            entry(Material.HOWL_POTTERY_SHERD, ItemGroup.SHERD),
            entry(Material.MINER_POTTERY_SHERD, ItemGroup.SHERD),
            entry(Material.MOURNER_POTTERY_SHERD, ItemGroup.SHERD),
            entry(Material.PLENTY_POTTERY_SHERD, ItemGroup.SHERD),
            entry(Material.PRIZE_POTTERY_SHERD, ItemGroup.SHERD),
            entry(Material.SCRAPE_POTTERY_SHERD, ItemGroup.SHERD),
            entry(Material.SHEAF_POTTERY_SHERD, ItemGroup.SHERD),
            entry(Material.SHELTER_POTTERY_SHERD, ItemGroup.SHERD),
            entry(Material.SKULL_POTTERY_SHERD, ItemGroup.SHERD),
            entry(Material.SNORT_POTTERY_SHERD, ItemGroup.SHERD)
    );

    /**
     * Overloaded method to add support for items
     *
     * @see #is(Material, ItemGroup)
     */
    public static boolean is(ItemStack item, ItemGroup group) {
        return is(item.getType(), group);
    }

    /**
     * Checks if an item is a part of the given item group
     *
     * @param type  item type to check
     * @param group group to match with
     * @return true if the item is a part of the item group
     */
    public static boolean is(Material type, ItemGroup group) {
        return itemGroups.containsKey(type) && itemGroups.get(type) == group;
    }

    /**
     * Overloaded method to add support for items
     *
     * @see #getItemGroup(Material)
     */
    public static ItemGroup getItemGroup(ItemStack item) {
        return getItemGroup(item.getType());
    }

    /**
     * Gets the item group of an item. Returns null if no group could be found
     *
     * @param type item type to get the group of
     * @return item group the item is part of
     */
    public static ItemGroup getItemGroup(Material type) {
        return itemGroups.get(type);
    }
}
