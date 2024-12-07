package org.masonio.enthrallment.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.masonio.enthrallment.ItemGroup;
import org.masonio.enthrallment.enthrallments.Enthrallment;
import org.mineacademy.fo.menu.Menu;
import org.mineacademy.fo.menu.button.Button;
import org.mineacademy.fo.menu.button.ButtonMenu;
import org.mineacademy.fo.menu.button.annotation.Position;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.menu.model.MenuClickLocation;
import org.mineacademy.fo.remain.CompMaterial;


/**
 * Menu screen for the enthrallment table gui
 * @see org.masonio.enthrallment.listener.EnthrallmentTableListener
 * @see org.mineacademy.fo.menu.Menu
 */
public class EnthrallmentGui extends Menu {
    @Position(26)
    private final Button confirmButton;

    @Position(4)
    private final Button openEnchantmentsMenuButton;

    public EnthrallmentGui() {
        setTitle("&4&l&nEnthrallments");
        setSize(9 * 3);

        //Fill background
        for (int i = 0; i < getSize(); i++) {
            setItem(i, ItemCreator.of(CompMaterial.GRAY_STAINED_GLASS_PANE,
                            "",
                            "")
                    .make());
        }

        setItem(11, ItemCreator.of(CompMaterial.LIGHT_GRAY_STAINED_GLASS_PANE,
                        "&7Item",
                        "&7Click an item in your",
                        "&7inventory to set the item")
                .make()
        );

        this.confirmButton = new Button() {
            @Override
            public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
                ItemStack item = getItemAt(11);
                Enthrallment enthrallment;
                //TODO: Implement give item with enthrallment
            }

            @Override
            public ItemStack getItem() {
                return ItemCreator.of(CompMaterial.GREEN_CONCRETE,
                                "&2&lConfirm",
                                "&6&lWARNING: &cThis is a permanent change!")
                        .make();
            }
        };

        this.openEnchantmentsMenuButton = new ButtonMenu(new EnchantmentGui(), ItemCreator.of(
                CompMaterial.NETHERITE_UPGRADE_SMITHING_TEMPLATE,
                "&4&l&nEnthrallments",
                "&3Click to switch to enchantments"));
    }

    @Override
    protected boolean isActionAllowed(MenuClickLocation location, int slot, @Nullable ItemStack clicked, @Nullable ItemStack cursor) {
        if (location == MenuClickLocation.PLAYER_INVENTORY) {
            if (ItemGroup.getItemGroup(clicked) != null && !ItemGroup.is(clicked, ItemGroup.SHERD)) {
                this.setItem(11, clicked);
                getViewer().getInventory().remove(clicked);
            } else if (ItemGroup.is(clicked, ItemGroup.SHERD)) {
                this.setItem(14, clicked);
                getViewer().getInventory().remove(clicked);
            }
        }
        return super.isActionAllowed(location, slot, clicked, cursor);
    }
}
