package de.alphahelix.kitsaddon.files;

import de.alphahelix.alphalibary.file.SimpleFile;
import de.alphahelix.alphalibary.file.SimpleJSONFile;
import de.alphahelix.alphalibary.item.ItemBuilder;
import de.alphahelix.alphalibary.kits.Kit;
import de.alphahelix.kitsaddon.KitsAddon;
import de.alphahelix.kitsaddon.instances.KitOptions;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class KitFile extends SimpleJSONFile {
    public KitFile() {
        super(KitsAddon.getInstance().getDataFolder().getAbsolutePath(), "kits.json");
        init();

        KitsAddon.setKitOptions(getValue("Options", KitOptions.class));

        for (Kit k : getValue("Kits", Kit[].class))
            KitsAddon.getFileKits().add(k);
    }

    private void init() {
        if (jsonContains("Options")) return;

        setValue("Options", new KitOptions(
                "§7You chose the [kit] kit",
                "§6Kits",
                "§7You just purchased the [kit] kit.",
                "§7Purchasing [kit] kit for [price] coins?",
                "§7Kit: §6[kit]",
                new ItemBuilder(Material.STAINED_CLAY).setDamage((short) 4).setName("§aAccept").build(),
                new ItemBuilder(Material.STAINED_CLAY).setDamage((short) 14).setName("§cDecline").build(),
                new ItemBuilder(Material.STAINED_GLASS_PANE).setDamage((short) 4).setName("§aNext page").build(),
                new ItemBuilder(Material.STAINED_GLASS_PANE).setDamage((short) 14).setName("§cPrevious page").build(),
                new SimpleFile.InventoryItem(new ItemBuilder(Material.IRON_SWORD).setName("§6Kits").build(), 0)
        ));

        setValue("Kits", new Kit[]{
                new Kit(
                        "Example",
                        0,
                        new ItemStack(Material.APPLE),
                        new ItemStack(Material.IRON_CHESTPLATE), new ItemStack(Material.COMPASS)
                )
        });
    }
}
