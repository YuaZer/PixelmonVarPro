package io.github.yuazer.pvpro.Common;

import com.pixelmonmod.pixelmon.api.storage.StorageProxy;
import me.clip.placeholderapi.PlaceholderHook;
import org.bukkit.entity.Player;

public class Shiny extends PlaceholderHook {
    private static final String hook_name = "shiny";

    @Override
    public String onPlaceholderRequest(Player p, String indentifier) {
        int slot = Integer.parseInt(indentifier.split("_")[1]);
        if (p == null) {
            return "";
        }
        if (StorageProxy.getParty(p.getUniqueId()).get(slot) == null && StorageProxy.getParty(p.getUniqueId()).get(slot).isEgg()) {
            return "空槽或蛋";
        } else {
            return StorageProxy.getParty(p.getUniqueId()).get(slot).isShiny() ? "是" : "否";
        }
    }

    public static void hook() {
        me.clip.placeholderapi.PlaceholderAPI.registerPlaceholderHook(hook_name, new Shiny());
    }

    public static void unhook() {
        me.clip.placeholderapi.PlaceholderAPI.unregisterPlaceholderHook(hook_name);
    }
}
