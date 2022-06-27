package io.github.yuazer.pvpro.Utils;

import io.github.yuazer.pvpro.pvpro;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public class YamlUtils {
    public static YamlConfiguration data;
    private static FileConfiguration newConfig = null;
    private static File configFile = null;
    private static ClassLoader classLoader = null;

    /**
     * 重载指定YAML文件
     * fileName示例:
     * - config.yml
     * - data.yml
     */
    public void reloadYaml(String fileName) {
        newConfig = YamlConfiguration.loadConfiguration(configFile);
        InputStream defConfigStream = this.getResource(fileName);
        if (defConfigStream != null) {
            newConfig.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream, StandardCharsets.UTF_8)));
        }
    }

    /**
     * 返回Config.yml的指定String
     */
    public String getConfigMessage(String path) {
        return pvpro.getInstance().getConfig().getString(path).replace("&", "§");
    }
    public double getConfigDouble(String path) {
        return pvpro.getInstance().getConfig().getDouble(path);
    }

    /**
     * fileName 举例：
     * - data.yml
     */
    public FileConfiguration getFile(String fileName) {
        File file = new File(pvpro.getInstance().getDataFolder(), fileName);
        return YamlConfiguration.loadConfiguration(file);
    }
    public File setFile(String fileName){
        File file = new File(pvpro.getInstance().getDataFolder(),fileName);
        return file;
    }

    public InputStream getResource(String filename) {
        if (filename == null) {
            throw new IllegalArgumentException("Filename cannot be null");
        } else {
            try {
                URL url = this.getClassLoader().getResource(filename);
                if (url == null) {
                    return null;
                } else {
                    URLConnection connection = url.openConnection();
                    connection.setUseCaches(false);
                    return connection.getInputStream();
                }
            } catch (IOException var4) {
                return null;
            }
        }
    }

    protected final ClassLoader getClassLoader() {
        return classLoader;
    }
}
