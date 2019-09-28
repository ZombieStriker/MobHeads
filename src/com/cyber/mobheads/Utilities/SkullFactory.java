/*     */
package com.cyber.mobheads.Utilities;


import com.cyber.mobheads.Config.ConfigController;
import com.cyber.mobheads.advancements.AdvancementsManager;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.UUID;

public class SkullFactory{
	public static ItemStack getMobSkull(MobMeta mobmeta, Player owner) {
		String encodedTexture = mobmeta.getTextureCode();
		String randomUUID = mobmeta.getUUID();
		String displayName = mobmeta.getDisplayName();
		if (encodedTexture == null) {
			return null;
		}
		if (encodedTexture.equalsIgnoreCase("[vanilla]") || randomUUID.equalsIgnoreCase("[vanilla]") || displayName.equalsIgnoreCase("[vanilla]")) {
			if (owner != null) {
				AdvancementsManager.triggerAdvancement(owner, mobmeta.getAdvancements());
			}
			if (displayName.equalsIgnoreCase("[vanilla]")) {
				return getVanillaSkull(mobmeta.getMobName());
			}
			ItemStack vanillaSkull = getVanillaSkull(mobmeta.getMobName());
			ItemMeta meta = vanillaSkull.getItemMeta();
			meta.setDisplayName(ChatColor.RESET + displayName);
			vanillaSkull.setItemMeta(meta);
			return vanillaSkull;
		}
		SkullMeta meta = (SkullMeta) Bukkit.getItemFactory().getItemMeta(Material.PLAYER_HEAD);
		GameProfile profile = new GameProfile(UUID.fromString(randomUUID), null);
		profile.getProperties().put("textures", new Property("textures", encodedTexture));
		Field profileField = null;
		try {
			profileField = meta.getClass().getDeclaredField("profile");
			profileField.setAccessible(true);
			profileField.set(meta, profile);
		} catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e1) {
			e1.printStackTrace();
		}
		ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1, (short) 3);
		meta.setDisplayName(ChatColor.RESET + displayName);
		if (owner != null) {
			AdvancementsManager.triggerAdvancement(owner, mobmeta.getAdvancements());
		}
		skull.setItemMeta(meta);
		return skull;
	}

	public static ItemStack getPlayerSkull(String playername, Player killer) {
		ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1);
		ItemMeta itemMeta = item.getItemMeta();
		((SkullMeta) itemMeta).setOwner(playername);
		item.setItemMeta(itemMeta);
		if (killer != null) AdvancementsManager.triggerAdvancement(killer, ConfigController.getAdvancementsPlayer());
		return item;
	}

	public static ItemStack getVanillaSkull(MobNames mobname) {
		Material mat;
		switch (mobname) {
			case Ender_Dragon:
				mat = Material.DRAGON_HEAD;
				return new ItemStack(mat, 1);
			case Zombie:
				mat = Material.ZOMBIE_HEAD;
				return new ItemStack(mat, 1);
			case Skeleton:
				mat = Material.SKELETON_SKULL;
				return new ItemStack(mat, 1);
			case Creeper:
				mat = Material.CREEPER_HEAD;
				return new ItemStack(mat, 1);
			case Wither_Skeleton:
				mat = Material.WITHER_SKELETON_SKULL;
				return new ItemStack(mat, 1);
		}
		return null;
	}


	public static String getVanillaName(MobNames mobname) {
		Material mat;
		switch (mobname) {
			case Skeleton:
				return "Skeleton Head";
			case Creeper:
				return "Creeper Head";
			case Zombie:
				return "Zombie Head";
			case Ender_Dragon:
				return "Dragon Head";
			case Wither_Skeleton:
				return "Wither Skeleton Head";
		}
		return null;
	}
	public static String getVanillaName(Entity entity) {
		switch (entity.getType()) {
			case SKELETON:
				return "Skeleton Head";
			case CREEPER:
				return "Creeper Head";
			case ZOMBIE:
				return "Zombie Head";
			case ENDER_DRAGON:
				return "Dragon Head";
			case WITHER_SKELETON:
				return "Wither Skeleton Head";
		}
		return null;
	}
}


/* Location:              F:\Minecraft Servers\SpigotLobby\plugins\MobHeads 2.4.jar!\com\cyber\mobheads\Utilities\SkullFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.7
 */