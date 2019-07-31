/*     */ package com.cyber.mobheads;
/*     */ 
/*     */ import com.cyber.mobheads.Config.ConfigController;
/*     */ import com.cyber.mobheads.Utilities.Broadcaster;
/*     */ import com.cyber.mobheads.Utilities.MinecraftAccountValidator;
/*     */ import com.cyber.mobheads.Utilities.MobMeta;
/*     */ import com.cyber.mobheads.Utilities.SkullFactory;
/*     */ import com.cyber.mobheads.listeners.EntityDeathListener;
/*     */ import com.cyber.mobheads.listeners.FishListener;
/*     */ import com.cyber.mobheads.listeners.SkullBreakListener;
/*     */ import java.io.File;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.OfflinePlayer;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.configuration.file.FileConfiguration;
/*     */ import org.bukkit.configuration.file.YamlConfiguration;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.plugin.java.JavaPlugin;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Main
/*     */   extends JavaPlugin
/*     */ {
/*     */   private static FileConfiguration config;
/*     */   private static File configFile;
/*     */   private static Plugin plugin;
/*     */   
/*     */   public void onEnable() {
/*  44 */     config = getConfig();
/*  45 */     config.options().copyDefaults(true);
/*  46 */     saveDefaultConfig();
/*  47 */     configFile = new File(getDataFolder(), "config.yml");
/*     */     
/*  49 */     plugin = this;
/*     */ 
/*     */     
/*  52 */     Bukkit.getPluginManager().registerEvents(new EntityDeathListener(), this);
/*  53 */     Bukkit.getPluginManager().registerEvents(new SkullBreakListener(), this);
/*  54 */     Bukkit.getPluginManager().registerEvents(new FishListener(), this);
/*     */     
/*  56 */     ConfigController.configurationCheck();
/*     */     
/*  58 */     Broadcaster.outputInfoConsole("Mob Heads enabled", 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  63 */   public void onDisable() { Broadcaster.outputInfoConsole("Mob Heads disabled", 0); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
/*  71 */     if (args.length >= 1) {
/*  72 */       String message; switch (args[0]) {
/*     */         
/*     */         case "give":
/*  75 */           if (!sender.hasPermission("com.cyber.mobheads.give")) {
/*  76 */             String message = ConfigController.getMessage("Messages.Error.No-permission");
/*     */             
/*  78 */             if (!message.isEmpty()) {
/*  79 */               sender.sendMessage(message);
/*     */             }
/*  81 */             return false;
/*     */           } 
/*     */           
/*  84 */           if (args.length <= 2) {
/*  85 */             String message = ConfigController.getMessage("Messages.Commands-usage.Give");
/*     */             
/*  87 */             if (!message.isEmpty()) {
/*  88 */               sender.sendMessage(message);
/*     */             }
/*  90 */             return false;
/*     */           } 
/*  92 */           if (args.length == 3) {
/*  93 */             Player player = Bukkit.getPlayer(args[1]);
/*  94 */             if (player == null) {
/*  95 */               String message = ConfigController.getMessage("Messages.Error.No-such-player");
/*     */               
/*  97 */               if (!message.isEmpty()) {
/*  98 */                 sender.sendMessage(message.replace("[given-player-name]", args[1]));
/*     */               }
/* 100 */               return false;
/*     */             } 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 106 */             Set<String> skullList = ConfigController.getSkullList(args[2]);
/*     */             
/* 108 */             MobMeta mobmeta = ConfigController.getSpecificConfigMobMeta(args[2]);
/*     */             
/* 110 */             if (skullList == null && mobmeta == null) {
/*     */ 
/*     */               
/* 113 */               if (MinecraftAccountValidator.isValidPlayer(args[2])) {
/*     */                 
/* 115 */                 String targetPlayer = Bukkit.getOfflinePlayer(args[2]).getName();
/* 116 */                 String message = ConfigController.getMessage("Messages.Success.Head-given-sender");
/* 117 */                 if (!message.isEmpty()) {
/* 118 */                   sender.sendMessage(message.replace("[name-of-head]", targetPlayer).replace("[receiver]", player.getName()));
/*     */                 }
/* 120 */                 givePlayerSkull(player, targetPlayer);
/* 121 */                 return true;
/*     */               } 
/*     */ 
/*     */               
/* 125 */               showAllSupportedMobs(sender);
/* 126 */               return false;
/*     */             } 
/*     */ 
/*     */ 
/*     */             
/* 131 */             if (skullList != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 136 */               String message = ConfigController.getMessage("Messages.Commands-usage.Show-sub-skulls").replace("[input]", args[2]).replace("[skull-list]", skullList.toString().replace("[", "").replace("]", ""));
/* 137 */               if (!message.isEmpty()) {
/* 138 */                 sender.sendMessage(message);
/*     */               }
/* 140 */               return false;
/*     */             } 
/*     */ 
/*     */             
/* 144 */             if (mobmeta == null) {
/* 145 */               showAllSupportedMobs(sender);
/* 146 */               return false;
/*     */             } 
/*     */ 
/*     */             
/* 150 */             String message = ConfigController.getMessage("Messages.Success.Head-given-sender");
/* 151 */             if (!message.isEmpty()) {
/* 152 */               sender.sendMessage(message.replace("[name-of-head]", mobmeta.getDisplayName()).replace("[receiver]", player.getName()));
/*     */             }
/* 154 */             giveMobSkull(player, mobmeta);
/* 155 */             return true;
/*     */           } 
/*     */         
/*     */         case "reload":
/* 159 */           if (!sender.hasPermission("com.cyber.mobheads.reload")) {
/* 160 */             String message = ConfigController.getMessage("Messages.Error.No-permission");
/* 161 */             if (!message.isEmpty()) {
/* 162 */               sender.sendMessage(message);
/*     */             }
/* 164 */             return false;
/*     */           } 
/*     */           
/* 167 */           config = YamlConfiguration.loadConfiguration(configFile);
/* 168 */           ConfigController.refreshConfig(config);
/* 169 */           message = ConfigController.getMessage("Messages.Success.Reload");
/* 170 */           if (!message.isEmpty()) {
/* 171 */             sender.sendMessage(message);
/*     */           }
/*     */           
/* 174 */           return true;
/*     */       } 
/*     */     
/*     */     } 
/* 178 */     if (!sender.hasPermission("com.cyber.mobheads.help")) {
/* 179 */       String message = ConfigController.getMessage("Messages.Error.No-permission");
/* 180 */       if (!message.isEmpty()) {
/* 181 */         sender.sendMessage(message);
/*     */       }
/* 183 */       return false;
/*     */     } 
/* 185 */     showHelp(sender);
/* 186 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void showHelp(CommandSender sender) {
/* 192 */     String message = ConfigController.getMessage("Messages.Commands-usage.Give");
/* 193 */     if (!message.isEmpty()) {
/* 194 */       sender.sendMessage(message);
/*     */     }
/* 196 */     message = ConfigController.getMessage("Messages.Commands-usage.Reload");
/* 197 */     if (!message.isEmpty()) {
/* 198 */       sender.sendMessage(message);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/* 203 */   public static FileConfiguration getMyConfig() { return config; }
/*     */ 
/*     */   
/*     */   private void giveMobSkull(Player player, MobMeta mobmeta) {
/* 207 */     ItemStack skull = (new SkullFactory()).getMobSkull(mobmeta, player);
/*     */     
/* 209 */     String message = ConfigController.getMessage("Messages.Success.Mob-head-given-receiver");
/* 210 */     if (!message.isEmpty()) {
/* 211 */       player.sendMessage(message.replace("[mobname]", mobmeta.getDisplayName()));
/*     */     }
/*     */     
/* 214 */     if (player.getInventory().firstEmpty() != -1) {
/*     */       
/* 216 */       player.getInventory().addItem(new ItemStack[] { skull });
/* 217 */       player.updateInventory();
/*     */     }
/*     */     else {
/*     */       
/* 221 */       player.getWorld().dropItem(player.getLocation(), skull);
/* 222 */       message = ConfigController.getMessage("Messages.Error.Inventory-full");
/* 223 */       if (!message.isEmpty()) {
/* 224 */         player.sendMessage(message);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void givePlayerSkull(Player receiver, String playerName) {
/* 233 */     String message = ConfigController.getMessage("Messages.Success.Player-head-given-receiver");
/* 234 */     if (!message.isEmpty()) {
/* 235 */       receiver.sendMessage(message.replace("[playername]", playerName));
/*     */     }
/*     */     
/* 238 */     ItemStack skull = (new SkullFactory()).getPlayerSkull(playerName, receiver);
/*     */     
/* 240 */     if (receiver.getInventory().firstEmpty() != -1) {
/*     */       
/* 242 */       receiver.getInventory().addItem(new ItemStack[] { skull });
/* 243 */       receiver.updateInventory();
/*     */     }
/*     */     else {
/*     */       
/* 247 */       receiver.getWorld().dropItem(receiver.getLocation(), skull);
/* 248 */       message = ConfigController.getMessage("Messages.Error.Inventory-full");
/* 249 */       if (!message.isEmpty()) {
/* 250 */         receiver.sendMessage(message);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void showAllSupportedMobs(CommandSender sender) {
/* 256 */     List<String> mobList = ConfigController.getAllMobNames();
/* 257 */     String output = "";
/*     */     
/* 259 */     for (int i = 0; i < mobList.size(); i++) {
/* 260 */       if (i < mobList.size()) {
/* 261 */         output = output + (String)mobList.get(i) + ", ";
/*     */       } else {
/*     */         
/* 264 */         output = output + "and " + (String)mobList.get(i);
/*     */       } 
/*     */     } 
/* 267 */     String message = ConfigController.getMessage("Messages.Commands-usage.Show-all-supported-heads");
/* 268 */     if (!message.isEmpty()) {
/* 269 */       sender.sendMessage(message.replace("[list-of-all-mobs]", "\n" + output));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/* 274 */   public static Plugin getPlugin() { return plugin; }
/*     */ 
/*     */   
/*     */   public OfflinePlayer getOfflinePlayer(String name) {
/* 278 */     for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
/* 279 */       if (player.getName().equalsIgnoreCase(name)) return player; 
/*     */     } 
/* 281 */     return null;
/*     */   }
/*     */ }


/* Location:              F:\Minecraft Servers\SpigotLobby\plugins\MobHeads 2.4.jar!\com\cyber\mobheads\Main.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.7
 */