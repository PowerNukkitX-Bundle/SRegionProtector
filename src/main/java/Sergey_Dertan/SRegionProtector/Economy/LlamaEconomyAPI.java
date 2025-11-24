package Sergey_Dertan.SRegionProtector.Economy;

import cn.nukkit.Player;
import net.lldv.llamaeconomy.LlamaEconomy;
import net.lldv.llamaeconomy.components.api.API;

public final class LlamaEconomyAPI implements AbstractEconomy {

    private final API llamaAPI = LlamaEconomy.getAPI();

    @Override
    public long getMoney(Player player) {
        return (long) this.llamaAPI.getMoney(player);
    }

    @Override
    public void addMoney(String player, long amount) {
        this.llamaAPI.addMoney(player, (double) amount);
    }

    @Override
    public void reduceMoney(String player, long amount) {
        this.llamaAPI.reduceMoney(player, (double) amount);
    }
}
