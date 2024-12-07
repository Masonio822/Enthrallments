package org.masonio.enthrallment.enthrallments;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.masonio.enthrallment.ItemGroup;
import org.masonio.enthrallment.Keys;
import org.mineacademy.fo.enchant.SimpleEnchantment;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.remain.CompMaterial;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Main class to create enthrallments. Other enthrallments should extend this
 * class to be registered as enthrallments
 */
public abstract class Enthrallment extends SimpleEnchantment implements Serializable {

    /**
     * The pottery sherd to represent the enthrallment. Should only be pottery sherds
     *
     * @see Material#ANGLER_POTTERY_SHERD
     * @see Material#ARCHER_POTTERY_SHERD
     * @see Material#ARMS_UP_POTTERY_SHERD
     * @see Material#BLADE_POTTERY_SHERD
     * @see Material#BREWER_POTTERY_SHERD
     * @see Material#BURN_POTTERY_SHERD
     * @see Material#DANGER_POTTERY_SHERD
     * @see Material#EXPLORER_POTTERY_SHERD
     * @see Material#FLOW_POTTERY_SHERD
     * @see Material#FRIEND_POTTERY_SHERD
     * @see Material#GUSTER_POTTERY_SHERD
     * @see Material#HEART_POTTERY_SHERD
     * @see Material#HEARTBREAK_POTTERY_SHERD
     * @see Material#HOWL_POTTERY_SHERD
     * @see Material#MINER_POTTERY_SHERD
     * @see Material#MOURNER_POTTERY_SHERD
     * @see Material#PLENTY_POTTERY_SHERD
     * @see Material#PRIZE_POTTERY_SHERD
     * @see Material#SCRAPE_POTTERY_SHERD
     * @see Material#SHEAF_POTTERY_SHERD
     * @see Material#SHELTER_POTTERY_SHERD
     * @see Material#SKULL_POTTERY_SHERD
     * @see Material#SNORT_POTTERY_SHERD
     */
    private final Material sherd;

    /**
     * The enthrallment's description. Supports minecraft color codes (ex. &7).
     */
    private final List<String> description;

    /**
     * Set of item groups that the enthrallment can be applied to
     *
     * @see ItemGroup
     */
    private final Set<ItemGroup> applicableItems;

    /**
     * Set of enchantments that cannot coexist on an item with this enthrallment.
     * The enchantments will be removed if an enthrallment is added to the item with these enchantments present.
     */
    private final Set<Enchantment> conflictingEnchantments;

    /**
     * Default constructor for enthrallments. Should not be overridden because other plugins may depend on it.
     * Create a new constructor if custom behavior is needed but make sure to fill all provided fields of this class
     *
     * @param name                    name of the enthrallment
     * @param sherd                   pottery sherd to represent the enthrallment
     * @param description             description of the enthrallment
     * @param applicable              set of item groups the enthrallment can be applied to
     * @param conflictingEnchantments set of enchantments that cannot coexist with this enthrallment
     * @throws RuntimeException if sherd is not a type of pottery sherd
     */
    protected Enthrallment(String name, Material sherd,
                           List<String> description, Set<ItemGroup> applicable,
                           Set<Enchantment> conflictingEnchantments) {
        super(name, 1);
        if (!(ItemGroup.getItemGroup(sherd) == ItemGroup.SHERD)) {
            throw new RuntimeException("Item must be a sherd");
        }
        this.sherd = sherd;
        this.description = description;
        this.applicableItems = applicable;
        this.conflictingEnchantments = conflictingEnchantments;
    }

    /**
     * Returns a set of enchantments that are needed to apply this Enthrallment to an item.
     *
     * @return set of enchantments
     */
    public Set<Enchantment> getRequiredEnchantments() {
        return Set.of();
    }

    /**
     * Checks if an item can have an enthrallment added.
     *
     * @param item item that the enthrallment is attempting to be applied to
     * @return true if this enthrallment can be added to the item
     */
    public boolean canBeApplied(ItemStack item) {
        boolean noEnthrallment = findEnchantments(item).entrySet()
                .stream()
                .noneMatch(enchant -> enchant instanceof Enthrallment);

        Set<Enchantment> requiredEnchantments = getRequiredEnchantments();
        requiredEnchantments.removeIf(enchantment -> item.getEnchantments().containsKey(enchantment));
        boolean hasEnchantments = requiredEnchantments.isEmpty();

        return noEnthrallment && hasEnchantments && this.canEnchantItem(item);
    }

    /**
     * Handles applying the enthrallment to the item. For internal use only.
     *
     * @param item item getting the enthrallment
     * @return the item with the enthrallment if it could be applied
     * @throws NullPointerException if the enthrallment cannot be applied to the item
     */
    public final ItemStack apply(ItemStack item) {
        if (!canBeApplied(item)) {
            throw new RuntimeException("enthrallment cannot be applied to item");
        }

        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(Keys.ENTHRALLMENT, new EnthrallmentDataType(), this);
        this.conflictingEnchantments.stream()
                .filter(enchantment -> item.getItemMeta().hasEnchant(enchantment))
                .forEach(enchantment -> meta.removeEnchant(enchantment));

        item.setItemMeta(meta);
        this.applyTo(item, 1);
        return this.onApply(item);
    }

    /**
     * Gets the enthrallment from an item stack.
     *
     * @param item item to get the enthrallment from
     * @return the enthrallment the item has. Returns null if the item has no enthrallment
     */
    public static Enthrallment of(ItemStack item) {
        return item.getItemMeta().getPersistentDataContainer().get(Keys.ENTHRALLMENT, new EnthrallmentDataType());
    }

    /**
     * Overridable method to implement any custom logic when applying an enthrallment to an item.
     * This method is called directly before the item is returned in the apply method.
     *
     * @param item the item getting the enthrallment
     * @return the item after custom logic has been handled
     * @see #apply(ItemStack)
     */
    public ItemStack onApply(ItemStack item) {
        return item;
    }

    /**
     * Returns the color formatted name of the Enthrallment. Supports symbol color codes (ex. &7)
     *
     * @return the formatted name of the enthrallment
     */
    public abstract String getFormattedName();

    /**
     * Creates the enchanted sherd item for the given enthrallment. Uses the sherd given in the constructor and
     * initializes the name and lore according to the standard. This is the item that should be input into the
     * enthrallment table to be added to an item. Like the enchanted book but for enthrallments
     *
     * @param enthrallment the enthrallment to apply to the sherd
     * @return pottery sherd with the enthrallment
     */
    public static ItemStack getItem(Enthrallment enthrallment) {
        Set<String> formattedRequiredEnchants = Set.of("&7None");
        if (!enthrallment.getRequiredEnchantments().isEmpty()) {
            formattedRequiredEnchants = enthrallment.getRequiredEnchantments()
                    .stream()
                    .map(enchantment -> {
                        String name = enchantment.getKey().getKey();
                        return ChatColor.YELLOW + (name.substring(0, 1).toUpperCase() + name.substring(1));
                    })
                    .collect(Collectors.toSet());
        }
        List<String> desc = enthrallment.description
                .stream()
                .map(string -> "&e• " + string)
                .toList();

        String enchantmentNumber = String.valueOf(enthrallment.getRequiredEnchantments().size());

        List<String> lore = Stream.concat(
                Stream.of(
                        enthrallment.getFormattedName(),
                        "",
                        "&eRequired Enchantments (" + (ChatColor.YELLOW + enchantmentNumber) + "&e): " +
                                (ChatColor.GRAY + formattedRequiredEnchants.toString()),
                        "",
                        "&eEffects: "
                ),
                desc.stream()
        ).toList();

        ItemStack item = ItemCreator.of(CompMaterial.fromMaterial(enthrallment.sherd), ChatColor.YELLOW + "Enthrallment", lore)
                .enchant(enthrallment)
                .glow(true)
                .make();
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(Keys.ENTHRALLMENT, new EnthrallmentDataType(), enthrallment);

        item.setItemMeta(meta);
        return item;
    }

    /**
     * Returns if the item can support the enthrallment <u>based on the item alone</u>. For internal use only.
     *
     * @param item item the enthrallment is attempting to be applied to
     * @return true if the item can receive the enthrallment
     */
    @Override
    public final boolean canEnchantItem(ItemStack item) {
        return this.applicableItems.contains(ItemGroup.getItemGroup(item));
    }

    /**
     * Disables the enthrallment from being a part of the traditional methods of obtaining enchantments. For internal use only.
     *
     * @return true
     */
    @Override
    public final boolean isTreasure() {
        return true;
    }

    /**
     * Disables the enthrallment from being a part of the traditional methods of obtaining enchantments. For internal use only.
     *
     * @return false
     */
    @Override
    public final boolean isTradeable() {
        return false;
    }

    /**
     * Disables the enthrallment from being a part of the traditional methods of obtaining enchantments. For internal use only.
     *
     * @return false
     */
    @Override
    public final boolean isDiscoverable() {
        return false;
    }
}
