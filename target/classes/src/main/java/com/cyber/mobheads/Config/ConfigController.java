/*     */ package com.cyber.mobheads.Config;
/*     */ 
/*     */ import com.cyber.mobheads.Main;
/*     */ import com.cyber.mobheads.Utilities.Broadcaster;
/*     */ import com.cyber.mobheads.Utilities.MobMeta;
/*     */ import com.cyber.mobheads.Utilities.MobNames;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.configuration.file.FileConfiguration;
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
/*     */ public class ConfigController
/*     */ {
/*  25 */   private static FileConfiguration CONFIGURATION = Main.getMyConfig();
/*     */ 
/*     */   
/*  28 */   public static void refreshConfig(FileConfiguration newConfig) { CONFIGURATION = newConfig; }
/*     */ 
/*     */ 
/*     */   
/*  32 */   public static boolean supportBabies() { return CONFIGURATION.getBoolean("support_baby_mobs", false); }
/*     */ 
/*     */ 
/*     */   
/*  36 */   public static boolean broadcastEnabledPlayers() { return Main.getMyConfig().getBoolean("player_skull_broadcast_message", false); }
/*     */ 
/*     */ 
/*     */   
/*     */   public static double getDropChancePlayer(int lootingValue) {
/*  41 */     double dropChance = percentageToDouble(CONFIGURATION.getString("Player.dropChance", "0"));
/*  42 */     double lootingMultiplier = percentageToDouble(CONFIGURATION.getString("Player.lootingBonus", "0"));
/*  43 */     return dropChance + lootingValue * lootingMultiplier;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getMessage(String path) {
/*  48 */     String message = CONFIGURATION.getString(path);
/*  49 */     if (message == null) {
/*  50 */       Broadcaster.outputInfoConsole("Could not find message in config file! Try to rebuild the config file if this issue persists!", 2);
/*     */     }
/*  52 */     return ChatColor.translateAlternateColorCodes('&', message);
/*     */   }
/*     */   
/*     */   public static MobMeta getSpecificConfigMobMeta(String inputName) {
/*  56 */     RootItem[] arrayOfRootItem = { new RootItem("ListOfMobs", "lootingBonus"), new RootItem("ListOfFish", "luckOfTheSeaBonus") };
/*     */     
/*  58 */     for (RootItem root : arrayOfRootItem) {
/*  59 */       for (String configName : CONFIGURATION.getConfigurationSection(root.name).getKeys(false)) {
/*     */         
/*  61 */         if (configName.equalsIgnoreCase(inputName)) {
/*  62 */           if (!CONFIGURATION.contains(root.name + "." + configName + ".skullList"))
/*     */           {
/*  64 */             return new MobMeta(MobNames.valueOf(configName), 
/*  65 */                 ChatColor.translateAlternateColorCodes('&', CONFIGURATION.getString(root.name + "." + configName + ".displayName")), CONFIGURATION
/*  66 */                 .getString(root.name + "." + configName + ".random_uuid"), CONFIGURATION
/*  67 */                 .getString(root.name + "." + configName + ".textureCode"), 
/*  68 */                 percentageToDouble(CONFIGURATION.getString(root.name + "." + configName + ".dropChance")), 
/*  69 */                 percentageToDouble(CONFIGURATION.getString(root.name + "." + configName + "." + root.bonus)), CONFIGURATION
/*  70 */                 .getBoolean(root.name + "." + configName + ".broadcastMessage"), CONFIGURATION
/*  71 */                 .getBoolean(root.name + "." + configName + ".dropOnChargedCreeperDeath"), CONFIGURATION
/*  72 */                 .getStringList(root.name + "." + configName + ".advancements"));
/*     */           }
/*     */           
/*  75 */           return null;
/*     */         } 
/*     */ 
/*     */         
/*  79 */         if (CONFIGURATION.contains(root.name + "." + configName + ".skullList")) {
/*     */           
/*  81 */           Set<String> skullList = CONFIGURATION.getConfigurationSection(root.name + "." + configName + ".skullList").getKeys(false);
/*  82 */           for (String listItem : skullList) {
/*  83 */             if (listItem.equalsIgnoreCase(inputName)) {
/*  84 */               return new MobMeta(null, 
/*  85 */                   ChatColor.translateAlternateColorCodes('&', CONFIGURATION.getString(root.name + "." + configName + ".skullList." + listItem + ".displayName")), CONFIGURATION
/*  86 */                   .getString(root.name + "." + configName + ".skullList." + listItem + ".random_uuid"), CONFIGURATION
/*  87 */                   .getString(root.name + "." + configName + ".skullList." + listItem + ".textureCode"), 
/*  88 */                   percentageToDouble(CONFIGURATION.getString(root.name + "." + configName + ".dropChance")), 
/*  89 */                   percentageToDouble(CONFIGURATION.getString(root.name + "." + configName + "." + root.bonus)), CONFIGURATION
/*  90 */                   .getBoolean(root.name + "." + configName + ".broadcastMessage"), CONFIGURATION
/*  91 */                   .getBoolean(root.name + "." + configName + ".dropOnChargedCreeperDeath"), CONFIGURATION
/*  92 */                   .getStringList(root.name + "." + configName + ".skullList." + listItem + ".advancements"));
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  99 */     return null;
/*     */   }
/*     */   
/*     */   public static MobMeta getRandomConfigMobMeta(MobNames mobName, boolean fish) {
/*     */     String bonus, root;
/* 104 */     if (fish) { root = "ListOfFish"; bonus = "luckOfTheSeaBonus"; }
/* 105 */     else { root = "ListOfMobs"; bonus = "lootingBonus"; }
/*     */     
/* 107 */     for (String configName : CONFIGURATION.getConfigurationSection(root).getKeys(false)) {
/* 108 */       if (mobName.name().equalsIgnoreCase(configName)) {
/*     */         
/* 110 */         if (CONFIGURATION.contains(root + "." + configName + ".skullList")) {
/* 111 */           Set<String> skullList = CONFIGURATION.getConfigurationSection(root + "." + configName + ".skullList").getKeys(false);
/*     */ 
/*     */           
/* 114 */           int nr = (new Random()).nextInt(skullList.size());
/* 115 */           String name = (String)skullList.toArray()[nr];
/* 116 */           return new MobMeta(mobName, 
/* 117 */               ChatColor.translateAlternateColorCodes('&', CONFIGURATION.getString(root + "." + configName + ".skullList." + name + ".displayName")), CONFIGURATION
/* 118 */               .getString(root + "." + configName + ".skullList." + name + ".random_uuid"), CONFIGURATION
/* 119 */               .getString(root + "." + configName + ".skullList." + name + ".textureCode"), 
/* 120 */               percentageToDouble(CONFIGURATION.getString(root + "." + configName + ".dropChance")), 
/* 121 */               percentageToDouble(CONFIGURATION.getString(root + "." + configName + "." + bonus)), CONFIGURATION
/* 122 */               .getBoolean(root + "." + configName + ".broadcastMessage"), CONFIGURATION
/* 123 */               .getBoolean(root + "." + configName + ".dropOnChargedCreeperDeath"), CONFIGURATION
/* 124 */               .getStringList(root + "." + configName + ".skullList." + name + ".advancements"));
/*     */         } 
/*     */ 
/*     */         
/* 128 */         return new MobMeta(mobName, 
/* 129 */             ChatColor.translateAlternateColorCodes('&', CONFIGURATION.getString(root + "." + configName + ".displayName")), CONFIGURATION
/* 130 */             .getString(root + "." + configName + ".random_uuid"), CONFIGURATION
/* 131 */             .getString(root + "." + configName + ".textureCode"), 
/* 132 */             percentageToDouble(CONFIGURATION.getString(root + "." + configName + ".dropChance")), 
/* 133 */             percentageToDouble(CONFIGURATION.getString(root + "." + configName + "." + bonus)), CONFIGURATION
/* 134 */             .getBoolean(root + "." + configName + ".broadcastMessage"), CONFIGURATION
/* 135 */             .getBoolean(root + "." + configName + ".dropOnChargedCreeperDeath"), CONFIGURATION
/* 136 */             .getStringList(root + "." + configName + ".advancements"));
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 141 */     Broadcaster.outputInfoConsole("Could not find the " + mobName.name() + " mob in the config file! Try to rebuild your config file, if this won't fix it, report this issue to the developer!", 2);
/* 142 */     return null;
/*     */   }
/*     */   
/*     */   private static double percentageToDouble(String percentage) {
/* 146 */     if (percentage == null) return 0.0D; 
/* 147 */     return Double.parseDouble(percentage.replace(",", ".").replace("%", "")) / 100.0D;
/*     */   }
/*     */   
/*     */   public static String getDisplayNameFromTexture(String texture) {
/* 151 */     String[] roots = { "ListOfMobs", "ListOfFish" };
/*     */     
/* 153 */     for (String root : roots) {
/* 154 */       for (String name : CONFIGURATION.getConfigurationSection(root).getKeys(false)) {
/*     */         
/* 156 */         if (CONFIGURATION.contains(root + "." + name + ".skullList")) {
/* 157 */           Set<String> skullList = CONFIGURATION.getConfigurationSection(root + "." + name + ".skullList").getKeys(false);
/* 158 */           for (String skullListItem : skullList) {
/* 159 */             if (CONFIGURATION.getString(root + "." + name + ".skullList." + skullListItem + ".textureCode").equals(texture))
/* 160 */               return ChatColor.translateAlternateColorCodes('&', CONFIGURATION.getString(root + "." + name + ".skullList." + skullListItem + ".displayName")); 
/*     */           } 
/*     */           continue;
/*     */         } 
/* 164 */         if (CONFIGURATION.getString(root + "." + name + ".textureCode").equals(texture)) {
/* 165 */           return ChatColor.translateAlternateColorCodes('&', CONFIGURATION.getString(root + "." + name + ".displayName"));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 170 */     return null;
/*     */   }
/*     */ 
/*     */   
/* 174 */   public static List<String> getAdvancementsPlayer() { return CONFIGURATION.getStringList("Player.advancements"); }
/*     */ 
/*     */   
/*     */   public static List<String> getAllMobNames() {
/* 178 */     output = new ArrayList();
/*     */     
/* 180 */     String[] roots = { "ListOfMobs", "ListOfFish" };
/*     */     
/* 182 */     for (String root : roots) {
/* 183 */       for (String name : CONFIGURATION.getConfigurationSection(root).getKeys(false)) {
/*     */         
/* 185 */         if (CONFIGURATION.contains(root + "." + name + ".skullList")) {
/* 186 */           Set<String> skullList = CONFIGURATION.getConfigurationSection(root + "." + name + ".skullList").getKeys(false);
/* 187 */           for (String skullListItem : skullList) {
/* 188 */             output.add(skullListItem);
/*     */           }
/*     */           continue;
/*     */         } 
/* 192 */         output.add(name);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 197 */     return output;
/*     */   }
/*     */   
/*     */   public static boolean chargedCreeperDrop(MobNames mobName) {
/* 201 */     if (mobName == null) {
/* 202 */       return false;
/*     */     }
/* 204 */     for (String configName : CONFIGURATION.getConfigurationSection("ListOfMobs").getKeys(false)) {
/* 205 */       if (configName.equalsIgnoreCase(mobName.name())) {
/* 206 */         return CONFIGURATION.getBoolean("ListOfMobs." + configName + ".dropOnChargedCreeperDeath");
/*     */       }
/*     */     } 
/* 209 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Set<String> getSkullList(String mobname) {
/* 214 */     String[] roots = { "ListOfMobs", "ListOfFish" };
/*     */     
/* 216 */     for (String root : roots) {
/* 217 */       for (String name : CONFIGURATION.getConfigurationSection(root).getKeys(false)) {
/* 218 */         if (name.equalsIgnoreCase(mobname) && CONFIGURATION.contains(root + "." + name + ".skullList")) {
/* 219 */           return CONFIGURATION.getConfigurationSection(root + "." + name + ".skullList").getKeys(false);
/*     */         }
/*     */       } 
/*     */     } 
/* 223 */     return null;
/*     */   }
/*     */ 
/*     */   
/* 227 */   public static boolean chargedCreeperDropForPlayer() { return CONFIGURATION.getBoolean("Player.dropOnChargedCreeperDeath", false); }
/*     */ 
/*     */ 
/*     */   
/* 231 */   public static boolean surpressWarnings() { return CONFIGURATION.getBoolean("surpress_warning", false); }
/*     */ 
/*     */ 
/*     */   
/* 235 */   public static boolean surpressErrors() { return CONFIGURATION.getBoolean("surpress_error", false); }
/*     */ 
/*     */ 
/*     */   
/* 239 */   public static boolean dropOutsideInventory() { return CONFIGURATION.getBoolean("drop_player_heads_outside_inventory", false); }
/*     */ 
/*     */ 
/*     */   
/* 243 */   public static boolean allow_self_player_head_farming() { return CONFIGURATION.getBoolean("allow_self_player_head_farming", false); }
/*     */   
/*     */   public static class RootItem
/*     */   {
/*     */     private String name;
/*     */     private String bonus;
/*     */     
/*     */     public RootItem(String n, String b) {
/* 251 */       this.name = n;
/* 252 */       this.bonus = b;
/*     */     }
/*     */ 
/*     */     
/* 256 */     public String getName() { return this.name; }
/*     */ 
/*     */ 
/*     */     
/* 260 */     public String getBonus() { return this.bonus; }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void configurationCheck() {
/* 267 */     if (!CONFIGURATION.contains("support_baby_mobs") || 
/* 268 */       !CONFIGURATION.contains("player_skull_broadcast_message") || 
/* 269 */       !CONFIGURATION.contains("Player.dropChance") || 
/* 270 */       !CONFIGURATION.contains("Player.lootingBonus") || 
/* 271 */       !CONFIGURATION.contains("Player.advancements") || 
/* 272 */       !CONFIGURATION.contains("Player.dropOnChargedCreeperDeath") || 
/* 273 */       !CONFIGURATION.contains("surpress_warning") || 
/* 274 */       !CONFIGURATION.contains("surpress_error") || 
/* 275 */       !CONFIGURATION.contains("drop_player_heads_outside_inventory") || 
/* 276 */       !CONFIGURATION.contains("allow_self_player_head_farming"))
/*     */     {
/* 278 */       Broadcaster.outputInfoConsole("Missing configuration values! Please update your configuration file!", 2);
/*     */     }
/*     */   }
/*     */ }


/* Location:              F:\Minecraft Servers\SpigotLobby\plugins\MobHeads 2.4.jar!\com\cyber\mobheads\Config\ConfigController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.7
 */