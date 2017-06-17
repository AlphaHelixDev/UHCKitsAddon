package de.alphahelix.kitsaddon.inventories;

import de.alphahelix.alphalibary.inventorys.InventoryBuilder;
import de.alphahelix.alphalibary.kits.Kit;
import de.alphahelix.alphalibary.utils.Sounds;
import de.alphahelix.alphalibary.utils.Util;
import de.alphahelix.kitsaddon.KitsAddon;
import de.alphahelix.kitsaddon.instances.KitSBObject;
import de.alphahelix.uhcremastered.UHC;
import de.alphahelix.uhcremastered.enums.GState;
import de.alphahelix.uhcremastered.instances.PlayerStatistic;
import de.alphahelix.uhcremastered.utils.KitUtil;
import de.alphahelix.uhcremastered.utils.ScoreboardUtil;
import de.alphahelix.uhcremastered.utils.StatsUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import java.util.WeakHashMap;

public class KitInventory {

    private static WeakHashMap<String, Inventory> kits = new WeakHashMap<>();

    public KitInventory() {
        Bukkit.getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onClick(PlayerInteractEvent e) {
                if (!GState.is(GState.LOBBY)) return;
                if (e.getItem() == null) return;
                if(!Util.isSame(e.getItem(), KitsAddon.getKitOptions().getIcon().getItemStack())) return;

                openInv(e.getPlayer());
            }
        }, UHC.getInstance());
    }

    public static void openInv(Player p) {
        PlayerStatistic stats = StatsUtil.getStatistics(p);

        InventoryBuilder ib = new InventoryBuilder(p, KitsAddon.getKitOptions().getGuiName(), ((KitsAddon.getFileKits().size() / 9) + 1) * 9);

        for (int i = 0; i < KitsAddon.getFileKits().size(); i++) {
            Kit k = KitsAddon.getFileKits().get(i);
            ib.addItem(ib.new SimpleItem(k.getIcon(), i) {
                @Override
                public void onClick(InventoryClickEvent e) {
                    e.setCancelled(true);
                    if (e.getAction() == InventoryAction.PICKUP_ALL) {
                        if (KitUtil.hasKit(p))
                            if (KitUtil.getPlayedKit(p).equals(k))
                                return;

                        if (stats.hasKit(k)) {
                            KitUtil.setPlayedKit(p, k);

                            p.playSound(p.getLocation(), Sounds.NOTE_PLING.bukkitSound(), 1, 1);
                            p.closeInventory();

                            ScoreboardUtil.updateLobbyScoreboardObject(p, new KitSBObject(p));
                            p.sendMessage(UHC.getGameOptions().getChatPrefix() + KitsAddon.getKitOptions().getKitChosen(k));
                        } else if (stats.hasCoins(k.getPrice())) {
                            p.closeInventory();

                            KitPurchaseConfirmInventory.openInventory(p, k);
                        } else {
                            p.sendMessage(UHC.getGameOptions().getChatPrefix() + KitsAddon.getKitOptions().getNotEnoughCoins(k));
                        }
                    } else {
                        PreviewInventory.openInventory(p, k);
                    }
                }
            });
        }

        kits.put(p.getName(), ib.build());
        p.openInventory(kits.get(p.getName()));
    }
}
