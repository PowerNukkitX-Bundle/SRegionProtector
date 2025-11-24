package Sergey_Dertan.SRegionProtector.UI.Form.Type;

import Sergey_Dertan.SRegionProtector.Region.Region;
import Sergey_Dertan.SRegionProtector.UI.Form.Element.Button;
import cn.nukkit.Player;
import cn.nukkit.form.element.simple.ButtonImage;
import cn.nukkit.form.window.SimpleForm;

final class MainForm extends SimpleForm implements UIForm {

    private static final transient ButtonImage priorityImage = new ButtonImage(ButtonImage.Type.PATH, "textures/ui/move.png");
    private static final transient ButtonImage membersImage = new ButtonImage(ButtonImage.Type.PATH, "textures/ui/permissions_member_star.png");
    private static final transient ButtonImage removeImage = new ButtonImage(ButtonImage.Type.PATH, "textures/ui/trash.png");
    private static final transient ButtonImage ownersImage = new ButtonImage(ButtonImage.Type.PATH, "textures/ui/permissions_op_crown.png");
    private static final transient ButtonImage flagsImage = new ButtonImage(ButtonImage.Type.PATH, "textures/ui/common-classic_toggle_checked_state.png");
    private static final transient ButtonImage sellImage = new ButtonImage(ButtonImage.Type.PATH, "textures/ui/MCoin.png");

    private final transient Region region;

    MainForm(Region region, Player player) {
        super("Region '" + region.name + "'",
                "Level: " + region.level + "\n" +
                        "Creator: " + region.getCreator() + "\n" +
                        "Priority: " + region.getPriority() + "\n" +
                        "Size: " + region.size
        );
        this.region = region;

        this.addElement(new Button("Owners", OwnersForm.class, region, player).setImage(ownersImage));
        this.addElement(new Button("Members", MembersForm.class, region, player).setImage(membersImage));
        this.addElement(new Button("Flags", FlagsForm.class, region, player).setImage(flagsImage));
        if (player.hasPermission("sregionprotector.admin") || this.region.isCreator(player.getName())) {
            this.addElement(new Button("Sell region", SellRegionForm.class, region).setImage(sellImage));
            this.addElement(new Button("Set priority", SetPriorityForm.class, region).setImage(priorityImage));
            this.addElement(new Button("Remove region", RemoveRegionForm.class, region, player).setImage(removeImage));
        }
    }

    @Override
    public Region getRegion() {
        return this.region;
    }
}
