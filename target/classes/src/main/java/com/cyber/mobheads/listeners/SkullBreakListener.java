/*    */ package com.cyber.mobheads.listeners;
/*    */ 
/*    */ import com.cyber.mobheads.Config.ConfigController;
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import com.mojang.authlib.properties.Property;
/*    */ import com.mojang.authlib.properties.PropertyMap;
/*    */ import java.lang.reflect.Field;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.entity.ItemSpawnEvent;
/*    */ import org.bukkit.inventory.meta.ItemMeta;
/*    */ import org.bukkit.inventory.meta.SkullMeta;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SkullBreakListener
/*    */   implements Listener
/*    */ {
/*    */   @EventHandler
/*    */   public void onSkullDrop(ItemSpawnEvent event) {
/* 29 */     if (event.getEntity().getItemStack().getType() != Material.PLAYER_HEAD) {
/*    */       return;
/*    */     }
/*    */ 
/*    */ 
/*    */     
/* 35 */     SkullMeta meta = (SkullMeta)event.getEntity().getItemStack().getItemMeta();
/*    */     
/* 37 */     if (meta.hasLocalizedName()) {
/*    */       return;
/*    */     }
/*    */ 
/*    */ 
/*    */     
/* 43 */     GameProfile profile = null;
/* 44 */     Field profileField = null;
/*    */     try {
/* 46 */       profileField = meta.getClass().getDeclaredField("profile");
/* 47 */       profileField.setAccessible(true);
/* 48 */       profile = (GameProfile)profileField.get(meta);
/* 49 */     } catch (NoSuchFieldException|IllegalArgumentException|IllegalAccessException e1) {
/* 50 */       e1.printStackTrace();
/*    */     } 
/*    */     
/* 53 */     if (profile == null) {
/*    */       return;
/*    */     }
/*    */ 
/*    */     
/* 58 */     PropertyMap map = profile.getProperties();
/*    */ 
/*    */     
/* 61 */     String code = null;
/*    */     try {
/* 63 */       code = ((Property)map.get("textures").toArray()[0]).getValue();
/*    */     }
/* 65 */     catch (ArrayIndexOutOfBoundsException e) {
/*    */       return;
/*    */     } 
/*    */ 
/*    */     
/* 70 */     if (code == null) {
/*    */       return;
/*    */     }
/*    */ 
/*    */ 
/*    */     
/* 76 */     String displayName = ConfigController.getDisplayNameFromTexture(code);
/*    */     
/* 78 */     if (displayName == null) {
/*    */       return;
/*    */     }
/*    */ 
/*    */     
/* 83 */     ItemMeta itemMeta = event.getEntity().getItemStack().getItemMeta();
/* 84 */     itemMeta.setDisplayName(ChatColor.RESET + displayName);
/* 85 */     event.getEntity().getItemStack().setItemMeta(itemMeta);
/*    */   }
/*    */ }


/* Location:              F:\Minecraft Servers\SpigotLobby\plugins\MobHeads 2.4.jar!\com\cyber\mobheads\listeners\SkullBreakListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.7
 */