package io.github.yuazer.pvpro.Common;

import com.pixelmonmod.pixelmon.api.storage.StorageProxy;
import me.clip.placeholderapi.PlaceholderHook;
import org.bukkit.entity.Player;

public class OtherData extends PlaceholderHook {
    private static final String hook_name = "otherdata";

    @Override
    public String onPlaceholderRequest(Player p, String indentifier) {
        if (p == null) {
            return "";
        }
        String[] judge = indentifier.split("_");
        int slot = Integer.parseInt(judge[1]) - 1;
        if (StorageProxy.getParty(p.getUniqueId()).get(slot) == null && StorageProxy.getParty(p.getUniqueId()).get(slot).isEgg()) {
            return "空槽或蛋";
        }
        if (judge[0].equalsIgnoreCase("level")) {
            return String.valueOf(StorageProxy.getParty(p.getUniqueId()).get(slot).getPokemonLevel());
        }
        if (judge[0].equalsIgnoreCase("friendship")) {
            return String.valueOf(StorageProxy.getParty(p.getUniqueId()).get(slot).getFriendship());
        }
        if (judge[0].equalsIgnoreCase("ability")) {
            return StorageProxy.getParty(p.getUniqueId()).get(slot).getAbilityName();
        }
        if (judge[0].equalsIgnoreCase("nature")) {
            return StorageProxy.getParty(p.getUniqueId()).get(slot).getNature().getLocalizedName();
        }
        if (judge[0].equalsIgnoreCase("growth")) {
            return StorageProxy.getParty(p.getUniqueId()).get(slot).getGrowth().getLocalizedName();
        }
        return "变量错误";
    }

    public static void hook() {
        me.clip.placeholderapi.PlaceholderAPI.registerPlaceholderHook(hook_name, new OtherData());
    }

    public static void unhook() {
        me.clip.placeholderapi.PlaceholderAPI.unregisterPlaceholderHook(hook_name);
    }
}
