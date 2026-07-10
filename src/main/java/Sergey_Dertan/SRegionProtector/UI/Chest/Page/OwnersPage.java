package Sergey_Dertan.SRegionProtector.UI.Chest.Page;

import Sergey_Dertan.SRegionProtector.Main.SRegionProtectorMain;
import Sergey_Dertan.SRegionProtector.Messenger.Messenger;
import Sergey_Dertan.SRegionProtector.Region.Region;
import Sergey_Dertan.SRegionProtector.Region.RegionManager;
import Sergey_Dertan.SRegionProtector.Utils.Tags;
import org.powernukkitx.Player;
import org.powernukkitx.block.BlockID;
import org.powernukkitx.item.Item;
import org.powernukkitx.item.ItemID;
import org.powernukkitx.nbt.tag.CompoundTag;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public final class OwnersPage implements Page {

    private final RegionManager regionManager = SRegionProtectorMain.getInstance().getRegionManager();

    OwnersPage() {
    }

    @Override
    public Map<Integer, Item> getItems(Region region, int page) {
        Map<Integer, Item> list = new HashMap<>(NAVIGATORS_CACHE);
        int counter = 0;
        for (String owner : region.getOwners().stream().skip(page * 18).limit(18).collect(Collectors.toList())) {
            Item item = Item.get(ItemID.PLAYER_HEAD).
                    setCustomName(Messenger.getInstance().getMessage("gui.owners.owner-name", "@owner", owner)).
                    setLore(Messenger.getInstance().getMessage("gui.owners.owner-lore"));
            @SuppressWarnings("Duplicates")
            CompoundTag nbt = item.getNbt();
            nbt.putString(Tags.TARGET_NAME_TAG, owner);
            item.setNbt(nbt);
            list.put(counter, item);
            ++counter;
        }
        this.prepareItems(list.values());
        return list;
    }

    @Override
    public boolean handle(Item item, Region region, Player player) {
        if (!this.hasPermission(player, region)) return false;
        String target = item.getNbt().getString(Tags.TARGET_NAME_TAG);
        if (target.isEmpty() || !region.isOwner(target)) return false;
        this.regionManager.removeOwner(region, target);
        return true;
    }

    @Override
    public String getName() {
        return "owners";
    }

    @Override
    public boolean hasPermission(Player player, Region region) {
        return player.hasPermission("sregionprotector.admin") || region.isCreator(player.getName());
    }

    @Override
    public Item getIcon() {
        Item item = Item.get(ItemID.PLAYER_HEAD).setCustomName(Messenger.getInstance().getMessage("gui.main.go-to-owners"));
        CompoundTag nbt = item.getNbt();
        nbt.putString(Tags.OPEN_PAGE_TAG, this.getName());
        item.setNbt(nbt);
        return item;
    }
}
