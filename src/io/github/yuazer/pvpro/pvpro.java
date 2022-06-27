package io.github.yuazer.pvpro;

import com.pixelmonmod.pixelmon.api.spawning.AbstractSpawner;
import com.pixelmonmod.pixelmon.api.storage.StorageProxy;
import com.pixelmonmod.pixelmon.spawning.LegendarySpawner;
import com.pixelmonmod.pixelmon.spawning.PixelmonSpawning;
import io.github.yuazer.pvpro.Common.*;
import io.github.yuazer.pvpro.Utils.YamlUtils;
import io.github.yuazer.pvpro.time.SpawnLegTime;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class pvpro extends JavaPlugin {
    private static pvpro instance;
    public static pvpro getInstance() {
        return instance;
    }
    private EventInfo eventInfo;
    YamlUtils utils = new YamlUtils();
    @Override
    public void onEnable() {
        Bukkit.getPluginCommand("ZPVar").setExecutor(this);
        instance = this;
        this.getLogger().info("§a精灵变量-Pro[1.16.5] QQ:2152356007定制版");
        this.getLogger().info("§3状态-    §a已加载");
        this.getLogger().info("§3作者-    §bZ菌:QQ[1109132]");
        this.getLogger().info("§3版本-    §6" + this.getDescription().getVersion());
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            saveDefaultConfig();
            int time = getConfig().getInt("BroMin");
            legend.hook();
            this.getLogger().info("§e神兽识别模块加载");
            eggInfo.hook();
            this.getLogger().info("§e孵蛋步数模块加载");
            IvsData.hook();
            this.getLogger().info("§e个体值模块加载");
            EvsData.hook();
            this.getLogger().info("§e努力值模块加载");
            ForgePoints.hook();
            this.getLogger().info("§e熔炼点模块加载");
            PokeDex.hook();
            this.getLogger().info("§e图鉴模块加载");
            Shiny.hook();
            this.getLogger().info("§e闪光模块加载");
            OtherData.hook();
            this.getLogger().info("§e其他数据模块加载");
            NameInfo.hook();
            this.getLogger().info("§e名称模块加载");
            SpawnLegTime.hook();
            this.getLogger().info("§e神兽倒计时模块加载");
            PlayerData.hook();
            this.getLogger().info("§e玩家数据模块加载");
            this.eventInfo = new EventInfo();
            eventInfo.register();
            Bukkit.getPluginManager().registerEvents(this.eventInfo, this);
            this.getLogger().info("§e精灵事件模块加载");
            (new BukkitRunnable() {
                @Override
                public void run() {
                    Bukkit.broadcastMessage(utils.getConfigMessage("Message.spawnLegend").replace("%time%", String.valueOf(getTime())));
                }
            }).runTaskTimerAsynchronously(this, 0L, (time * 60) * 20L);
        } else {
            getLogger().info("§cZ菌提示:未找到PlaceholderAPI.");
            getLogger().info("§c插件取消加载.");
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    public AbstractSpawner abstractSpawner() {
        return PixelmonSpawning.coordinator.getSpawner("legendary");
    }

    public long getTime() {
        if (abstractSpawner() instanceof LegendarySpawner) {
            long l1 = ((LegendarySpawner) abstractSpawner()).nextSpawnTime;
            long l2 = System.currentTimeMillis();
            return Double.valueOf((l1 - l2) / 1000.0D).intValue();
        }
        return -1L;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("ZPVar")) {
            if (args.length == 0) {
                sender.sendMessage("§6精灵变量§f>>>>§f输入/ZPVar help查看指令");
                return true;
            }
            if ("reload".equalsIgnoreCase(args[0]) && sender.hasPermission("pixelmonvar.admin")) {
                reloadConfig();
                sender.sendMessage(utils.getConfigMessage("Message.reload"));
                return true;
            }
            if ("test".equalsIgnoreCase(args[0])){
                if (sender instanceof Player){
                    sender.sendMessage(StorageProxy.getParty(((Player) sender).getUniqueId()).get(1).getLocalizedName());
                }
            }
            if (args[0].equalsIgnoreCase("help")) {
                sender.sendMessage("§6/ZPVar reload§f    §f重载config.yml配置文件");
                return true;
            }
        }
        return true;
    }

    @Override
    public void onDisable() {
        eggInfo.unhook();
        IvsData.unhook();
        EvsData.unhook();
        ForgePoints.unhook();
        PokeDex.unhook();
        Shiny.unhook();
        OtherData.unhook();
        NameInfo.unhook();
        this.getLogger().info("§e[PixelmonVarPro]卸载成功！");
    }
}

