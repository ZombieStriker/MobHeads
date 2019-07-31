/*    */ package com.cyber.mobheads.Utilities;
/*    */ 
/*    */ import com.cyber.mobheads.Config.ConfigController;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Broadcaster
/*    */ {
/*    */   public static void broadCastMobHead(Player player, String displayName) {
/* 21 */     String configMessage = ConfigController.getMessage("Messages.Success.Broadcast-message-mob");
/*    */     
/* 23 */     if (!configMessage.isEmpty()) {
/* 24 */       Bukkit.broadcastMessage(configMessage
/* 25 */           .replace("[playername]", player.getName())
/* 26 */           .replace("[mobname]", displayName));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public static void broadCastPlayerHead(Player killer, Player victim) {
/* 32 */     if (!ConfigController.broadcastEnabledPlayers()) {
/*    */       return;
/*    */     }
/*    */     
/* 36 */     String configMessage = ConfigController.getMessage("Messages.Success.Broadcast-message-player");
/*    */ 
/*    */     
/* 39 */     if (!configMessage.isEmpty()) {
/* 40 */       Bukkit.broadcastMessage(configMessage
/* 41 */           .replace("[killer]", killer.getName())
/* 42 */           .replace("[victim]", victim.getName()));
/*    */     }
/*    */   }
/*    */   
/*    */   public static void outputInfoConsole(String message, int urgency) {
/* 47 */     ChatColor green = ChatColor.GREEN;
/* 48 */     ChatColor yellow = ChatColor.YELLOW;
/* 49 */     ChatColor red = ChatColor.RED;
/*    */     
/* 51 */     if (urgency <= 0) {
/* 52 */       Bukkit.getServer().getConsoleSender().sendMessage(green + "[Mob Heads] " + message);
/*    */     }
/* 54 */     else if (urgency == 1 && !ConfigController.surpressWarnings()) {
/* 55 */       Bukkit.getServer().getConsoleSender().sendMessage(yellow + "[Mob Heads] " + message);
/*    */     }
/* 57 */     else if (urgency >= 2 && !ConfigController.surpressErrors()) {
/* 58 */       Bukkit.getServer().getConsoleSender().sendMessage(red + "[Mob Heads] " + message);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              F:\Minecraft Servers\SpigotLobby\plugins\MobHeads 2.4.jar!\com\cyber\mobheads\Utilities\Broadcaster.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.7
 */