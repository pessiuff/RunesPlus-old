/*
package me.pessiuff.runesplus.commands.user;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import me.pessiuff.runesplus.RunesPlus;
import me.pessiuff.runesplus.config.ConfigManager;
import me.pessiuff.runesplus.lang.LangManager;
import me.pessiuff.runesplus.message.MessageClass;
import org.bukkit.command.CommandSender;

@CommandAlias("runesplus|rplus|runes|rune")
@CommandPermission("runesplus.user")
public class UserCommand extends BaseCommand {
    private RunesPlus pluginInstance;
    private final ConfigManager configManager;
    private final LangManager langManager;
    private final MessageClass messageClass;

    public UserCommand(RunesPlus pluginInstance, ConfigManager configManager, LangManager langManager, MessageClass messageClass) {
        this.pluginInstance = pluginInstance;
        this.configManager = configManager;
        this.langManager = langManager;
        this.messageClass  = messageClass;
    }

    @Default
    @Description("Shows help message.")
    public void onDefault(CommandSender sender) {
        sender.sendMessage(messageClass.getMessageList("help_message_user"));
    }
}
*/