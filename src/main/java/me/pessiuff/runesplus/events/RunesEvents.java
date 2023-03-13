package me.pessiuff.runesplus.events;

import com.destroystokyo.paper.event.player.PlayerPostRespawnEvent;
import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import io.papermc.paper.event.player.PlayerInventorySlotChangeEvent;
import me.pessiuff.runesplus.RunesPlus;
import me.pessiuff.runesplus.message.MessageClass;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class RunesEvents implements Listener {
    private final RunesPlus pluginInstance;
    // private final ConfigManager configManager;
    // private final LangManager langManager;
    private final MessageClass messageClass;
    public RunesEvents(RunesPlus pluginInstance, /*ConfigManager configManager, LangManager langManager, */MessageClass messageClass) {
        this.pluginInstance = pluginInstance;
        // this.configManager = configManager;
        // this.langManager = langManager;
        this.messageClass = messageClass;
        registerEvents();
    }
    private final HashMap<UUID, PotionEffect> oldRuneEffect = new HashMap<>();
    @EventHandler
    private void onSlotChange(PlayerInventorySlotChangeEvent event) { // This checks equip & unequip
        if (event.getSlot() != 40) return;
        Player player = event.getPlayer();

        ItemStack oldItem = event.getOldItemStack();
        ItemStack newItem = event.getNewItemStack();

        if (oldItem == newItem)
            return;

        UUID playerUUID = player.getUniqueId();

        if (!oldItem.getType().isAir()) {
            NBTItem oldNBT = new NBTItem(oldItem);
            if (isRune(oldNBT)) {
                NBTCompound oldRuneCompound = oldNBT.getCompound("Rune");
                player.removePotionEffect(Objects.requireNonNull(PotionEffectType.getByName(oldRuneCompound.getString("Effect"))));
                if (oldRuneEffect.containsKey(playerUUID)) {
                    player.addPotionEffect(oldRuneEffect.get(playerUUID));
                    oldRuneEffect.remove(playerUUID);
                }
                player.sendMessage(messageClass.getMessage("rune_unequipped", true));
            }
        }

        if (!newItem.getType().isAir()) {
            NBTItem newNBT = new NBTItem(newItem);
            if (isRune(newNBT)) {
                NBTCompound newRuneCompound = newNBT.getCompound("Rune");
                PotionEffectType runeEffect = Objects.requireNonNull(PotionEffectType.getByName(newRuneCompound.getString("Effect")));
                if (player.getPotionEffect(runeEffect) != null)
                    oldRuneEffect.put(playerUUID, player.getPotionEffect(runeEffect));
                player.addPotionEffect(runeEffect.createEffect(51840000, newRuneCompound.getInteger("Level")));
                player.sendMessage(messageClass.getMessage("rune_equipped", true));
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) { // This fixes server restart bug
        Player player = event.getPlayer();

        ItemStack offHand = player.getInventory().getItemInOffHand();
        if (offHand.getType().isAir())
            return;

        NBTItem nbtItem = new NBTItem(offHand);
        if (isRune(nbtItem)) {
            NBTCompound runeCompound = nbtItem.getCompound("Rune");
            player.removePotionEffect(Objects.requireNonNull(PotionEffectType.getByName(runeCompound.getString("Effect"))));
        }
    }

    @EventHandler
    public void onPlayerConsumeItem(PlayerItemConsumeEvent event) { // This fixes milk bucket bug
        Player player = event.getPlayer();

        ItemStack consumedItem = event.getItem();
        if (!consumedItem.getType().equals(Material.MILK_BUCKET))
            return;

        ItemStack offHandItem = player.getInventory().getItemInOffHand();
        if (offHandItem.getType().isAir())
            return;

        NBTItem offHandItemNBT = new NBTItem(offHandItem);
        if (isRune(offHandItemNBT)) {
            event.setCancelled(true);
            UUID playerUUID = player.getUniqueId();
            oldRuneEffect.remove(playerUUID);
            NBTCompound runeCompound = offHandItemNBT.getCompound("Rune");
            PotionEffectType runeEffect = Objects.requireNonNull(PotionEffectType.getByName(runeCompound.getString("Effect")));
            for (PotionEffect effect : player.getActivePotionEffects()) {
                if (!effect.getType().equals(runeEffect))
                    player.removePotionEffect(effect.getType());
            }
        }
    }

    @EventHandler
    public void onEntityPotionEffect(EntityPotionEffectEvent event) { // This fixes higher level drank potion removing rune effect bug
        if (!(event.getEntity() instanceof Player))
            return;

        if (event.getAction().equals(EntityPotionEffectEvent.Action.REMOVED)) {
            Player player = ((Player) event.getEntity()).getPlayer();
            assert player != null;

            ItemStack offHandItem = player.getInventory().getItemInOffHand();
            if (offHandItem.getType().isAir())
                return;

            NBTItem offHandItemNBT = new NBTItem(offHandItem);
            if (isRune(offHandItemNBT)) {
                NBTCompound runeCompound = offHandItemNBT.getCompound("Rune");

                PotionEffectType runeEffect = Objects.requireNonNull(PotionEffectType.getByName(runeCompound.getString("Effect")));
                if (!Objects.requireNonNull(event.getOldEffect()).getType().equals(runeEffect))
                    return;

                player.addPotionEffect(runeEffect.createEffect(51840000, runeCompound.getInteger("Level")));
            }
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerPostRespawnEvent event) { // This fixes rune effect removing after death
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        ItemStack offHandItem = player.getInventory().getItemInOffHand();
        if (offHandItem.getType().isAir())
            return;

        NBTItem newNBT = new NBTItem(offHandItem);
        if (isRune(newNBT)) {
            NBTCompound runeCompound = newNBT.getCompound("Rune");
            PotionEffectType runeEffect = Objects.requireNonNull(PotionEffectType.getByName(runeCompound.getString("Effect")));
            if (player.getPotionEffect(runeEffect) != null)
                oldRuneEffect.put(playerUUID, player.getPotionEffect(runeEffect));
            player.addPotionEffect(runeEffect.createEffect(51840000, runeCompound.getInteger("Level")));
        }
    }

    public void registerEvents() {
        Bukkit.getServer().getPluginManager().registerEvents(this, pluginInstance);
    }

    private boolean isRune(NBTItem item) {
        if (item.getCompound("Rune") == null)
            return false;
        return item.getCompound("Rune").hasTag("Effect") || item.getCompound("Rune").hasTag("Level");
    }
}
