package me.pessiuff.runesplus.commands.admin;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import me.pessiuff.runesplus.config.ConfigManager;
import me.pessiuff.runesplus.lang.LangManager;
import me.pessiuff.runesplus.message.MessageClass;
import me.pessiuff.runesplus.rune.RuneClass;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

@CommandAlias("runesadmin|runeadmin")
@CommandPermission("runesplus.admin")
public class AdminCommand extends BaseCommand {
    // private RunesPlus pluginInstance;
    private final ConfigManager configManager;
    private final LangManager langManager;
    private final MessageClass messageClass;

    public AdminCommand(/*RunesPlus pluginInstance, */ConfigManager configManager, LangManager langManager, MessageClass messageClass) {
        // this.pluginInstance = pluginInstance;
        this.configManager = configManager;
        this.langManager = langManager;
        this.messageClass  = messageClass;
    }

    @Default
    @Description("Shows help message.")
    public void onDefault(CommandSender sender) {
        sender.sendMessage(messageClass.getMessageList("help_message_admin"));
    }

    @Subcommand("get")
    @Description("Get rune.")
    @CommandCompletion("@effect")
    public void onGet(Player player, String effect, Integer level) {
        PotionEffectType effectType = PotionEffectType.getByName(effect);
        if (effectType == null) {
            player.sendMessage(messageClass.getMessage("effect_not_found", true));
            return;
        }
        RuneClass rune = new RuneClass(effectType, level, messageClass);

        player.getInventory().addItem(rune);
        player.sendMessage(messageClass.getMessage("rune_taken", true));
    }

    @Subcommand("give")
    @Description("Give rune.")
    @CommandCompletion("@players @effect @level")
    public void onGive(CommandSender sender, OnlinePlayer target, String effect, Integer level) {
        PotionEffectType effectType = PotionEffectType.getByName(effect);
        if (effectType == null) {
            sender.sendMessage(messageClass.getMessage("effect_not_found", true));
            return;
        }
        RuneClass rune = new RuneClass(effectType, level, messageClass);
        target.getPlayer().getInventory().addItem(rune);
        sender.sendMessage(messageClass.getMessage("rune_given", true));
        target.getPlayer().sendMessage(messageClass.getMessage("rune_received", true));
    }

    @Subcommand("reload")
    @Description("Reload the configuration & language files.")
    public void onReload(CommandSender sender) {
        configManager.loadConfig();
        langManager.loadMessages();
        sender.sendMessage(messageClass.getMessage("reloaded", true));
    }
}
