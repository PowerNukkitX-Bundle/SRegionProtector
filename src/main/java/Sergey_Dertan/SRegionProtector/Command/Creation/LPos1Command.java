package Sergey_Dertan.SRegionProtector.Command.Creation;

import Sergey_Dertan.SRegionProtector.Command.SRegionProtectorCommand;
import Sergey_Dertan.SRegionProtector.Region.Selector.RegionSelector;
import org.powernukkitx.Player;
import org.powernukkitx.command.CommandSender;
import org.powernukkitx.level.Position;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;

public final class LPos1Command extends SRegionProtectorCommand {

    private final RegionSelector selector;
    private final int maxRadius;

    public LPos1Command(RegionSelector selector, int maxRadius) {
        super("lpos1");
        this.selector = selector;

        this.maxRadius = maxRadius;

        this.setCommandParameters(new Object2ObjectArrayMap<>());
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] strings) {
        if (!this.testPermissionSilent(sender)) {
            this.messenger.sendMessage(sender, "command.lpos1.permission");
            return false;
        }
        if (!(sender instanceof Player)) {
            this.messenger.sendMessage(sender, "command.lpos1.in-game");
            return false;
        }
        Position pos = ((Player) sender).getTargetBlock(this.maxRadius);
        if (pos != null) {
            this.selector.getSession((Player) sender).pos1 = Position.fromObject(pos, pos.level);
            this.messenger.sendMessage(sender, "command.lpos1.success");
            return true;
        } else {
            this.messenger.sendMessage(sender, "command.lpos1.fail", "@radius", Integer.toString(this.maxRadius));
            return false;
        }
    }
}
