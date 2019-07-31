/*    */ package com.cyber.mobheads.advancements;
/*    */ 
/*    */ import com.cyber.mobheads.Main;
/*    */ import java.util.List;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.NamespacedKey;
/*    */ import org.bukkit.advancement.Advancement;
/*    */ import org.bukkit.advancement.AdvancementProgress;
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
/*    */ 
/*    */ 
/*    */ public class AdvancementsManager
/*    */ {
/*    */   public static void triggerAdvancement(Player player, List<String> advancementNames) {
/* 26 */     for (String fullAdvName : advancementNames) {
/* 27 */       fullAdvName = fullAdvName.replace("mobheads:", "");
/* 28 */       String advName = fullAdvName.split(" ")[0];
/* 29 */       String criteriaName = fullAdvName.split(" ")[1];
/*    */       
/* 31 */       NamespacedKey key = new NamespacedKey(Main.getPlugin(), advName);
/* 32 */       Advancement advancement = Bukkit.getAdvancement(key);
/* 33 */       if (advancement == null) {
/*    */         return;
/*    */       }
/*    */       
/* 37 */       AdvancementProgress progress = player.getAdvancementProgress(advancement);
/* 38 */       progress.awardCriteria(criteriaName);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              F:\Minecraft Servers\SpigotLobby\plugins\MobHeads 2.4.jar!\com\cyber\mobheads\advancements\AdvancementsManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.7
 */