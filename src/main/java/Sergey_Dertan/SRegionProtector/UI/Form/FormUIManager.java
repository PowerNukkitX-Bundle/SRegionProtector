package Sergey_Dertan.SRegionProtector.UI.Form;

import Sergey_Dertan.SRegionProtector.Main.SRegionProtectorMain;
import Sergey_Dertan.SRegionProtector.Region.Region;
import Sergey_Dertan.SRegionProtector.Region.RegionManager;
import Sergey_Dertan.SRegionProtector.UI.Form.Element.Button;
import Sergey_Dertan.SRegionProtector.UI.Form.Type.UIForm;
import cn.nukkit.Player;
import cn.nukkit.form.element.simple.ElementButton;
import cn.nukkit.form.response.Response;
import cn.nukkit.form.response.SimpleResponse;
import cn.nukkit.form.window.Form;


public abstract class FormUIManager {

    private static final RegionManager REGION_MANAGER = SRegionProtectorMain.getInstance().getRegionManager();

    public static void open(Player target, Region region) {
        target.sendForm((Form<?>) UIForm.getInstance(UIForm.MAIN, region, target));
    }

    public static void handle(Player player, UIForm form) {
        if (!REGION_MANAGER.regionExists(form.getRegion().name)) return;
        Response response = ((Form) form).response();
        if (response instanceof SimpleResponse) {
            ElementButton btn = ((SimpleResponse) response).button();
            if (btn instanceof Button) {
                UIForm next = ((Button) btn).getNext();
                if (next != null) player.sendForm((Form<?>) next);
                return;
            }
        }

        UIForm next = form.handle(((Form) form).response(), player);
        if (next != null) player.sendForm((Form<?>) next);
    }
}
