package com.cyber.mobheads;

import com.cyber.mobheads.Config.ConfigController;
import com.cyber.mobheads.Utilities.*;
import com.cyber.mobheads.listeners.EntityDeathListener;
import com.cyber.mobheads.listeners.FishListener;
import com.cyber.mobheads.listeners.SkullBreakListener;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Main
		extends JavaPlugin {
	private static FileConfiguration config;
	private static File configFile;
	private static Plugin plugin;

	public static FileConfiguration getMyConfig() {
		return config;
	}

	public static Plugin getPlugin() {
		return plugin;
	}

	@Override
	public void onLoad() {
		try{
			Plugin p = Bukkit.getPluginManager().getPlugin("WorldGuard");
			if(p!=null){
				WorldGuardSupport.initiate();
			}
		}catch (Error|Exception r54){}
	}

	public void onEnable() {
		config = getConfig();
		config.options().copyDefaults(true);
		saveDefaultConfig();
		configFile = new File(getDataFolder(), "config.yml");

		plugin = this;


		Bukkit.getPluginManager().registerEvents(new EntityDeathListener(), this);
		Bukkit.getPluginManager().registerEvents(new SkullBreakListener(), this);
		Bukkit.getPluginManager().registerEvents(new FishListener(), this);

		ConfigController.configurationCheck();

		SpiGetUpdater.checkAutoUpdate(this, 70019, getConfig().getBoolean("auto-update") == true);
		//Broadcaster.outputInfoConsole("Mob Heads enabled", 0);
	}

	public void onDisable() {
		/*Broadcaster.outputInfoConsole("Mob Heads disabled", 0);*/
	}

	public void a(List<String> tabs, String test, String args) {
		if (test.toLowerCase().startsWith(args.toLowerCase()))
			tabs.add(test);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		List<String> tabs = new ArrayList<>();
		if (args.length == 1) {
			a(tabs, "give", args[0]);
			a(tabs, "probability", args[0]);
			a(tabs, "reload", args[0]);
			a(tabs,"initachievements",args[0]);
			return tabs;
		} else if (args.length == 2) {
			if (args[0].equalsIgnoreCase("probability")) {
				List<String> mobList = ConfigController.getAllMobNames();
				for (String k : mobList) {
					a(tabs, k, args[1]);
				}
				return tabs;
			}
		} else if (args.length == 3) {
			if (args[0].equalsIgnoreCase("give")) {
				List<String> mobList = ConfigController.getAllMobNames();
				for (String k : mobList) {
					a(tabs, k, args[2]);
				}
				return tabs;
			}
		}
		return null;
	}
	public void copyFile(String inputPrefix, String inputPath, String outputPath ) throws IOException {

		InputStream inputStream = null;

		OutputStream outputStream = null;
		try {

			inputStream = getClass().getResourceAsStream("/"+inputPrefix+"/"+inputPath);
			outputStream = new FileOutputStream(outputPath+"/"+inputPath);

			byte[] buf = new byte[1024];

			int bytesRead;

			while ((bytesRead = inputStream.read(buf)) > 0) {

				outputStream.write(buf, 0, bytesRead);

			}
		}catch (Exception e5){
			e5.printStackTrace();
		} finally {
			inputStream.close();
			outputStream.close();
		}
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length >= 1) {
			String message;
			switch (args[0]) {
				case "initachievements":
					if (!sender.hasPermission("com.cyber.mobheads.initachievements")) {
						message = ConfigController.getMessage("Messages.Error.No-permission");

						if (!message.isEmpty()) {
							sender.sendMessage(message);
						}
						return false;
					}

					File baseDirectory = getDataFolder().getParentFile().getParentFile();
					File world = new File(baseDirectory,Bukkit.getWorlds().get(0).getName());
					File datapacks = new File(world,"datapacks");
					File mobHeads = new File(datapacks,"mobheads");



					String achievementsDir = null;
				try {
					achievementsDir = getClass().getResource("").getPath();
					achievementsDir = achievementsDir.substring(5,achievementsDir.indexOf("!")).replaceAll("%20"," ");

					if(!mobHeads.exists())
						mobHeads.mkdirs();

				} catch (Exception e) {
					e.printStackTrace();
				}
					try{
						final JarFile jar = new JarFile(achievementsDir);
						final Enumeration<JarEntry> entries = jar.entries(); //gives ALL entries in jar
						while(entries.hasMoreElements()) {
							final String name = entries.nextElement().getName();

							if (name.startsWith("achievements")) { //filter according to the path
								String relavtivePath = name.substring("achievements".length()+1);
								String fileName = name.substring(name.lastIndexOf('/')+1);

								if(name.contains("."))//has extension
								{
									copyFile("achievements",relavtivePath, mobHeads.getPath());
								}else{
									new File(mobHeads.getPath(),relavtivePath).mkdirs();
								}
							}
						}
						jar.close();

						//copyContentsTo(achievementsDir,mobHeads);


					}catch (Error|Exception e4){
						message = ConfigController.getMessage("Messages.Error.Fail");
						if (!message.isEmpty()) {
							sender.sendMessage(message);
						}
						e4.printStackTrace();
						return true;
					}
					message = ConfigController.getMessage("Messages.Success.Head-achievements");
					if (!message.isEmpty()) {
						sender.sendMessage(message);
					}

					return true;
				case "probability":

					if (!sender.hasPermission("com.cyber.mobheads.probability")) {
						message = ConfigController.getMessage("Messages.Error.No-permission");

						if (!message.isEmpty()) {
							sender.sendMessage(message);
						}
						return false;
					}

					if (args.length <= 1) {
						message = ConfigController.getMessage("Messages.Commands-usage.Probability");

						if (!message.isEmpty()) {
							sender.sendMessage(message);
						}
						return false;
					}
					if (args.length == 2) {
						Set<String> skullList = ConfigController.getSkullList(args[1]);

						MobMeta mobmeta = ConfigController.getSpecificConfigMobMeta(args[1]);

						if (skullList == null && mobmeta == null) {
							showAllSupportedMobs(sender);
							return false;
						}


						if (skullList != null) {

							message = ConfigController.getMessage("Messages.Commands-usage.Show-sub-skulls").replace("[input]", args[1]).replace("[skull-list]", skullList.toString().replace("[", "").replace("]", ""));
							if (!message.isEmpty()) {
								sender.sendMessage(message);
							}
							return false;
						}


						if (mobmeta == null) {
							showAllSupportedMobs(sender);
							return false;
						}


						message = ConfigController.getMessage("Messages.Success.Head-probability-sender");
						if(mobmeta.getDropChance()<=0){
							message = ConfigController.getMessage("Messages.Success.Head-probability-sender-basegame");
						}
						if (!message.isEmpty()) {
							String bonus = mobmeta.getDropBonus()*100 < 0.001? new DecimalFormat("0.##########").format(mobmeta.getDropBonus()*100) : mobmeta.getDropBonus()*100+"";
							sender.sendMessage(message.replace("[name-of-head]", mobmeta.getUsedDisplayName()).replace("[probability]", mobmeta.getDropChance()*100 + "").replace("[probabilitylooting]", bonus));
						}


						message = ConfigController.getMessage("Messages.Success.Head-probability-chance-sender");
						if (!message.isEmpty()) {
							int chance = (int) ((1.0 - Math.pow(1.0 - mobmeta.getDropChance(), 100)) * 100);
							if (chance > 100) chance = 100;
							int chanceB = (int) ((1.0 - Math.pow(1.0 - (mobmeta.getDropChance()+(mobmeta.getDropBonus()*3)), 100)) * 100);
							if (chanceB > 100) chanceB = 100;
							sender.sendMessage(message.replace("[name-of-head]", mobmeta.getUsedDisplayName()).replace("[probability]", chance + "").replace("[bonusprobability]",""+chanceB));
						}
						if(mobmeta.isChargedCreeperDrop()) {
							message = ConfigController.getMessage("Messages.Success.Head-probability-charged-sender");
							if (!message.isEmpty()) {
								sender.sendMessage(message);
							}
						}
					}
					return true;

				case "give":
					if (!sender.hasPermission("com.cyber.mobheads.give")) {
						message = ConfigController.getMessage("Messages.Error.No-permission");

						if (!message.isEmpty()) {
							sender.sendMessage(message);
						}
						return false;
					}

					if (args.length <= 2) {
						message = ConfigController.getMessage("Messages.Commands-usage.Give");

						if (!message.isEmpty()) {
							sender.sendMessage(message);
						}
						return false;
					}
					if (args.length == 3) {
						Player player = Bukkit.getPlayer(args[1]);
						if (player == null) {
							message = ConfigController.getMessage("Messages.Error.No-such-player");

							if (!message.isEmpty()) {
								sender.sendMessage(message.replace("[given-player-name]", args[1]));
							}
							return false;
						}


						Set<String> skullList = ConfigController.getSkullList(args[2]);

						MobMeta mobmeta = ConfigController.getSpecificConfigMobMeta(args[2]);

						if (skullList == null && mobmeta == null) {


							if (MinecraftAccountValidator.isValidPlayer(args[2])) {

								String targetPlayer = Bukkit.getOfflinePlayer(args[2]).getName();
								message = ConfigController.getMessage("Messages.Success.Head-given-sender");
								if (!message.isEmpty()) {
									sender.sendMessage(message.replace("[name-of-head]", targetPlayer).replace("[receiver]", player.getName()));
								}
								givePlayerSkull(player, targetPlayer);
								return true;
							}


							showAllSupportedMobs(sender);
							return false;
						}


						if (skullList != null) {


							message = ConfigController.getMessage("Messages.Commands-usage.Show-sub-skulls").replace("[input]", args[2]).replace("[skull-list]", skullList.toString().replace("[", "").replace("]", ""));
							if (!message.isEmpty()) {
								sender.sendMessage(message);
							}
							return false;
						}


						if (mobmeta == null) {
							showAllSupportedMobs(sender);
							return false;
						}


						message = ConfigController.getMessage("Messages.Success.Head-given-sender");
						if (!message.isEmpty()) {
							sender.sendMessage(message.replace("[name-of-head]", mobmeta.getUsedDisplayName()).replace("[receiver]", player.getName()));
						}
						giveMobSkull(player, mobmeta);
						return true;
					}

				case "reload":
					if (!sender.hasPermission("com.cyber.mobheads.reload")) {
						message = ConfigController.getMessage("Messages.Error.No-permission");
						if (!message.isEmpty()) {
							sender.sendMessage(message);
						}
						return false;
					}

					config = YamlConfiguration.loadConfiguration(configFile);
					ConfigController.refreshConfig(config);
					message = ConfigController.getMessage("Messages.Success.Reload");
					if (!message.isEmpty()) {
						sender.sendMessage(message);
					}

					return true;
			}

		}
		if (!sender.hasPermission("com.cyber.mobheads.help")) {
			String message = ConfigController.getMessage("Messages.Error.No-permission");
			if (!message.isEmpty()) {
			}
			return false;
		}
		showHelp(sender);
		return false;
	}

	private void showHelp(CommandSender sender) {
		String message = ConfigController.getMessage("Messages.Commands-usage.Give");
		if (!message.isEmpty()) {
			sender.sendMessage(message);
		}
		message = ConfigController.getMessage("Messages.Commands-usage.Reload");
		if (!message.isEmpty()) {
			sender.sendMessage(message);
		}
	}

	private void giveMobSkull(Player player, MobMeta mobmeta) {
		ItemStack skull = (new SkullFactory()).getMobSkull(mobmeta, player);

		String message = ConfigController.getMessage("Messages.Success.Mob-head-given-receiver");
		if (!message.isEmpty()) {
			player.sendMessage(message.replace("[mobname]", mobmeta.getUsedDisplayName()));
		}

		if (player.getInventory().firstEmpty() != -1) {

			player.getInventory().addItem(skull);
			player.updateInventory();
		} else {

			player.getWorld().dropItem(player.getLocation(), skull);
			message = ConfigController.getMessage("Messages.Error.Inventory-full");
			if (!message.isEmpty()) {
				player.sendMessage(message);
			}
		}
	}

	private void givePlayerSkull(Player receiver, String playerName) {
		String message = ConfigController.getMessage("Messages.Success.Player-head-given-receiver");
		if (!message.isEmpty()) {
			receiver.sendMessage(message.replace("[playername]", playerName));
		}

		ItemStack skull = (new SkullFactory()).getPlayerSkull(playerName, receiver);

		if (receiver.getInventory().firstEmpty() != -1) {

			receiver.getInventory().addItem(skull);
			receiver.updateInventory();
		} else {

			receiver.getWorld().dropItem(receiver.getLocation(), skull);
			message = ConfigController.getMessage("Messages.Error.Inventory-full");
			if (!message.isEmpty()) {
				receiver.sendMessage(message);
			}
		}
	}

	private void showAllSupportedMobs(CommandSender sender) {
		List<String> mobList = ConfigController.getAllMobNames();
		String output = "";

		for (int i = 0; i < mobList.size(); i++) {
			if (i < mobList.size()) {
				output = output + mobList.get(i) + ", ";
			} else {

				output = output + "and " + mobList.get(i);
			}
		}
		String message = ConfigController.getMessage("Messages.Commands-usage.Show-all-supported-heads");
		if (!message.isEmpty()) {
			sender.sendMessage(message.replace("[list-of-all-mobs]", "\n" + output));
		}
	}
}