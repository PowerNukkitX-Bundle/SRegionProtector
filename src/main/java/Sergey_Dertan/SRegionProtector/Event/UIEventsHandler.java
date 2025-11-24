package Sergey_Dertan.SRegionProtector.Event;

import Sergey_Dertan.SRegionProtector.UI.Chest.ChestUIManager;
import Sergey_Dertan.SRegionProtector.UI.Chest.UIInventory;
import Sergey_Dertan.SRegionProtector.UI.Form.FormUIManager;
import Sergey_Dertan.SRegionProtector.UI.Form.Type.UIForm;
import Sergey_Dertan.SRegionProtector.UI.UIType;
import Sergey_Dertan.SRegionProtector.Utils.Tags;
import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.inventory.InventoryCloseEvent;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.event.player.PlayerTransferItemEvent;
import cn.nukkit.inventory.Inventory;
import cn.nukkit.item.Item;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public final class UIEventsHandler implements Listener {

    private final UIType uiType;

    public UIEventsHandler(UIType uiType) {
        this.uiType = uiType;
    }

    //remove fake chest, chest UI
    @EventHandler
    public void inventoryClose(InventoryCloseEvent e) {
        ChestUIManager.removeChest(e.getPlayer());
    }

    //form UI
    @EventHandler
    public void playerFormResponded(PlayerFormRespondedEvent e) {
        if (this.uiType != UIType.FORM) return;
        if (!(e.getWindow() instanceof UIForm)) return;
        FormUIManager.handle(e.getPlayer(), (UIForm) e.getWindow());
    }

    //chest UI
    @EventHandler
    public void inventoryTransaction(PlayerTransferItemEvent e) {
        if (this.uiType != UIType.CHEST) return;
        List<Inventory> inventories = new ArrayList<>();
        inventories.add(e.getSourceInventory());
        if(e.getDestinationInventory().isPresent()) {
            inventories.add(e.getDestinationInventory().get());
        }
        if (inventories.stream().noneMatch(inventory -> inventory instanceof UIInventory)) {
            return;
        }
        e.setCancelled();
        Player player = e.getPlayer();
        Inventory source = e.getSourceInventory();
        Item sourceItem = e.getSourceItem();
        if (sourceItem.getNamedTagEntry(Tags.IS_UI_ITEM_TAG) != null) {
            ChestUIManager.handle(player, sourceItem);
            return;
        }
        if(e.getDestinationItem().isPresent()) {
            Item destItem = e.getDestinationItem().get();
            if (destItem.getNamedTagEntry(Tags.IS_UI_ITEM_TAG) != null) {
                ChestUIManager.handle(player, destItem);
                return;
            }
        }
    }
}
