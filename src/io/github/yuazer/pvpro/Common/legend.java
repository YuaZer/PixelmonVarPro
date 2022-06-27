package io.github.yuazer.pvpro.Common;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.storage.StorageProxy;
import me.clip.placeholderapi.PlaceholderHook;
import org.bukkit.entity.Player;

import java.util.UUID;

public class legend extends PlaceholderHook{
    private static final String hook_name = "legend";
    @Override
    public String onPlaceholderRequest(Player p, String indentifier) {
        if (p == null) {
            return "";
        }
        UUID player = p.getUniqueId();
        //背包神兽数量
        if (indentifier.equalsIgnoreCase("sum")) {
            int legendsum = 0;
            for (Pokemon pokemon : StorageProxy.getParty(player).getAll()) {
                if (pokemon != null) {
                    if (pokemon.isLegendary()) {
                        legendsum++;
                    }
                }
            }
            return String.valueOf(legendsum);
        }
        //判断是否是神兽
        if(indentifier.length() == 1){
            int slot = Integer.parseInt(indentifier)-1;
            if (StorageProxy.getParty(player).get(slot) == null && StorageProxy.getParty(player).get(slot).isEgg()) {
                return "空槽或蛋";
            } else {
                return StorageProxy.getParty(player).get(slot).isLegendary() ? "是" : "否";
            }
        }
        return "精灵变量：精灵数据错误";
    }
    public static void hook() {
        me.clip.placeholderapi.PlaceholderAPI.registerPlaceholderHook(hook_name, new legend());
    }

    public static void unhook() {
        me.clip.placeholderapi.PlaceholderAPI.unregisterPlaceholderHook(hook_name);
    }
}
