/*    */ package com.cyber.mobheads.Utilities;
/*    */ 
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MobMeta
/*    */ {
/*    */   private final MobNames mobName;
/*    */   private final String displayName;
/*    */   private final String UUID;
/*    */   private final String textureCode;
/*    */   private final double dropChance;
/*    */   private final double dropBonus;
/*    */   private final boolean shouldBroadcast;
/*    */   private final boolean chargedCreeperDrop;
/*    */   private final List<String> advancements;
/*    */   
/*    */   public MobMeta(MobNames mobName, String displayName, String UUID, String textureCode, double dropChance, double dropBonus, boolean shouldBroadcast, boolean chargedCreeperDrop, List<String> advancements) {
/* 26 */     this.mobName = mobName;
/* 27 */     this.displayName = displayName;
/* 28 */     this.UUID = UUID;
/* 29 */     this.textureCode = textureCode;
/* 30 */     this.dropChance = dropChance;
/* 31 */     this.dropBonus = dropBonus;
/* 32 */     this.shouldBroadcast = shouldBroadcast;
/* 33 */     this.chargedCreeperDrop = chargedCreeperDrop;
/* 34 */     this.advancements = advancements;
/*    */   }
/*    */ 
/*    */   
/* 38 */   public MobNames getMobName() { return this.mobName; }
/*    */ 
/*    */ 
/*    */   
/* 42 */   public String getDisplayName() { return this.displayName; }
/*    */ 
/*    */ 
/*    */   
/* 46 */   public String getUUID() { return this.UUID; }
/*    */ 
/*    */ 
/*    */   
/* 50 */   public String getTextureCode() { return this.textureCode; }
/*    */ 
/*    */ 
/*    */   
/* 54 */   public double getDropChance() { return this.dropChance; }
/*    */ 
/*    */ 
/*    */   
/* 58 */   public double getDropBonus() { return this.dropBonus; }
/*    */ 
/*    */ 
/*    */   
/* 62 */   public boolean isShouldBroadcast() { return this.shouldBroadcast; }
/*    */ 
/*    */ 
/*    */   
/* 66 */   public boolean isChargedCreeperDrop() { return this.chargedCreeperDrop; }
/*    */ 
/*    */ 
/*    */   
/* 70 */   public List<String> getAdvancements() { return this.advancements; }
/*    */ }


/* Location:              F:\Minecraft Servers\SpigotLobby\plugins\MobHeads 2.4.jar!\com\cyber\mobheads\Utilities\MobMeta.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.7
 */