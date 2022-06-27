package io.github.yuazer.pvpro.Common;

import com.pixelmonmod.pixelmon.api.config.PixelmonConfigProxy;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.storage.StorageProxy;
import me.clip.placeholderapi.PlaceholderHook;
import org.bukkit.entity.Player;

import java.util.UUID;

public class eggInfo extends PlaceholderHook {
    private static final String hook_name = "eggcheck";

    private String checkStep(UUID player, int x) {
        if (StorageProxy.getParty(player).get(x) == null) {
            return "此处为空槽";
        }
        if (!StorageProxy.getParty(player).get(x).isEgg()) {
            return "这不是个蛋";
        } else {
            Pokemon pokemon = StorageProxy.getParty(player).get(x);
            int Step = (pokemon.getEggCycles() + 1) * PixelmonConfigProxy.getBreeding().getStepsPerEggCycle() - pokemon.getEggSteps();
            return String.valueOf(Step);
        }
    }

    @Override
    public String onPlaceholderRequest(Player p, String indentifier) {
        if (p == null) {
            return "";
        }
        UUID player = p.getUniqueId();
        String[] key = indentifier.split("_");
        int x = Integer.parseInt(key[1]);
        if ("step".equalsIgnoreCase(key[0]) && x > 0 && x <= 6) {
            return checkStep(player, x - 1);
        }
        if ("isegg".equalsIgnoreCase(key[0])){
            return String.valueOf(StorageProxy.getParty(p.getUniqueId()).get(x-1).isEgg());
        }
        return "蛋模块：错误";
    }

    public static void hook() {
        me.clip.placeholderapi.PlaceholderAPI.registerPlaceholderHook(hook_name, new eggInfo());
    }

    public static void unhook() {
        me.clip.placeholderapi.PlaceholderAPI.unregisterPlaceholderHook(hook_name);
    }
}
