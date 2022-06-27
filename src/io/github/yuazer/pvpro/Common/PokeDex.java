package io.github.yuazer.pvpro.Common;

import com.pixelmonmod.pixelmon.api.pokemon.species.Pokedex;
import com.pixelmonmod.pixelmon.api.registries.PixelmonSpecies;
import com.pixelmonmod.pixelmon.api.storage.StorageProxy;
import me.clip.placeholderapi.PlaceholderHook;
import org.bukkit.entity.Player;

public class PokeDex extends PlaceholderHook {
    int fulldex1 = PixelmonSpecies.getAll().size();
    private static final String hook_name = "pokedex";
    @Override
    public String onPlaceholderRequest(Player p, String indentifier) {
        int num = Integer.parseInt(indentifier.split("_")[1]) - 1;
        if (p == null) {
            return "";
        }
        if (indentifier.equalsIgnoreCase("find")){
            return String.valueOf(StorageProxy.getParty(p.getUniqueId()).playerPokedex.countCaught());
        }
        if (indentifier.equalsIgnoreCase("percent")){
            return (StorageProxy.getParty(p.getUniqueId()).playerPokedex.countCaught() / fulldex1) * 100 + "%";
        }
        if (indentifier.split("_")[0].equalsIgnoreCase("pokeid")){
            if (StorageProxy.getParty(p.getUniqueId()).get(num) == null && StorageProxy.getParty(p.getUniqueId()).get(num).isEgg()) {
                return "空槽或蛋";
            }else {
                return String.valueOf(Pokedex.nameToID(StorageProxy.getParty(p.getUniqueId()).get(num).getSpecies().getName()));
            }
        }
        if (indentifier.split("_")[0].equalsIgnoreCase("pokeid2")){
            if (StorageProxy.getParty(p.getUniqueId()).get(num) == null && StorageProxy.getParty(p.getUniqueId()).get(num).isEgg()) {
                return "空槽或蛋";
            }else {
                return String.valueOf(StorageProxy.getParty(p.getUniqueId()).get(num).getSpecies().getDex());
            }
        }
        return "PokeDex模块错误";
    }
    public static void hook() {
        me.clip.placeholderapi.PlaceholderAPI.registerPlaceholderHook(hook_name, new PokeDex());
    }

    public static void unhook() {
        me.clip.placeholderapi.PlaceholderAPI.unregisterPlaceholderHook(hook_name);
    }
}
