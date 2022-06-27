package io.github.yuazer.pvpro.time;

import com.pixelmonmod.pixelmon.api.spawning.AbstractSpawner;
import com.pixelmonmod.pixelmon.spawning.LegendarySpawner;
import com.pixelmonmod.pixelmon.spawning.PixelmonSpawning;
import me.clip.placeholderapi.PlaceholderHook;
import org.bukkit.entity.Player;

public class SpawnLegTime extends PlaceholderHook {
    private static final String hook_name = "pokespawntime";
    @Override
    public String onPlaceholderRequest(Player p, String indentifier) {
        if (p == null) {
            return "";
        }
        if (indentifier.equalsIgnoreCase("time")){
            return String.valueOf(getTime());
        }
        return "神兽倒计时错误";
    }

    public AbstractSpawner abstractSpawner() {
        return PixelmonSpawning.coordinator.getSpawner("legendary");
    }
    public long getTime() {
        if (abstractSpawner() instanceof LegendarySpawner) {
            long l1 = ((LegendarySpawner)abstractSpawner()).nextSpawnTime;
            long l2 = System.currentTimeMillis();
            return Double.valueOf((l1 - l2) / 1000.0D).intValue();
        }
        return -1L;
    }
    public static void hook() {
        me.clip.placeholderapi.PlaceholderAPI.registerPlaceholderHook(hook_name, new SpawnLegTime());
    }

    public static void unhook() {
        me.clip.placeholderapi.PlaceholderAPI.unregisterPlaceholderHook(hook_name);
    }
}
