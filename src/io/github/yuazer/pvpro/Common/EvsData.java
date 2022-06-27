package io.github.yuazer.pvpro.Common;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.stats.BattleStatsType;
import com.pixelmonmod.pixelmon.api.storage.StorageProxy;
import me.clip.placeholderapi.PlaceholderHook;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.UUID;

public class EvsData extends PlaceholderHook {
    private static final String hook_name = "evsdata";

    @Override
    public String onPlaceholderRequest(Player p, String indentifier) {
        if (p == null) {
            return "";
        }
        UUID player = p.getUniqueId();
        if (indentifier.length() == 1) {
            int slot = Integer.parseInt(indentifier) - 1;
            if (StorageProxy.getParty(player).get(slot) == null && StorageProxy.getParty(player).get(slot).isEgg()) {
                return "此处为空槽或蛋";
            } else {
                return String.valueOf(StorageProxy.getParty(player).get(slot).getEVs().getTotal());
            }
        } else {
            String[] word = indentifier.split("_");
            int slot = Integer.parseInt(word[1]) - 1;
            Pokemon pkm = StorageProxy.getParty(player).get(slot);
            if ("per".equalsIgnoreCase(word[0])) {
                DecimalFormat df = new DecimalFormat("#0.##");
                if (pkm != null && !pkm.isEgg()) {
                    return perEvs(df, pkm);
                } else {
                    return "精灵为空或蛋";
                }
            }
            if ("hp".equalsIgnoreCase(word[0])) {
                if (pkm != null && !pkm.isEgg()) {
                    return String.valueOf(StorageProxy.getParty(player).get(slot).getEVs().getStat(BattleStatsType.HP));
                } else {
                    return "精灵为空或蛋";
                }
            }
            if ("attack".equalsIgnoreCase(word[0])) {
                if (pkm != null && !pkm.isEgg()) {
                    return String.valueOf(StorageProxy.getParty(player).get(slot).getEVs().getStat(BattleStatsType.ATTACK));
                } else {
                    return "精灵为空或蛋";
                }
            }
            if ("defense".equalsIgnoreCase(word[0])) {
                if (pkm != null && !pkm.isEgg()) {
                    return String.valueOf(StorageProxy.getParty(player).get(slot).getEVs().getStat(BattleStatsType.DEFENSE));
                } else {
                    return "精灵为空或蛋";
                }
            }
            if ("specialattack".equalsIgnoreCase(word[0])) {
                if (pkm != null && !pkm.isEgg()) {
                    return String.valueOf(StorageProxy.getParty(player).get(slot).getEVs().getStat(BattleStatsType.SPECIAL_ATTACK));
                } else {
                    return "精灵为空或蛋";
                }
            }
            if ("specialdefense".equalsIgnoreCase(word[0])) {
                if (pkm != null && !pkm.isEgg()) {
                    return String.valueOf(StorageProxy.getParty(player).get(slot).getEVs().getStat(BattleStatsType.SPECIAL_DEFENSE));
                } else {
                    return "精灵为空或蛋";
                }
            }
            if ("speed".equalsIgnoreCase(word[0])) {
                if (pkm != null && !pkm.isEgg()) {
                    return String.valueOf(StorageProxy.getParty(player).get(slot).getEVs().getStat(BattleStatsType.SPEED));
                } else {
                    return "精灵为空或蛋";
                }
            }
        }
        return "精灵变量:该精灵努力值计算错误!";
    }

    private String perEvs(DecimalFormat df, Pokemon pkm) {
        if (pkm == null) {
            return "此处为空槽";
        } else if (pkm != null) {
            int ivSum = pkm.getStats().getEVs().getStat(BattleStatsType.HP) +
                    pkm.getStats().getEVs().getStat(BattleStatsType.ATTACK) +
                    pkm.getStats().getEVs().getStat(BattleStatsType.SPEED) +
                    pkm.getStats().getEVs().getStat(BattleStatsType.SPECIAL_ATTACK) +
                    pkm.getStats().getEVs().getStat(BattleStatsType.SPECIAL_DEFENSE) +
                    pkm.getStats().getEVs().getStat(BattleStatsType.DEFENSE);
            String totalIVs = df.format((int) ((double) ivSum / 186.0D * 100.0D)) + "%";
            return totalIVs;
        } else {
            return "";
        }
    }

    public static void hook() {
        me.clip.placeholderapi.PlaceholderAPI.registerPlaceholderHook(hook_name, new EvsData());
    }

    public static void unhook() {
        me.clip.placeholderapi.PlaceholderAPI.unregisterPlaceholderHook(hook_name);
    }
}
