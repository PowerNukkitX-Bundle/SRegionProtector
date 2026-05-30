package Sergey_Dertan.SRegionProtector.UI.Chest;

import Sergey_Dertan.SRegionProtector.Region.Region;
import cn.nukkit.inventory.ContainerInventory;
import cn.nukkit.inventory.Inventory;
import cn.nukkit.inventory.InventoryHolder;
import cn.nukkit.item.Item;
import cn.nukkit.level.Level;
import cn.nukkit.math.Vector3;
import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerEnumName;
import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerType;

import java.util.Map;

public final class UIInventory extends ContainerInventory {

    public final Region region;

    @Override
    public void init() {
        Map<Integer, ContainerEnumName> map = super.slotTypeMap();
        for (int i = 0; i < getSize(); i++) {
            map.put(i, ContainerEnumName.LEVEL_ENTITY_CONTAINER);
        }
    }

    @Override
    public Map<Integer, ContainerEnumName> slotTypeMap() {
        Map<Integer, ContainerEnumName> map = super.slotTypeMap();
        for (int i = 0; i < this.getSize(); i++) {
            map.put(i, ContainerEnumName.INVENTORY_CONTAINER);
        }
        return map;
    }

    UIInventory(Vector3 holder, Map<Integer, Item> content, Region region) {
        super(new Holder(holder.x, holder.y, holder.z), ContainerType.CONTAINER, 27);
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
