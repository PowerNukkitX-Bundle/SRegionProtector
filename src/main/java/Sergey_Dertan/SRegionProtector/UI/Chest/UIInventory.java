package Sergey_Dertan.SRegionProtector.UI.Chest;

import Sergey_Dertan.SRegionProtector.Region.Region;
import cn.nukkit.inventory.ContainerInventory;
import cn.nukkit.inventory.Inventory;
import cn.nukkit.inventory.InventoryHolder;
import cn.nukkit.inventory.InventoryType;
import cn.nukkit.item.Item;
import cn.nukkit.level.Level;
import cn.nukkit.math.Vector3;
import cn.nukkit.network.protocol.types.itemstack.ContainerSlotType;

import java.util.Map;

public final class UIInventory extends ContainerInventory {

    public final Region region;

    @Override
    public void init() {
        Map<Integer, ContainerSlotType> map = super.slotTypeMap();
        for (int i = 0; i < getSize(); i++) {
            map.put(i, ContainerSlotType.LEVEL_ENTITY);
        }
    }

    @Override
    public Map<Integer, ContainerSlotType> slotTypeMap() {
        Map<Integer, ContainerSlotType> map = super.slotTypeMap();
        for (int i = 0; i < this.getSize(); i++) {
            map.put(i, ContainerSlotType.INVENTORY);
        }
        return map;
    }

    UIInventory(Vector3 holder, Map<Integer, Item> content, Region region) {
        super(new Holder(holder.x, holder.y, holder.z), InventoryType.CONTAINER, 27);
        this.setContents(content);
        this.region = region;
    }

    static final class Holder extends Vector3 implements InventoryHolder {

        private Holder(double x, double y, double z) {
            super(x, y, z);
        }

        @Override
        public Inventory getInventory() {
            return null;
        }

        @Override
        public Level getLevel() {
            return null;
        }
    }
}
