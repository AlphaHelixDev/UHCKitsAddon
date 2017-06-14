package de.alphahelix.kitsaddon.inventories;

import de.alphahelix.alphalibary.inventorys.InventoryBuilder;
import de.alphahelix.alphalibary.kits.Kit;
import de.alphahelix.alphalibary.utils.Sounds;
import de.alphahelix.kitsaddon.KitsAddon;
import de.alphahelix.uhcremastered.UHC;
import de.alphahelix.uhcremastered.instances.PlayerStatistic;
import de.alphahelix.uhcremastered.utils.KitUtil;
import de.alphahelix.uhcremastered.utils.ScoreboardUtil;
import de.alphahelix.uhcremastered.utils.StatsUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;


public class KitPurchaseConfirmInventory {

    public static void openInventory(Player p, Kit toBuy) {
        InventoryBuilder ib = new InventoryBuilder(KitsAddon.getKitOptions().getPurchasingGUIName(toBuy), 3 * 9) {
            @Override
            public void onOpen(InventoryOpenEvent inventoryOpenEvent) {
            }

            @Override
            public void onClose(InventoryCloseEvent inventoryCloseEvent) {
            }
        };

        for (int i = 0; i < 27; i++) {
            if (i == 11 || i == 15) continue;
            ib.addItem(new InventoryBuilder.SimpleItem(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7), i) {
                @Override
                public void onClick(InventoryClickEvent e) {
                    if (e.getClickedInventory().getTitle().equals(KitsAddon.getKitOptions().getPurchasingGUIName(toBuy)))
                        e.setCancelled(true);
                }
            });
        }

        ib.addItem(new InventoryBuilder.SimpleItem(KitsAddon.getKitOptions().getConfirmItem(), 11) {
            @Override
            public void onClick(InventoryClickEvent e) {
                if (e.getClickedInventory().getTitle().equals(KitsAddon.getKitOptions().getPurchasingGUIName(toBuy))) {
                    PlayerStatistic stats = StatsUtil.getStatistics(p);

                    e.setCancelled(true);

                    stats.removeCoins(toBuy.getPrice());
                    stats.addKit(toBuy);

                    KitUtil.setPlayedKit(p, toBuy);

                    p.playSound(p.getLocation(), Sounds.NOTE_PLING.bukkitSound(), 1, 1);
                    p.closeInventory();

                    ScoreboardUtil.updateLobbyKit(p);
                    ScoreboardUtil.updateLobbyCoins(p);
                    p.sendMessage(UHC.getGameOptions().getChatPrefix() + KitsAddon.getKitOptions().getKitChosen(toBuy));
                }
            }
        });

        ib.addItem(new InventoryBuilder.SimpleItem(KitsAddon.getKitOptions().getDeclineItem(), 15) {
            @Override
            public void onClick(InventoryClickEvent e) {
                if (e.getClickedInventory().getTitle().equals(KitsAddon.getKitOptions().getPurchasingGUIName(toBuy))) {
                    p.closeInventory();
                    KitInventory.openInv(p);
                }
            }
        });

        p.openInventory(ib.build());
    }
}
