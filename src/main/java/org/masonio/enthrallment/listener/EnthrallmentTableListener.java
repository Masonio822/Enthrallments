package org.masonio.enthrallment.listener;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.masonio.enthrallment.gui.EnthrallmentGui;

/**
 * This class solely listens to blocks being right-clicked to open the enthrallment gui
 * The target block can be changed in the settings file. The default is <i>{@link Material#FLETCHING_TABLE}</i>
 */
public class EnthrallmentTableListener implements Listener {
    @EventHandler
    public void onTableClick(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.FLETCHING_TABLE) {
            new EnthrallmentGui().displayTo(event.getPlayer());
        }
    }
}
