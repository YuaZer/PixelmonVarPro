package io.github.yuazer.pvpro.Common;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.storage.StorageProxy;
import me.clip.placeholderapi.PlaceholderHook;
import org.bukkit.entity.Player;

import java.util.UUID;

public class NameInfo extends PlaceholderHook {
    private static final String hook_name = "pvpro";

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
        if (judge[0].equalsIgnoreCase("name")) {
            return String.valueOf(StorageProxy.getParty(p.getUniqueId()).get(slot).getSpecies().getName());
        }
        if (judge[0].equalsIgnoreCase("localname")) {
            return String.valueOf(StorageProxy.getParty(p.getUniqueId()).get(slot).getLocalizedName());
        }
        if (judge[0].equalsIgnoreCase("move")) {
            return getMove(p, slot, Integer.parseInt(judge[2]) - 1);
        }
        return "变量错误";
    }

    public String getMove(Player p, int slot, int move) {
        UUID player = p.getUniqueId();
        Pokemon pokemon = StorageProxy.getParty(player).get(slot);
        if (pokemon != null && pokemon.getMoveset().get(move) != null) {
            return pokemon.getMoveset().get(move).getMove().getLocalizedName();
        } else {
            return "空";
        }
    }

    public static void hook() {
        me.clip.placeholderapi.PlaceholderAPI.registerPlaceholderHook(hook_name, new NameInfo());
    }

    public static void unhook() {
        me.clip.placeholderapi.PlaceholderAPI.unregisterPlaceholderHook(hook_name);
    }
}
