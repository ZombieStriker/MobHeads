package com.cyber.mobheads.Config;

import com.cyber.mobheads.Main;
import com.cyber.mobheads.Utilities.Broadcaster;
import com.cyber.mobheads.Utilities.MobMeta;
import com.cyber.mobheads.Utilities.MobNames;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class ConfigController {
	private static FileConfiguration CONFIGURATION = Main.getMyConfig();

	/*     */
	public static String getDisplayNameFromTexture(String texture) {
		String[] roots = {"ListOfMobs", "ListOfFish"};

		for (String root : roots) {
			for (String name : CONFIGURATION.getConfigurationSection(root).getKeys(false)) {

				if (CONFIGURATION.contains(root + "." + name + ".skullList")) {
					Set<String> skullList = CONFIGURATION.getConfigurationSection(root + "." + name + ".skullList").getKeys(false);
					for (String skullListItem : skullList) {
						if (CONFIGURATION.getString(root + "." + name + ".skullList." + skullListItem + ".textureCode").equals(texture))
							return ChatColor.translateAlternateColorCodes('&', CONFIGURATION.getString(root + "." + name + ".skullList." + skullListItem + ".displayName"));
					}
					continue;
				}
				if (CONFIGURATION.getString(root + "." + name + ".textureCode").equals(texture)) {
					return ChatColor.translateAlternateColorCodes('&', CONFIGURATION.getString(root + "." + name + ".displayName"));
				}
			}
		}
		return null;
	}

	public static void configurationCheck() {
		if (!CONFIGURATION.contains("support_baby_mobs") ||
				!CONFIGURATION.contains("player_skull_broadcast_message") ||
				!CONFIGURATION.contains("Player.dropChance") ||
				!CONFIGURATION.contains("Player.lootingBonus") ||
				!CONFIGURATION.contains("Player.advancements") ||
				!CONFIGURATION.contains("Player.dropOnChargedCreeperDeath") ||
				!CONFIGURATION.contains("surpress_warning") ||
				!CONFIGURATION.contains("surpress_error") ||
				!CONFIGURATION.contains("drop_player_heads_outside_inventory") ||
				!CONFIGURATION.contains("allow_self_player_head_farming")
		||!CONFIGURATION.contains("Messages.Success.Head-probability-chance-sender")) {
			Broadcaster.outputInfoConsole("Missing configuration values! Please update your configuration file!", 2);
		}
	}

	public static void refreshConfig(FileConfiguration newConfig) {
		CONFIGURATION = newConfig;
	}

	public static boolean supportBabies() {
		return CONFIGURATION.getBoolean("support_baby_mobs", false);
	}

	public static boolean broadcastEnabledPlayers() {
		return Main.getMyConfig().getBoolean("player_skull_broadcast_message", false);
	}

	public static double getDropChancePlayer(int lootingValue) {
		double dropChance = percentageToDouble(CONFIGURATION.getString("Player.dropChance", "0"));
		double lootingMultiplier = percentageToDouble(CONFIGURATION.getString("Player.lootingBonus", "0"));
		return dropChance + lootingValue * lootingMultiplier;
	}

	public static String getMessage(String path) {
		String message = CONFIGURATION.getString(path);
		if (message == null || message.length()==0) {
			Broadcaster.outputInfoConsole("Could not find message in config file! Try to rebuild the config file if this issue persists!", 2);
			return "Check Console...";
		}
		return ChatColor.translateAlternateColorCodes('&', message);
	}

	public static MobMeta getSpecificConfigMobMeta(String inputName) {
		RootItem[] arrayOfRootItem = {new RootItem("ListOfMobs", "lootingBonus"), new RootItem("ListOfFish", "luckOfTheSeaBonus")};

		for (RootItem root : arrayOfRootItem) {
			for (String configName : CONFIGURATION.getConfigurationSection(root.name).getKeys(false)) {

				if (configName.equalsIgnoreCase(inputName)) {
					if (!CONFIGURATION.contains(root.name + "." + configName + ".skullList")) {
						return new MobMeta(MobNames.valueOf(configName),
								ChatColor.translateAlternateColorCodes('&', CONFIGURATION.getString(root.name + "." + configName + ".displayName")), CONFIGURATION
								.getString(root.name + "." + configName + ".random_uuid"), CONFIGURATION
								.getString(root.name + "." + configName + ".textureCode"),
								percentageToDouble(CONFIGURATION.getString(root.name + "." + configName + ".dropChance")),
								percentageToDouble(CONFIGURATION.getString(root.name + "." + configName + "." + root.bonus)), CONFIGURATION
								.getBoolean(root.name + "." + configName + ".broadcastMessage"), CONFIGURATION
								.getBoolean(root.name + "." + configName + ".dropOnChargedCreeperDeath"), CONFIGURATION
								.getStringList(root.name + "." + configName + ".advancements"));
					}

					return null;
				}


				if (CONFIGURATION.contains(root.name + "." + configName + ".skullList")) {

					Set<String> skullList = CONFIGURATION.getConfigurationSection(root.name + "." + configName + ".skullList").getKeys(false);
					for (String listItem : skullList) {
						if (listItem.equalsIgnoreCase(inputName)) {
							return new MobMeta(null,
									ChatColor.translateAlternateColorCodes('&', CONFIGURATION.getString(root.name + "." + configName + ".skullList." + listItem + ".displayName")), CONFIGURATION
									.getString(root.name + "." + configName + ".skullList." + listItem + ".random_uuid"), CONFIGURATION
									.getString(root.name + "." + configName + ".skullList." + listItem + ".textureCode"),
									percentageToDouble(CONFIGURATION.getString(root.name + "." + configName + ".dropChance")),
									percentageToDouble(CONFIGURATION.getString(root.name + "." + configName + "." + root.bonus)), CONFIGURATION
									.getBoolean(root.name + "." + configName + ".broadcastMessage"), CONFIGURATION
									.getBoolean(root.name + "." + configName + ".dropOnChargedCreeperDeath"), CONFIGURATION
									.getStringList(root.name + "." + configName + ".skullList." + listItem + ".advancements"));
						}
					}
				}
			}
		}

		return null;
	}

	public static MobMeta getRandomConfigMobMeta(MobNames mobName, boolean fish) {
		String bonus, root;
		if (fish) {
			root = "ListOfFish";
			bonus = "luckOfTheSeaBonus";
		} else {
			root = "ListOfMobs";
			bonus = "lootingBonus";
		}

		for (String configName : CONFIGURATION.getConfigurationSection(root).getKeys(false)) {
			if (mobName.name().equalsIgnoreCase(configName)) {

				if (CONFIGURATION.contains(root + "." + configName + ".skullList")) {
					Set<String> skullList = CONFIGURATION.getConfigurationSection(root + "." + configName + ".skullList").getKeys(false);


					int nr = (new Random()).nextInt(skullList.size());
					String name = (String) skullList.toArray()[nr];
					return new MobMeta(mobName,
							ChatColor.translateAlternateColorCodes('&', CONFIGURATION.getString(root + "." + configName + ".skullList." + name + ".displayName")), CONFIGURATION
							.getString(root + "." + configName + ".skullList." + name + ".random_uuid"), CONFIGURATION
							.getString(root + "." + configName + ".skullList." + name + ".textureCode"),
							percentageToDouble(CONFIGURATION.getString(root + "." + configName + ".dropChance")),
							percentageToDouble(CONFIGURATION.getString(root + "." + configName + "." + bonus)), CONFIGURATION
							.getBoolean(root + "." + configName + ".broadcastMessage"), CONFIGURATION
							.getBoolean(root + "." + configName + ".dropOnChargedCreeperDeath"), CONFIGURATION
							.getStringList(root + "." + configName + ".skullList." + name + ".advancements"));
				}
				return new MobMeta(mobName,
						ChatColor.translateAlternateColorCodes('&', CONFIGURATION.getString(root + "." + configName + ".displayName")), CONFIGURATION
						.getString(root + "." + configName + ".random_uuid"), CONFIGURATION
						.getString(root + "." + configName + ".textureCode"),
						percentageToDouble(CONFIGURATION.getString(root + "." + configName + ".dropChance")),
						percentageToDouble(CONFIGURATION.getString(root + "." + configName + "." + bonus)), CONFIGURATION
						.getBoolean(root + "." + configName + ".broadcastMessage"), CONFIGURATION
						.getBoolean(root + "." + configName + ".dropOnChargedCreeperDeath"), CONFIGURATION
						.getStringList(root + "." + configName + ".advancements"));
			}
		}

		Broadcaster.outputInfoConsole("Could not find the " + mobName.name() + " mob in the config file! Try to rebuild your config file, if this won't fix it, report this issue to the developer!", 2);
		return null;
	}

	private static double percentageToDouble(String percentage) {
		if (percentage == null) return 0.0D;
		return Double.parseDouble(percentage.replace(",", ".").replace("%", "")) / 100.0D;
	}


	public static List<String> getAdvancementsPlayer() {
		return CONFIGURATION.getStringList("Player.advancements");
	}


	public static List<String> getAllMobNames() {
		List<String> output = new ArrayList();

		String[] roots = {"ListOfMobs", "ListOfFish"};

		for (String root : roots) {
			for (String name : CONFIGURATION.getConfigurationSection(root).getKeys(false)) {

				if (CONFIGURATION.contains(root + "." + name + ".skullList")) {
					Set<String> skullList = CONFIGURATION.getConfigurationSection(root + "." + name + ".skullList").getKeys(false);
					for (String skullListItem : skullList) {
						output.add(skullListItem);
					}
					continue;
				}
				output.add(name);
			}
		}
		return output;
	}

	public static boolean chargedCreeperDrop(MobNames mobName) {
		if (mobName == null) {
			return false;
		}
		for (String configName : CONFIGURATION.getConfigurationSection("ListOfMobs").getKeys(false)) {
			if (configName.equalsIgnoreCase(mobName.name())) {
				return CONFIGURATION.getBoolean("ListOfMobs." + configName + ".dropOnChargedCreeperDeath");
			}
		}
		return false;
	}

	public static Set<String> getSkullList(String mobname) {
		String[] roots = {"ListOfMobs", "ListOfFish"};

		for (String root : roots) {
			for (String name : CONFIGURATION.getConfigurationSection(root).getKeys(false)) {
				if (name.equalsIgnoreCase(mobname) && CONFIGURATION.contains(root + "." + name + ".skullList")) {
					return CONFIGURATION.getConfigurationSection(root + "." + name + ".skullList").getKeys(false);
				}
			}
		}
		return null;
	}

	public static boolean chargedCreeperDropForPlayer() {
		return CONFIGURATION.getBoolean("Player.dropOnChargedCreeperDeath", false);
	}

	public static boolean surpressWarnings() {
		return CONFIGURATION.getBoolean("surpress_warning", false);
	}

	public static boolean surpressErrors() {
		return CONFIGURATION.getBoolean("surpress_error", false);
	}

	public static boolean dropOutsideInventory() {
		return CONFIGURATION.getBoolean("drop_player_heads_outside_inventory", false);
	}


	public static boolean allow_self_player_head_farming() {
		return CONFIGURATION.getBoolean("allow_self_player_head_farming", false);
	}


	public static class RootItem {
		private String name;
		private String bonus;

		public RootItem(String n, String b) {
			this.name = n;
			this.bonus = b;
		}


		public String getName() {
			return this.name;
		}

		public String getBonus() {
			return this.bonus;
		}

	}

}


/* Location:              F:\Minecraft Servers\SpigotLobby\plugins\MobHeads 2.4.jar!\com\cyber\mobheads\Config\ConfigController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.7
 */