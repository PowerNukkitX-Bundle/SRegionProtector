package Sergey_Dertan.SRegionProtector.Command.Creation;

import Sergey_Dertan.SRegionProtector.Command.SRegionProtectorCommand;
import org.powernukkitx.Player;
import org.powernukkitx.command.CommandSender;
import org.powernukkitx.item.Item;
import org.powernukkitx.item.ItemID;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;

public final class GetWandCommand extends SRegionProtectorCommand {

    public GetWandCommand() {
        super("wand");

        this.setCommandParameters(new Object2ObjectArrayMap<>());
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] strings) {
        if (!this.testPermissionSilent(sender)) {
            this.messenger.sendMessage(sender, "command.wand.permission");
            return false;
        }
        if (!(sender instanceof Player)) {
            this.messenger.sendMessage(sender, "command.wand.in-game");
            return false;
        }
        ((Player) sender).getInventory().addItem(Item.get(ItemID.WOODEN_AXE, 0, 1));
        this.messenger.sendMessage(sender, "command.wand.wand-given");
        return true;
    }
}
