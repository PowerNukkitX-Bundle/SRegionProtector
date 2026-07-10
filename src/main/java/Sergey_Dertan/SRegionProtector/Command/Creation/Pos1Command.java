package Sergey_Dertan.SRegionProtector.Command.Creation;

import Sergey_Dertan.SRegionProtector.Command.SRegionProtectorCommand;
import Sergey_Dertan.SRegionProtector.Region.Selector.RegionSelector;
import org.powernukkitx.Player;
import org.powernukkitx.command.CommandSender;
import org.cloudburstmc.protocol.bedrock.data.command.CommandParamType;
import org.powernukkitx.command.data.CommandParameter;
import org.powernukkitx.level.Position;
import org.powernukkitx.math.Vector3;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;

import java.util.Map;

public final class Pos1Command extends SRegionProtectorCommand {

    private final RegionSelector selector;

    public Pos1Command(RegionSelector selector) {
        super("pos1");
        this.selector = selector;

        Map<String, CommandParameter[]> parameters = new Object2ObjectArrayMap<>();
        parameters.put("target_pos1", new CommandParameter[]{
                CommandParameter.newType("target", true, CommandParamType.POSITION)
        });
        this.setCommandParameters(parameters);
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if (!this.testPermissionSilent(sender)) {
            this.messenger.sendMessage(sender, "command.pos1.permission");
            return false;
        }
        if (!(sender instanceof Player)) {
            this.messenger.sendMessage(sender, "command.pos1.in-game");
            return false;
        }
        if (args.length >= 3) {
            try {
                double x = Double.parseDouble(args[0]);
                double y = Double.parseDouble(args[1]);
                double z = Double.parseDouble(args[2]);
                this.selector.getSession((Player) sender).pos1 = Position.fromObject(new Vector3(x, y, z), ((Player) sender).level);
            } catch (NumberFormatException e) {
                this.messenger.sendMessage(sender, "command.pos1.wrong-coordinates");
                return false;
            }
        } else {
            this.selector.getSession((Player) sender).pos1 = ((Player) sender).getPosition();
        }
        this.messenger.sendMessage(sender, "command.pos1.pos-set");
        return false;
    }
}
