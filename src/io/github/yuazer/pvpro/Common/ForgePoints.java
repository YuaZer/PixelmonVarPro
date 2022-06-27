package io.github.yuazer.pvpro.Common;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.stats.BattleStatsType;
import com.pixelmonmod.pixelmon.api.storage.StorageProxy;
import io.github.yuazer.pvpro.Utils.YamlUtils;
import me.clip.placeholderapi.PlaceholderHook;
import org.bukkit.entity.Player;

public class ForgePoints extends PlaceholderHook {
    private static final String hook_name = "ForgePoints";
    YamlUtils utils = new YamlUtils();
    double forge = 0;
    IvsData ivsPer = new IvsData();

    @Override
    public String onPlaceholderRequest(Player p, String indentifier) {
        if (p == null) {
            return "";
        }
        return getPoints(p, Integer.parseInt(indentifier));
    }

    //计算精灵熔炼点
    public String getPoints(Player player, int slot) {
        Pokemon pokemon = StorageProxy.getParty(player.getUniqueId()).get(slot - 1);
        String[] arr = ivsPer.onPlaceholderRequest(player, String.valueOf(slot)).split("%");
        if (!arr[0].equalsIgnoreCase("精灵变量:该精灵个体值百分比计算错误!")) {
            int ipver1 = Integer.parseInt(arr[0]);
            if (pokemon == null) {
                return "此处为空槽";
            } else {
                double hpPoints = pokemon.getIVs().getStat(BattleStatsType.HP) * finishDouble("ForgeConfig.HP");
                double atkPoints = pokemon.getIVs().getStat(BattleStatsType.ATTACK) * finishDouble("ForgeConfig.Attack");
                double dfePoints = pokemon.getIVs().getStat(BattleStatsType.DEFENSE) * finishDouble("ForgeConfig.Defence");
                double spdPoints = pokemon.getIVs().getStat(BattleStatsType.SPEED) * finishDouble("ForgeConfig.Speed");
                double satkPoints = pokemon.getIVs().getStat(BattleStatsType.SPECIAL_ATTACK) * finishDouble("ForgeConfig.SpecialAttack");
                double sdfePoints = pokemon.getIVs().getStat(BattleStatsType.SPECIAL_DEFENSE) * finishDouble("ForgeConfig.SpecialDefence");
                int forgeAll = (int) ((hpPoints + atkPoints + dfePoints + satkPoints + sdfePoints + spdPoints) / finishDouble("ForgeConfig.divide") * ipver1);
                if (pokemon.isLegendary()) {
                    return String.valueOf(forgeAll * finishDouble("ForgeConfig.isLegend"));
                } else {
                    return String.valueOf(forgeAll);
                }
            }
        }
        return "0";
    }

    //判断path所指值不为空
    public double finishDouble(String path) {
        if (utils.getConfigDouble(path) >= 0) {
            forge = utils.getConfigDouble(path);
        } else {
            forge = 1;
        }
        return forge;
    }

    public static void hook() {
        me.clip.placeholderapi.PlaceholderAPI.registerPlaceholderHook(hook_name, new ForgePoints());
    }

    public static void unhook() {
        me.clip.placeholderapi.PlaceholderAPI.unregisterPlaceholderHook(hook_name);
    }
}
