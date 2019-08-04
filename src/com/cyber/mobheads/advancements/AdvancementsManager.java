package com.cyber.mobheads.advancements;

import com.cyber.mobheads.Main;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.entity.Player;

import java.util.List;

public class AdvancementsManager {
	public static void triggerAdvancement(Player player, List<String> advancementNames) {
		for (String fullAdvName : advancementNames) {
			fullAdvName = fullAdvName.replace("mobheads:", "");
			String advName = fullAdvName.split(" ")[0];
			String criteriaName = fullAdvName.split(" ")[1];
			NamespacedKey key = new NamespacedKey(Main.getPlugin(), advName);
			Advancement advancement = Bukkit.getAdvancement(key);
			if (advancement == null) {
				return;
			}
			AdvancementProgress progress = player.getAdvancementProgress(advancement);
			progress.awardCriteria(criteriaName);
		}
	}
}