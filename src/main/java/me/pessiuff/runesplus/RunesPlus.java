package me.pessiuff.runesplus;

import co.aikar.commands.PaperCommandManager;
import me.pessiuff.runesplus.commands.admin.AdminCommand;
import me.pessiuff.runesplus.config.ConfigManager;
import me.pessiuff.runesplus.events.RunesEvents;
import me.pessiuff.runesplus.lang.LangManager;
import me.pessiuff.runesplus.message.MessageClass;
import me.pessiuff.runesplus.misc.MiscClass;
import org.bukkit.plugin.java.JavaPlugin;

public final class RunesPlus extends JavaPlugin {
    @Override
    public void onEnable() {
        // Initialize configuration manager
        ConfigManager configManager = new ConfigManager(this);

        // Initialize language manager
        LangManager langManager = new LangManager(this);

        // Initialize message class
        MessageClass messageClass  = new MessageClass(/*this, configManager, */langManager);

        // Initialize command manager
        PaperCommandManager commandManager = new PaperCommandManager(this);
        // commandManager.registerCommand(new UserCommand(this, configManager, langManager, messageClass));
        commandManager.registerCommand(new AdminCommand(/*this, */configManager, langManager, messageClass));
        commandManager.getCommandCompletions().registerAsyncCompletion("effect", ctx ->
                MiscClass.runeEffects
        );

        // Register events
        RunesEvents events = new RunesEvents(this, /*configManager, langManager,*/ messageClass);
    }
}
