package org.masonio.enthrallment;

import org.masonio.enthrallment.listener.EnthrallmentTableListener;
import org.mineacademy.fo.plugin.SimplePlugin;

public final class EnthrallmentsPlugin extends SimplePlugin {
    private static EnthrallmentsPlugin instance;

    @Override
    public void onPluginStart() {
        // Plugin startup logic
        instance = this;
        getServer().getPluginManager().registerEvents(new EnthrallmentTableListener(), this);
    }

    @Override
    public void onPluginStop() {
        // Plugin shutdown logic
    }

    public static EnthrallmentsPlugin getInstance() {
        return instance;
    }
}
