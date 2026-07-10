package Sergey_Dertan.SRegionProtector.UI.Form.Type;

import Sergey_Dertan.SRegionProtector.Region.Region;
import org.powernukkitx.Player;
import org.powernukkitx.form.element.ElementLabel;
import org.powernukkitx.form.element.custom.ElementInput;
import org.powernukkitx.form.response.CustomResponse;
import org.powernukkitx.form.response.Response;
import org.powernukkitx.form.window.CustomForm;

final class SetPriorityForm extends CustomForm implements UIForm {

    private final transient Region region;

    @SuppressWarnings({"WeakerAccess", "SameParameterValue"})
    SetPriorityForm(Region region, String err) {
        super("Changing priority for " + region.name);
        this.region = region;

        this.addElement(new ElementLabel("Current priority: " + region.getPriority()));
        if (err != null) this.addElement(new ElementLabel(err));
        this.addElement(new ElementInput("Priority", "PRIORITY"));
    }

    @SuppressWarnings("unused")
    SetPriorityForm(Region region) {
        this(region, "");
    }

    @Override
    public UIForm handle(Response response, Player player) {
        if (!player.hasPermission("sregionprotector.admin") && !this.region.isCreator(player.getName())) return null;
        int priority;
        try {
            String priorityStr = null;
            for (int i = 0; i < 3; ++i) {
                priorityStr = ((CustomResponse) response).getInputResponse(i);
                if (priorityStr != null) break;
            }
            if (priorityStr == null) throw new RuntimeException();
            priority = Integer.parseInt(priorityStr);
        } catch (RuntimeException e) {
            return UIForm.getInstance(SetPriorityForm.class, this.region, "Wrong priority!");
        }
        this.region.setPriority(priority);
        return UIForm.getInstance(SetPriorityForm.class, this.region);
    }

    @Override
    public Region getRegion() {
        return this.region;
    }
}
