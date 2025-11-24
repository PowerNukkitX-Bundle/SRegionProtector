package Sergey_Dertan.SRegionProtector.UI.Form.Type;

import Sergey_Dertan.SRegionProtector.Region.Region;
import cn.nukkit.Player;
import cn.nukkit.form.element.ElementLabel;
import cn.nukkit.form.element.custom.ElementInput;
import cn.nukkit.form.response.CustomResponse;
import cn.nukkit.form.response.Response;
import cn.nukkit.form.window.CustomForm;

final class SellRegionForm extends CustomForm implements UIForm {

    private final transient Region region;

    @SuppressWarnings({"WeakerAccess", "SameParameterValue"})
    SellRegionForm(Region region, String err) {
        super("Sell region " + region.name);
        this.region = region;

        if (err != null) this.addElement(new ElementLabel(err));
        this.addElement(new ElementInput("Price", "PRICE"));
    }

    @SuppressWarnings("unused")
    SellRegionForm(Region region) {
        this(region, "");
    }

    @Override
    public Region getRegion() {
        return this.region;
    }

    @Override
    public UIForm handle(Response response, Player player) {
        if (!player.hasPermission("sregionprotector.admin") && !this.region.isCreator(player.getName())) return null;
        long price;
        try {
            String priceStr = ((CustomResponse) response).getInputResponse(0);
            if (priceStr == null) priceStr = ((CustomResponse) response).getInputResponse(1);
            price = Long.parseLong(priceStr);
            if (price <= 0) throw new RuntimeException();
        } catch (RuntimeException e) {
            return UIForm.getInstance(SellRegionForm.class, this.region, "Wrong price!");
        }
        this.region.setSellFlagState(price, true);
        return UIForm.getInstance(SellRegionForm.class, this.region, "Success! Current price " + price);
    }
}
