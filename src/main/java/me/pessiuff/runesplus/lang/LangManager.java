package me.pessiuff.runesplus.lang;

import me.pessiuff.runesplus.RunesPlus;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class LangManager {
    private final RunesPlus pluginInstance;
    private FileConfiguration langConfig;

    public LangManager(RunesPlus pluginInstance) {
        this.pluginInstance = pluginInstance;

        loadMessages();
    }

    public void loadMessages() {
        pluginInstance.saveResource("lang/en.yml", false);
        pluginInstance.saveResource("lang/ru.yml", false);
        pluginInstance.saveResource("lang/tr.yml", false);
        pluginInstance.saveResource("lang/custom.yml", false);

        String configLocale = pluginInstance.getConfig().getString("locale");

        if (configLocale == null)
            configLocale = "en";

        if (configLocale.isEmpty() || !configLocale.matches("en|ru|tr|custom"))
            configLocale = "en";

        File langFile = new File(pluginInstance.getDataFolder(), String.format("lang/%s.yml", configLocale));

        langConfig = YamlConfiguration.loadConfiguration(langFile);
    }

    public FileConfiguration getFile() {
        return langConfig;
    }
}
