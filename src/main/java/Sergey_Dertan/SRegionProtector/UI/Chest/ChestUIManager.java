package Sergey_Dertan.SRegionProtector.UI.Chest;

import Sergey_Dertan.SRegionProtector.Messenger.Messenger;
import Sergey_Dertan.SRegionProtector.Region.Region;
import Sergey_Dertan.SRegionProtector.UI.Chest.Page.Page;
import Sergey_Dertan.SRegionProtector.UI.Chest.Page.RemoveRegionPage;
import Sergey_Dertan.SRegionProtector.Utils.Tags;
import Sergey_Dertan.SRegionProtector.Utils.Utils;
import cn.nukkit.Player;
import cn.nukkit.block.BlockChest;
import cn.nukkit.block.BlockID;
import cn.nukkit.inventory.Inventory;
import cn.nukkit.item.Item;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.utils.RuntimeBlockDefinition;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import org.cloudburstmc.math.vector.Vector3i;
import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.protocol.bedrock.packet.BlockActorDataPacket;
import org.cloudburstmc.protocol.bedrock.packet.UpdateBlockPacket;

public abstract class ChestUIManager {

    private static final Int2ObjectMap<UIInventory> inventories = new Int2ObjectArrayMap<>(); //loader id -> gui inventory
    /**
     * async packets should be put directly to the interface
     *
     * @see cn.nukkit.event.server.DataPacketSendEvent
     */
    private static boolean async;

    private ChestUIManager() {
    }

    public static void handle(Player player, Item item) {
        Inventory inventory = inventories.get(player.getLoaderId());
        if (inventory == null) return;
        Region region = inventories.get(player.getLoaderId()).region;
        if (!region.isLivesIn(player.getName()) && !player.hasPermission("sregionprotector.info.other") && !player.hasPermission("sregionprotector.admin")) {
            removeChest(player, (Vector3) inventories.remove(player.getLoaderId()).getHolder());
            return;
        }
        CompoundTag nbt = item.getNbt();
        Page page;
        //navigators
        page = Page.getPage(nbt.getString(Tags.CURRENT_PAGE_NAME_TAG));
        if (page != null) {
            if (nbt.contains(Tags.REFRESH_PAGE_TAG)) {
                inventory.setContents(page.getItems(region, nbt.getInt(Tags.CURRENT_PAGE_NUMBER_TAG)));
                return;
            }
            if (nbt.contains(Tags.NEXT_PAGE_TAG)) {
                int pageNumber = nbt.getInt(Tags.CURRENT_PAGE_NUMBER_TAG) + 1;
                inventory.setContents(page.getItems(region, pageNumber));
                return;
            }
            if (nbt.contains(Tags.PREVIOUS_PAGE_TAG)) {
                int pageNumber = nbt.getInt(Tags.CURRENT_PAGE_NUMBER_TAG) - 1;
                pageNumber = pageNumber < 0 ? 0 : pageNumber;
                inventory.setContents(page.getItems(region, pageNumber));
                return;
            }
        }
        //page link
        page = Page.getPage(nbt.getString(Tags.OPEN_PAGE_TAG));
        if (page != null) {
            inventory.setContents(page.getItems(region));
            return;
        }
        //page handler
        page = Page.getPage(nbt.getString(Tags.CURRENT_PAGE_NAME_TAG));
        if (page != null) {
            if (page.handle(item, region, player)) {
                if (page instanceof RemoveRegionPage) {
                    removeChest(player, ((Vector3) inventory.getHolder()));
                    Messenger.getInstance().sendMessage(player, "command.remove.region-removed", "@region", region.name);
                    return;
                }
                inventory.setContents(page.getItems(region, nbt.getInt(Tags.CURRENT_PAGE_NUMBER_TAG)));
            }
        }
    }

    public static void setAsync(boolean async) {
        ChestUIManager.async = async;
    }

    public static void open(Player player, Region region) {
        Vector3 pos = sendFakeChest(player, region.name);
        if (pos == null) return;
        UIInventory inventory = new UIInventory(pos, Page.MAIN.getItems(region), region);
        if (player.addWindow(inventory) == -1) {
            removeChest(player, pos);
        } else {
            inventories.put(player.getLoaderId(), inventory);
        }
    }

    public static void removeChest(Player target) {
        Inventory inventory = inventories.remove(target.getLoaderId());
        if (inventory != null) {
            removeChest(target, ((Vector3) inventory.getHolder()));
        }
    }

    private static Vector3 sendFakeChest(Player target, String region) {
        UpdateBlockPacket pk1 = new UpdateBlockPacket();
        Vector3i chestPosition = Vector3i.from((int) target.x, (int) target.y - 1, (int) target.z);
        pk1.setBlockPosition(chestPosition);
        pk1.setLayer(0);
        pk1.setDefinition(new RuntimeBlockDefinition(BlockChest.PROPERTIES.getDefaultState().blockStateHash()));
        if (async && !Utils.directDataPacket(target, pk1)) {
            return null;
        } else {
            target.sendPacket(pk1);
        }

        BlockActorDataPacket pk2 = new BlockActorDataPacket();
        pk2.setBlockPosition(chestPosition);
        pk2.setActorDataTags(NbtMap.builder()
                .putString(Tags.CUSTOM_NAME_TAG, region)
                .putInt(Tags.X_TAG, chestPosition.getX())
                .putInt(Tags.Y_TAG, chestPosition.getY())
                .putInt(Tags.Z_TAG, chestPosition.getZ())
                .build());
        if (async && !Utils.directDataPacket(target, pk2)) {
            return null;
        } else {
            target.sendPacket(pk2);
        }
        return new Vector3(target.x, target.y - 1, target.z);
    }

    private static void removeChest(Player target, Vector3 pos) {
        target.level.sendBlocks(new Player[]{target}, new Vector3[]{pos});
    }
}
