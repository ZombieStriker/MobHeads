/*    */ package com.cyber.mobheads.listeners;
/*    */ 
/*    */ import com.cyber.mobheads.Config.ConfigController;
/*    */ import com.cyber.mobheads.Utilities.Broadcaster;
/*    */ import com.cyber.mobheads.Utilities.MobMeta;
/*    */ import com.cyber.mobheads.Utilities.MobNames;
/*    */ import com.cyber.mobheads.Utilities.SkullFactory;
/*    */ import java.util.Random;
/*    */ import org.bukkit.enchantments.Enchantment;
/*    */ import org.bukkit.entity.Item;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.player.PlayerFishEvent;
/*    */ import org.bukkit.inventory.ItemStack;
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
/*    */ public class FishListener
/*    */   implements Listener
/*    */ {
/*    */   @EventHandler
/*    */   public void fishCaught(PlayerFishEvent event) {
/* 30 */     if (!event.getPlayer().hasPermission("com.cyber.mobheads.behead.fish")) {
/*    */       return;
/*    */     }
/*    */     
/* 34 */     if (event.getState().equals(PlayerFishEvent.State.CAUGHT_FISH)) {
/* 35 */       MobNames mobname = MobNames.getName(event.getCaught());
/*    */       
/* 37 */       if (mobname == null) {
/*    */         return;
/*    */       }
/*    */       
/* 41 */       MobMeta mobmeta = ConfigController.getRandomConfigMobMeta(mobname, true);
/*    */ 
/*    */       
/* 44 */       int luckOfTheSeaLevel = event.getPlayer().getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.LUCK);
/* 45 */       double dropBonus = mobmeta.getDropBonus();
/* 46 */       double dropChance = mobmeta.getDropChance();
/*    */       
/* 48 */       double chance = dropChance + luckOfTheSeaLevel * dropBonus;
/*    */ 
/*    */       
/* 51 */       if (willDrop(chance)) {
/*    */         
/* 53 */         ItemStack skull = (new SkullFactory()).getMobSkull(mobmeta, event.getPlayer());
/*    */         
/* 55 */         Item fish = (Item)event.getCaught();
/* 56 */         Item skullItem = fish.getWorld().dropItem(fish.getLocation(), skull);
/* 57 */         skullItem.setVelocity(fish.getVelocity());
/*    */         
/* 59 */         event.getCaught().setPassenger(skullItem);
/*    */         
/* 61 */         if (mobmeta.isShouldBroadcast()) {
/* 62 */           Broadcaster.broadCastMobHead(event.getPlayer(), mobmeta.getDisplayName());
/*    */         }
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   private boolean willDrop(double dropChance) {
/* 69 */     double randomDouble = (new Random()).nextDouble();
/* 70 */     return (randomDouble <= dropChance);
/*    */   }
/*    */ }


/* Location:              F:\Minecraft Servers\SpigotLobby\plugins\MobHeads 2.4.jar!\com\cyber\mobheads\listeners\FishListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.7
 */