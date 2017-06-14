package de.alphahelix.kitsaddon.instances;

import de.alphahelix.alphalibary.file.SimpleFile;
import de.alphahelix.alphalibary.kits.Kit;
import org.bukkit.inventory.ItemStack;

public class KitOptions {

    private String kitChosen, guiName, notEnoughCoins, purchasingGUIName, previewGUIName;
    private ItemStack confirmItem, declineItem, nextPage, previousPage;
    private SimpleFile.InventoryItem icon;

    public KitOptions(String kitChosen, String guiName, String notEnoughCoins, String purchasingGUIName, String previewGUIName, ItemStack confirmItem, ItemStack declineItem, ItemStack nextPage, ItemStack previousPage, SimpleFile.InventoryItem icon) {
        this.kitChosen = kitChosen;
        this.guiName = guiName;
        this.notEnoughCoins = notEnoughCoins;
        this.purchasingGUIName = purchasingGUIName;
        this.previewGUIName = previewGUIName;
        this.confirmItem = confirmItem;
        this.declineItem = declineItem;
        this.nextPage = nextPage;
        this.previousPage = previousPage;
        this.icon = icon;
    }

    public String getGuiName() {
        return guiName;
    }

    public String getKitChosen(Kit kit) {
        return kitChosen.replace("[kit]", kit.getName());
    }

    public String getNotEnoughCoins(Kit kit) {
        return notEnoughCoins.replace("[kit]", kit.getName());
    }

    public String getPurchasingGUIName(Kit kit) {
        return purchasingGUIName.replace("[kit]", kit.getName()).replace("[price]", Long.toString(kit.getPrice()));
    }

    public SimpleFile.InventoryItem getIcon() {
        return icon;
    }

    public ItemStack getConfirmItem() {
        return confirmItem;
    }

    public ItemStack getDeclineItem() {
        return declineItem;
    }

    public String getPreviewGUIName(Kit kit) {
        return previewGUIName.replace("[kit]", kit.getName());
    }

    public ItemStack getNextPage() {
        return nextPage;
    }

    public ItemStack getPreviousPage() {
        return previousPage;
    }
}
