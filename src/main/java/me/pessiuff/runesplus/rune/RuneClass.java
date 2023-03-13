package me.pessiuff.runesplus.rune;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import de.tr7zw.nbtapi.NBTListCompound;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class RuneClass extends ItemStack {
    private final PotionEffectType effect;
    private final Integer level;

    public RuneClass(@NotNull PotionEffectType effect, @NotNull Integer level) {
        super(Material.PLAYER_HEAD, 1);
        this.effect = effect;
        this.level = level;
        this.setItemMeta(getResultItemMeta());
    }

    private ItemMeta getResultItemMeta() {
        NBTItem runeNBT = new NBTItem(this);
        NBTCompound skullCompound = runeNBT.addCompound("SkullOwner");
        skullCompound.setString("Id", "bdc273dd-a63e-44e0-873b-d76a145fa624");
        NBTListCompound skullTextureCompound = skullCompound.addCompound("Properties").getCompoundList("textures").addCompound();
        skullTextureCompound.setString("Value", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTIyMjg3NjVkZjBlMmViZDZjM2EzZmRlMDcwZGRjOGM1NTFjZWI0YTQzYjk1OWUxYzQ0ZDM0OWNlNTE2NTYwIn19fQ==");
        NBTCompound runeCompound = runeNBT.addCompound("Rune");
        runeCompound.setString("Effect", this.effect.getName());
        runeCompound.setInteger("Level", this.level);

        ItemMeta runeMeta = runeNBT.getItem().getItemMeta();
        runeMeta.displayName(Component.text(ChatColor.translateAlternateColorCodes('&', "&6Rune Stone")));
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text(ChatColor.translateAlternateColorCodes('&', "&eEffect: &d" + this.effect.getName())));
        lore.add(Component.text(ChatColor.translateAlternateColorCodes('&', "&eLevel: &f" + this.level)));
        runeMeta.lore(lore);
        runeNBT.getItem().setItemMeta(runeMeta);
        return runeNBT.getItem().getItemMeta();
    }
}
