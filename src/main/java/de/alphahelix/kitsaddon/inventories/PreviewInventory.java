package de.alphahelix.kitsaddon.inventories;

import de.alphahelix.alphalibary.inventorys.SimpleMovingInventory;
import de.alphahelix.alphalibary.kits.Kit;
import de.alphahelix.kitsaddon.KitsAddon;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class PreviewInventory {
    public static void openInventory(Player p, Kit toSee) {
        ArrayList<ItemStack> stacks = new ArrayList<>();
        for (ItemStack is : toSee.getItems()) {
            if (is != null)
                stacks.add(is);
        }

        new SimpleMovingInventory(
                p,
                9 * 5,
                stacks,
                KitsAddon.getKitOptions().getPreviewGUIName(toSee),
                KitsAddon.getKitOptions().getNextPage(),
                KitsAddon.getKitOptions().getPreviousPage());
    }
}
