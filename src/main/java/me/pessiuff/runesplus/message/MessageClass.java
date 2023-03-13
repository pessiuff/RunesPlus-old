package me.pessiuff.runesplus.message;

import me.pessiuff.runesplus.lang.LangManager;
import org.bukkit.ChatColor;

import java.util.List;

public class MessageClass {
    // private RunesPlus pluginInstance;
    private final LangManager langManager;
    // private final ConfigManager configManager;

    public MessageClass(/*RunesPlus pluginInstance, ConfigManager configManager, */LangManager langManager) {
        // this.pluginInstance = pluginInstance;
        this.langManager = langManager;
        //this.configManager = configManager;
    }

    public String getMessage(String path, boolean prefix) {
        if (!prefix)
            return ChatColor.translateAlternateColorCodes('&', String.format("%s", langManager.getFile().getString(path)));
        return ChatColor.translateAlternateColorCodes('&', String.format("%s%s", langManager.getFile().getString("prefix"), langManager.getFile().getString(path)));
    }

    public String getMessageList(String path) {
        List<String> list = langManager.getFile().getStringList(path);
        return ChatColor.translateAlternateColorCodes('&', String.join("\n", list));
    }
}
