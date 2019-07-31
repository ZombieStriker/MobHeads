/*     */ package com.cyber.mobheads.Utilities;
/*     */ 
/*     */ import com.cyber.mobheads.Config.ConfigController;
/*     */ import com.cyber.mobheads.advancements.AdvancementsManager;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.authlib.properties.Property;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.UUID;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.inventory.meta.SkullMeta;
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
/*     */ public class SkullFactory
/*     */ {
/*     */   public ItemStack getMobSkull(MobMeta mobmeta, Player owner) {
/*  31 */     String encodedTexture = mobmeta.getTextureCode();
/*  32 */     String randomUUID = mobmeta.getUUID();
/*  33 */     String displayName = mobmeta.getDisplayName();
/*     */     
/*  35 */     if (encodedTexture == null) {
/*  36 */       return null;
/*     */     }
/*  38 */     if (encodedTexture.equalsIgnoreCase("[vanilla]") || randomUUID.equalsIgnoreCase("[vanilla]") || displayName.equalsIgnoreCase("[vanilla]")) {
/*     */       
/*  40 */       if (owner != null) {
/*  41 */         AdvancementsManager.triggerAdvancement(owner, mobmeta.getAdvancements());
/*     */       }
/*     */       
/*  44 */       if (displayName.equalsIgnoreCase("[vanilla]")) {
/*  45 */         return getVanillaSkull(mobmeta.getMobName());
/*     */       }
/*     */ 
/*     */       
/*  49 */       ItemStack vanillaSkull = getVanillaSkull(mobmeta.getMobName());
/*  50 */       ItemMeta meta = vanillaSkull.getItemMeta();
/*  51 */       meta.setDisplayName(ChatColor.RESET + displayName);
/*  52 */       vanillaSkull.setItemMeta(meta);
/*  53 */       return vanillaSkull;
/*     */     } 
/*     */ 
/*     */     
/*  57 */     SkullMeta meta = (SkullMeta)Bukkit.getItemFactory().getItemMeta(Material.PLAYER_HEAD);
/*     */ 
/*     */     
/*  60 */     GameProfile profile = new GameProfile(UUID.fromString(randomUUID), null);
/*     */     
/*  62 */     profile.getProperties().put("textures", new Property("textures", encodedTexture));
/*  63 */     Field profileField = null;
/*     */     try {
/*  65 */       profileField = meta.getClass().getDeclaredField("profile");
/*  66 */       profileField.setAccessible(true);
/*  67 */       profileField.set(meta, profile);
/*  68 */     } catch (NoSuchFieldException|IllegalArgumentException|IllegalAccessException e1) {
/*  69 */       e1.printStackTrace();
/*     */     } 
/*     */     
/*  72 */     ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1, (short)3);
/*     */     
/*  74 */     meta.setDisplayName(ChatColor.RESET + displayName);
/*     */ 
/*     */     
/*  77 */     if (owner != null) {
/*  78 */       AdvancementsManager.triggerAdvancement(owner, mobmeta.getAdvancements());
/*     */     }
/*     */     
/*  81 */     skull.setItemMeta(meta);
/*  82 */     return skull;
/*     */   }
/*     */   
/*     */   public ItemStack getPlayerSkull(String playername, Player killer) {
/*  86 */     ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1);
/*  87 */     ItemMeta itemMeta = item.getItemMeta();
/*  88 */     ((SkullMeta)itemMeta).setOwner(playername);
/*  89 */     item.setItemMeta(itemMeta);
/*  90 */     if (killer != null) AdvancementsManager.triggerAdvancement(killer, ConfigController.getAdvancementsPlayer()); 
/*  91 */     return item;
/*     */   } public ItemStack getVanillaSkull(MobNames mobname) {
/*     */     Material mat;
/*     */     Material mat;
/*     */     Material mat;
/*     */     Material mat;
/*  97 */     switch (mobname) {
/*     */       case Ender_Dragon:
/*  99 */         mat = Material.DRAGON_HEAD;
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
/* 110 */         return new ItemStack(mat, 1);case Zombie: mat = Material.ZOMBIE_HEAD; return new ItemStack(mat, 1);case Skeleton: mat = Material.SKELETON_SKULL; return new ItemStack(mat, 1);case Creeper: mat = Material.CREEPER_HEAD; return new ItemStack(mat, 1);
/*     */     } 
/*     */     return null;
/*     */   }
/*     */ }


/* Location:              F:\Minecraft Servers\SpigotLobby\plugins\MobHeads 2.4.jar!\com\cyber\mobheads\Utilities\SkullFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.7
 */