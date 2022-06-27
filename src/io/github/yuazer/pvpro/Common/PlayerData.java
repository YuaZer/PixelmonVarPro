package io.github.yuazer.pvpro.Common;

import com.pixelmonmod.pixelmon.api.storage.StorageProxy;
import me.clip.placeholderapi.PlaceholderHook;
import org.bukkit.entity.Player;

public class PlayerData extends PlaceholderHook {
    private static final String hook_name = "playerdata";

    @Override
    public String onPlaceholderRequest(Player p, String indentifier) {
        if (p == null) {
            return "";
        }
        if (indentifier.equalsIgnoreCase("wins")) {
            return String.valueOf(StorageProxy.getParty(p.getUniqueId()).stats.getWins());
        }
        if (indentifier.equalsIgnoreCase("losses")) {
            return String.valueOf(StorageProxy.getParty(p.getUniqueId()).stats.getLosses());
        }
        if (indentifier.equalsIgnoreCase("total")) {
            return String.valueOf(StorageProxy.getParty(p.getUniqueId()).stats.getLosses() + StorageProxy.getParty(p.getUniqueId()).stats.getWins());
        }
        return "0";
    }
    public static void hook() {
        me.clip.placeholderapi.PlaceholderAPI.registerPlaceholderHook(hook_name, new PlayerData());
    }

    public static void unhook() {
        me.clip.placeholderapi.PlaceholderAPI.unregisterPlaceholderHook(hook_name);
    }
}
