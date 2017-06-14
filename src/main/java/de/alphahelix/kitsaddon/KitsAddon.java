package de.alphahelix.kitsaddon;

import de.alphahelix.alphalibary.kits.Kit;
import de.alphahelix.alphalibary.utils.Util;
import de.alphahelix.kitsaddon.files.KitFile;
import de.alphahelix.kitsaddon.instances.KitOptions;
import de.alphahelix.kitsaddon.inventories.KitInventory;
import de.alphahelix.uhcremastered.UHC;
import de.alphahelix.uhcremastered.addons.core.Addon;
import de.alphahelix.uhcremastered.enums.GState;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;

public class KitsAddon extends Addon {

    private static KitsAddon instance;
    private static KitOptions kitOptions;
    private static ArrayList<Kit> fileKits = new ArrayList<>();

    public static KitsAddon getInstance() {
        return instance;
    }

    public static ArrayList<Kit> getFileKits() {
        return fileKits;
    }

    public static KitOptions getKitOptions() {
        return kitOptions;
    }

    public static void setKitOptions(KitOptions kitOptions) {
        KitsAddon.kitOptions = kitOptions;
    }

    @Override
    public void onEnable() {
        instance = this;

        Bukkit.getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onJoin(PlayerJoinEvent e) {
                if (GState.is(GState.LOBBY))
                    Util.runLater(2, false, () ->
                            e.getPlayer().getInventory().setItem(getKitOptions().getIcon().getSlot(), getKitOptions().getIcon().getItemStack())
                    );
            }
        }, UHC.getInstance());

        new KitFile();
        new KitInventory();
    }
}
