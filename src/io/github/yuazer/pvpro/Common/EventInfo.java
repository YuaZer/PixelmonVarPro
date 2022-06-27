package io.github.yuazer.pvpro.Common;

import catserver.api.bukkit.ForgeEventV2;
import com.pixelmonmod.pixelmon.api.events.BeatWildPixelmonEvent;
import com.pixelmonmod.pixelmon.api.events.CaptureEvent;
import com.pixelmonmod.pixelmon.battles.controller.participants.PixelmonWrapper;
import io.github.yuazer.pvpro.Utils.YamlUtils;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class EventInfo extends PlaceholderExpansion implements Listener {
    YamlUtils utils = new YamlUtils();
    //PAPI所用变量
    String CapPokeName = "";
    String BeatPokeName = "";
    //事件所用变量
    int capCount = 0;
    int beatCount = 0;
    int capLegCount = 0;

    @EventHandler
    public void onEvent(ForgeEventV2 event) {
        if (event.getForgeEvent() instanceof CaptureEvent.SuccessfulCapture) {
            CaptureEvent.SuccessfulCapture e = (CaptureEvent.SuccessfulCapture) event.getForgeEvent();
            Player player = Bukkit.getPlayer(e.player.func_110124_au());
            String capName = e.getPokemon().getPokemonName();
            FileConfiguration fc = utils.getFile("PokeCap/" + capName + ".yml");
            capCount = fc.getInt(player.getName());
            capCount++;
            fc.set(player.getName(), capCount);
            CapPokeName = capName;
            try {
                fc.save(utils.setFile("PokeCap/" + capName + ".yml"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (e.getPokemon().isLegendary()) {
                File file = utils.setFile("capLegendary.yml");
                FileConfiguration legfc = utils.getFile("capLegendary.yml");
                capLegCount = legfc.getInt(player.getName());
                capLegCount++;
                legfc.set(player.getName(), capLegCount);
                try {
                    fc.save(file);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        if (event.getForgeEvent() instanceof BeatWildPixelmonEvent) {
            BeatWildPixelmonEvent e = (BeatWildPixelmonEvent) event.getForgeEvent();
            Player player = Bukkit.getPlayer(e.player.func_110124_au());
            List<PixelmonWrapper> pokemonList = e.wpp.controlledPokemon;
            for (PixelmonWrapper pw : pokemonList) {
                String pwName = pw.getPokemonName();
                FileConfiguration fc = utils.getFile("PokeBeat/" + pwName + ".yml");
                beatCount = fc.getInt(player.getName());
                beatCount++;
                fc.set(player.getName(), beatCount);
                BeatPokeName = pwName;
                try {
                    fc.save(utils.setFile("PokeBeat/" + pwName + ".yml"));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @Override
    public String onPlaceholderRequest(Player p, String indentifier) {
        String[] key = indentifier.split("_");
        if (p == null) {
            return "玩家为空";
        }
        if ("cap".equalsIgnoreCase(key[0])) {
            FileConfiguration data = utils.getFile("PokeCap/" + key[1] + ".yml");
            int tempCap = data.getInt(p.getName());
            return "" + tempCap;
        }
        if ("beat".equalsIgnoreCase(key[0])) {
            FileConfiguration data = utils.getFile("PokeBeat/" + key[1] + ".yml");
            int tempCap = data.getInt(p.getName());
            return "" + tempCap;
        }
        if ("capLeg".equalsIgnoreCase(key[0])) {
            FileConfiguration data = utils.getFile("capLegendary.yml");
            int tempCap = data.getInt(p.getName());
            return "" + tempCap;
        }
        return "0";

    }

    @Override
    public String getIdentifier() {
        return "eventinfo";
    }

    @Override
    public String getAuthor() {
        return "Z菌";
    }

    @Override
    public String getVersion() {
        return "0.1";
    }
}
