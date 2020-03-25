package com.cyber.mobheads.listeners;

import com.cyber.mobheads.Config.ConfigController;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;

public class SkullBreakListener implements Listener {
	@EventHandler
	public void onSkullDrop(ItemSpawnEvent event) {
		if (event.getEntity().getItemStack().getType() != Material.PLAYER_HEAD) {
			return;
		}


		SkullMeta meta = (SkullMeta) event.getEntity().getItemStack().getItemMeta();

		if (meta.hasLocalizedName()) {
			return;
		}


		GameProfile profile = null;
		Field profileField = null;
		try {
			profileField = meta.getClass().getDeclaredField("profile");
			profileField.setAccessible(true);
			profile = (GameProfile) profileField.get(meta);
		} catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e1) {
			e1.printStackTrace();
		}

		if (profile == null) {
			return;
		}


		PropertyMap map = profile.getProperties();


		String code = null;
		try {
			code = ((Property) map.get("textures").toArray()[0]).getValue();
		} catch (ArrayIndexOutOfBoundsException e) {
			return;
		}


		if (code == null) {
			return;
		}


		String displayName = ConfigController.getDisplayNameFromTexture(code);

		if (displayName == null) {
			return;
		}


		ItemMeta itemMeta = event.getEntity().getItemStack().getItemMeta();
		itemMeta.setDisplayName(ChatColor.RESET + displayName);
		event.getEntity().getItemStack().setItemMeta(itemMeta);
	}
}
