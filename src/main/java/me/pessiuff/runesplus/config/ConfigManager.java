package me.pessiuff.runesplus.config;

import me.pessiuff.runesplus.RunesPlus;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {
    private final RunesPlus pluginInstance;

    public ConfigManager(RunesPlus pluginInstance) {
        this.pluginInstance = pluginInstance;

        loadConfig();
    }

    public void loadConfig() {
        pluginInstance.reloadConfig();
        pluginInstance.saveDefaultConfig();
        FileConfiguration config = pluginInstance.getConfig();
        config.options().copyDefaults(true);
        pluginInstance.saveConfig();
    }

    /* public FileConfiguration getConfig() {
        return config;
    } */
}
