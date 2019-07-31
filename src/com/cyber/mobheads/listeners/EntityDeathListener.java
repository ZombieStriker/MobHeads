/*     */ package com.cyber.mobheads.listeners;
/*     */ 
/*     */ import com.cyber.mobheads.Config.ConfigController;
/*     */ import com.cyber.mobheads.Utilities.Broadcaster;
/*     */ import com.cyber.mobheads.Utilities.MobMeta;
/*     */ import com.cyber.mobheads.Utilities.MobNames;
/*     */ import com.cyber.mobheads.Utilities.SkullFactory;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import java.util.UUID;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.entity.Ageable;
/*     */ import org.bukkit.entity.Creeper;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.EntityType;
/*     */ import org.bukkit.entity.LivingEntity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.entity.EntityDamageByEntityEvent;
/*     */ import org.bukkit.event.entity.EntityDeathEvent;
/*     */ import org.bukkit.inventory.ItemStack;
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
/*     */ 
/*     */ 
/*     */ public class EntityDeathListener
/*     */   implements Listener
/*     */ {
/*  42 */   private final List<UUID> killedByChargedCreeper = new ArrayList();
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onChargedCreeperDeath(EntityDamageByEntityEvent event) {
/*  48 */     if (event.getEntity() instanceof LivingEntity) {
/*  49 */       LivingEntity le = (LivingEntity)event.getEntity();
/*  50 */       if (le.getHealth() - event.getDamage() <= 0.0D && 
/*  51 */         event.getDamager() instanceof Creeper && (
/*  52 */         (Creeper)event.getDamager()).isPowered()) {
/*     */         
/*  54 */         MobNames mobName = MobNames.getName(event.getEntity());
/*  55 */         if ((mobName != null && ConfigController.chargedCreeperDrop(mobName)) || (event
/*  56 */           .getEntity() instanceof Player && ConfigController.chargedCreeperDropForPlayer())) {
/*  57 */           this.killedByChargedCreeper.add(event.getEntity().getUniqueId());
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.MONITOR)
/*     */   public void onEntityDeath(EntityDeathEvent event) {
/*  68 */     LivingEntity livingEntity = event.getEntity();
/*  69 */     Player killer = event.getEntity().getKiller();
/*     */ 
/*     */     
/*     */     try {
/*  73 */       if (!ConfigController.supportBabies() && !((Ageable)livingEntity).isAdult())
/*     */       {
/*     */         return;
/*     */       }
/*     */     }
/*  78 */     catch (ClassCastException classCastException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  83 */     if (this.killedByChargedCreeper.remove(livingEntity.getUniqueId())) {
/*     */       ItemStack skull;
/*  85 */       if (livingEntity instanceof Player) {
/*  86 */         skull = getPlayerHead((Player)livingEntity, null, true);
/*     */       } else {
/*     */         
/*  89 */         skull = getMobHead(killer, livingEntity, true);
/*     */       } 
/*  91 */       event.getDrops().add(skull);
/*     */       
/*     */       return;
/*     */     } 
/*  95 */     if (killer == null) {
/*     */       return;
/*     */     }
/*     */     
/*  99 */     if (livingEntity.getUniqueId().equals(killer.getUniqueId()) && 
/* 100 */       !ConfigController.allow_self_player_head_farming()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 106 */     if (livingEntity instanceof Player) {
/*     */       
/* 108 */       ItemStack playerSkull = getPlayerHead((Player)livingEntity, killer, false);
/* 109 */       if (ConfigController.dropOutsideInventory()) {
/* 110 */         event.getEntity().getWorld().dropItem(event.getEntity().getLocation(), playerSkull);
/*     */       } else {
/*     */         
/* 113 */         event.getDrops().add(playerSkull);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 118 */       event.getDrops().add(getMobHead(killer, livingEntity, false));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private ItemStack getMobHead(Player killer, Entity mob, boolean forceDrop) {
/* 125 */     if (!forceDrop && !killer.hasPermission("com.cyber.mobheads.behead.mobs")) {
/* 126 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 130 */     MobNames mobName = MobNames.getName(mob);
/*     */     
/* 132 */     if (mobName == null) {
/* 133 */       if (mob.getType().equals(EntityType.WITHER_SKELETON)) {
/* 134 */         return null;
/*     */       }
/*     */       
/* 137 */       Broadcaster.outputInfoConsole("This mob is not supported, please update!", 1);
/* 138 */       return null;
/*     */     } 
/*     */ 
/*     */     
/* 142 */     MobMeta mobmeta = ConfigController.getRandomConfigMobMeta(mobName, false);
/*     */     
/* 144 */     if (!forceDrop) {
/*     */       
/* 146 */       int lootingValue = killer.getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS);
/* 147 */       double dropBonus = mobmeta.getDropBonus();
/* 148 */       double dropChance = mobmeta.getDropChance();
/*     */       
/* 150 */       double chance = dropChance + lootingValue * dropBonus;
/*     */ 
/*     */       
/* 153 */       if (!willDrop(chance)) {
/* 154 */         return null;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 159 */     ItemStack skull = (new SkullFactory()).getMobSkull(mobmeta, killer);
/*     */     
/* 161 */     if (mobmeta.isShouldBroadcast() && !forceDrop) {
/* 162 */       String name = mobmeta.getDisplayName();
/* 163 */       if (name == null) {
/* 164 */         name = getVanillaName(mob);
/*     */       }
/* 166 */       Broadcaster.broadCastMobHead(killer, name);
/*     */     } 
/* 168 */     return skull;
/*     */   }
/*     */ 
/*     */   
/*     */   private ItemStack getPlayerHead(Player deadPlayer, Player killer, boolean forceDrop) {
/* 173 */     if (forceDrop) {
/* 174 */       return (new SkullFactory()).getPlayerSkull(deadPlayer.getName(), killer);
/*     */     }
/*     */     
/* 177 */     if (!killer.hasPermission("com.cyber.mobheads.behead.players")) {
/* 178 */       return null;
/*     */     }
/*     */     
/* 181 */     int lootingValue = killer.getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS);
/* 182 */     double dropChance = ConfigController.getDropChancePlayer(lootingValue);
/*     */     
/* 184 */     if (willDrop(dropChance)) {
/* 185 */       Broadcaster.broadCastPlayerHead(killer, deadPlayer);
/* 186 */       return (new SkullFactory()).getPlayerSkull(deadPlayer.getName(), killer);
/*     */     } 
/* 188 */     return null;
/*     */   }
/*     */   
/*     */   private boolean willDrop(double dropChance) {
/* 192 */     double randomDouble = (new Random()).nextDouble();
/* 193 */     return (randomDouble <= dropChance);
/*     */   }
/*     */   
/*     */   private String getVanillaName(Entity entity) {
/* 197 */     switch (entity.getType()) {
/*     */       case SKELETON:
/* 199 */         return "Skeleton Head";
/*     */       case CREEPER:
/* 201 */         return "Creeper Head";
/*     */       case ZOMBIE:
/* 203 */         return "Zombie Head";
/*     */       case ENDER_DRAGON:
/* 205 */         return "Dragon Head";
/*     */     } 
/* 207 */     return null;
/*     */   }
/*     */ }


/* Location:              F:\Minecraft Servers\SpigotLobby\plugins\MobHeads 2.4.jar!\com\cyber\mobheads\listeners\EntityDeathListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.7
 */