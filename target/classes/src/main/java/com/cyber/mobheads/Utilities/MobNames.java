/*     */ package com.cyber.mobheads.Utilities;
/*     */ 
/*     */ import org.bukkit.DyeColor;
/*     */ import org.bukkit.entity.Creeper;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.EntityType;
/*     */ import org.bukkit.entity.Horse;
/*     */ import org.bukkit.entity.Llama;
/*     */ import org.bukkit.entity.Ocelot;
/*     */ import org.bukkit.entity.Parrot;
/*     */ import org.bukkit.entity.Rabbit;
/*     */ import org.bukkit.entity.Sheep;
/*     */ import org.bukkit.entity.Villager;
/*     */ import org.bukkit.entity.Wolf;
/*     */ import org.bukkit.entity.ZombieVillager;
/*     */ 
/*     */ public static enum MobNames
/*     */ {
/*  19 */   Bat,
/*  20 */   Blaze,
/*  21 */   Black_Cat,
/*  22 */   Ginger_Cat,
/*  23 */   Siamese_Cat,
/*  24 */   Cave_Spider,
/*  25 */   Chicken,
/*  26 */   Cow,
/*  27 */   Creeper,
/*  28 */   Charged_Creeper,
/*  29 */   Dolphin,
/*  30 */   Donkey,
/*  31 */   Drowned,
/*  32 */   Elder_Guardian,
/*  33 */   Enderman,
/*  34 */   Endermite,
/*  35 */   Ender_Dragon,
/*  36 */   Evoker,
/*  37 */   Ghast,
/*  38 */   Guardian,
/*  39 */   Black_Horse,
/*  40 */   Brown_Horse,
/*  41 */   Chestnut_Horse,
/*  42 */   Creamy_Horse,
/*  43 */   Dark_Brown_Horse,
/*  44 */   Gray_Horse,
/*  45 */   White_Horse,
/*  46 */   Skeleton_Horse,
/*  47 */   Zombie_Horse,
/*  48 */   Husk,
/*  49 */   Illusioner,
/*  50 */   Iron_Golem,
/*  51 */   Brown_Llama,
/*  52 */   Creamy_Llama,
/*  53 */   Gray_Llama,
/*  54 */   White_Llama,
/*  55 */   Magma_Cube,
/*  56 */   Mooshroom,
/*  57 */   Mule,
/*  58 */   Ocelot,
/*  59 */   Blue_Parrot,
/*  60 */   Cyan_Parrot,
/*  61 */   Gray_Parrot,
/*  62 */   Green_Parrot,
/*  63 */   Red_Parrot,
/*  64 */   Phantom,
/*  65 */   Pig,
/*  66 */   Polar_Bear,
/*  67 */   Black_Rabbit,
/*  68 */   Black_and_White_Rabbit,
/*  69 */   Brown_Rabbit,
/*  70 */   Gold_Rabbit,
/*  71 */   Salt_and_Pepper_Rabbit,
/*  72 */   The_Killer_Bunny,
/*  73 */   Toast_Rabbit,
/*  74 */   White_Rabbit,
/*  75 */   Black_Sheep,
/*  76 */   Blue_Sheep,
/*  77 */   Brown_Sheep,
/*  78 */   Cyan_Sheep,
/*  79 */   Gray_Sheep,
/*  80 */   Green_Sheep,
/*  81 */   Light_Blue_Sheep,
/*  82 */   Lime_Sheep,
/*  83 */   Magenta_Sheep,
/*  84 */   Orange_Sheep,
/*  85 */   Pink_Sheep,
/*  86 */   Purple_Sheep,
/*  87 */   Rainbow_Sheep,
/*  88 */   Red_Sheep,
/*  89 */   Light_Gray_Sheep,
/*  90 */   White_Sheep,
/*  91 */   Yellow_Sheep,
/*  92 */   Shulker,
/*  93 */   Silverfish,
/*  94 */   Skeleton,
/*  95 */   Slime,
/*  96 */   Snow_Golem,
/*  97 */   Spider,
/*  98 */   Squid,
/*  99 */   Stray,
/* 100 */   Turtle,
/* 101 */   Vex,
/* 102 */   Villager,
/* 103 */   Vindicator,
/* 104 */   Witch,
/* 105 */   Wither,
/* 106 */   Wild_Wolf,
/* 107 */   Tamed_Wolf,
/* 108 */   Zombie,
/* 109 */   Zombie_Pigman,
/* 110 */   Zombie_Butcher_Villager,
/* 111 */   Zombie_Cleric_Villager,
/* 112 */   Zombie_Farmer_Villager,
/* 113 */   Zombie_Librarian_Villager,
/* 114 */   Zombie_Nitwit_Villager,
/* 115 */   Zombie_Smith_Villager,
/*     */   
/* 117 */   Tropical_Fish,
/* 118 */   Cod,
/* 119 */   Salmon,
/* 120 */   Pufferfish;
/*     */ 
/*     */ 
/*     */   
/*     */   public static MobNames getName(Entity entity) {
/* 125 */     switch (entity.getType()) {
/*     */       case BLACKSMITH:
/* 127 */         return Bat;
/*     */       case BUTCHER:
/* 129 */         return Blaze;
/*     */       case FARMER:
/* 131 */         return getCatName((Ocelot)entity);
/*     */       case LIBRARIAN:
/* 133 */         return Cave_Spider;
/*     */       case NITWIT:
/* 135 */         return Chicken;
/*     */       case PRIEST:
/* 137 */         return Cow;
/*     */       case null:
/* 139 */         return getCreeperName((Creeper)entity);
/*     */       case null:
/* 141 */         return Dolphin;
/*     */       case null:
/* 143 */         return Donkey;
/*     */       case null:
/* 145 */         return Drowned;
/*     */       case null:
/* 147 */         return Elder_Guardian;
/*     */       case null:
/* 149 */         return Enderman;
/*     */       case null:
/* 151 */         return Endermite;
/*     */       case null:
/* 153 */         return Ender_Dragon;
/*     */       case null:
/* 155 */         return Evoker;
/*     */       case null:
/* 157 */         return Ghast;
/*     */       case null:
/* 159 */         return Guardian;
/*     */       case null:
/* 161 */         return getHorseName((Horse)entity);
/*     */       case null:
/* 163 */         return Zombie_Horse;
/*     */       case null:
/* 165 */         return Skeleton_Horse;
/*     */       case null:
/* 167 */         return Husk;
/*     */       case null:
/* 169 */         return Illusioner;
/*     */       case null:
/* 171 */         return Iron_Golem;
/*     */       case null:
/* 173 */         return getLLamaName((Llama)entity);
/*     */       case null:
/* 175 */         return Magma_Cube;
/*     */       case null:
/* 177 */         return Mooshroom;
/*     */       case null:
/* 179 */         return Mule;
/*     */       case null:
/* 181 */         return getParrotName((Parrot)entity);
/*     */       case null:
/* 183 */         return Phantom;
/*     */       case null:
/* 185 */         return Pig;
/*     */       case null:
/* 187 */         return Polar_Bear;
/*     */       case null:
/* 189 */         return getRabbitName((Rabbit)entity);
/*     */       case null:
/* 191 */         return getSheepName((Sheep)entity);
/*     */       case null:
/* 193 */         return Shulker;
/*     */       case null:
/* 195 */         return Silverfish;
/*     */       case null:
/* 197 */         return Skeleton;
/*     */       case null:
/* 199 */         return Slime;
/*     */       case null:
/* 201 */         return Snow_Golem;
/*     */       case null:
/* 203 */         return Spider;
/*     */       case null:
/* 205 */         return Squid;
/*     */       case null:
/* 207 */         return Stray;
/*     */       case null:
/* 209 */         return Turtle;
/*     */       case null:
/* 211 */         return Vex;
/*     */       case null:
/* 213 */         return Villager;
/*     */       case null:
/* 215 */         return Vindicator;
/*     */       case null:
/* 217 */         return Witch;
/*     */       case null:
/* 219 */         return Wither;
/*     */       case null:
/* 221 */         return getWolfName((Wolf)entity);
/*     */       case null:
/* 223 */         return Zombie;
/*     */       case null:
/* 225 */         return Zombie_Pigman;
/*     */       case null:
/* 227 */         return getZombieVillagerName((ZombieVillager)entity);
/*     */       case null:
/* 229 */         return Cod;
/*     */       case null:
/* 231 */         return Pufferfish;
/*     */       case null:
/* 233 */         return Salmon;
/*     */       case null:
/* 235 */         return Tropical_Fish;
/*     */       case null:
/* 237 */         return getFishName(entity);
/*     */     } 
/* 239 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static MobNames getCatName(Ocelot ocelot) {
/* 246 */     if (ocelot.getCatType() == null) {
/* 247 */       return null;
/*     */     }
/*     */     
/* 250 */     switch (ocelot.getCatType()) {
/*     */       case BLACKSMITH:
/* 252 */         return Black_Cat;
/*     */       case BUTCHER:
/* 254 */         return Ginger_Cat;
/*     */       case FARMER:
/* 256 */         return Siamese_Cat;
/*     */       case LIBRARIAN:
/* 258 */         return Ocelot;
/*     */     } 
/* 260 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static MobNames getCreeperName(Creeper creeper) {
/* 267 */     if (creeper.isPowered()) {
/* 268 */       return Charged_Creeper;
/*     */     }
/*     */     
/* 271 */     return Creeper;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static MobNames getHorseName(Horse horse) {
/* 278 */     if (horse.getColor() == null) {
/* 279 */       return null;
/*     */     }
/*     */     
/* 282 */     switch (horse.getColor()) {
/*     */       case BLACKSMITH:
/* 284 */         return Black_Horse;
/*     */       case BUTCHER:
/* 286 */         return Brown_Horse;
/*     */       case FARMER:
/* 288 */         return Chestnut_Horse;
/*     */       case LIBRARIAN:
/* 290 */         return Creamy_Horse;
/*     */       case NITWIT:
/* 292 */         return Dark_Brown_Horse;
/*     */       case PRIEST:
/* 294 */         return Gray_Horse;
/*     */       case null:
/* 296 */         return White_Horse;
/*     */     } 
/* 298 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static MobNames getLLamaName(Llama llama) {
/* 305 */     if (llama.getColor() == null) {
/* 306 */       return null;
/*     */     }
/*     */     
/* 309 */     switch (llama.getColor()) {
/*     */       case BLACKSMITH:
/* 311 */         return Brown_Llama;
/*     */       case BUTCHER:
/* 313 */         return Creamy_Llama;
/*     */       case FARMER:
/* 315 */         return Gray_Llama;
/*     */       case LIBRARIAN:
/* 317 */         return White_Llama;
/*     */     } 
/* 319 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static MobNames getParrotName(Parrot parrot) {
/* 326 */     if (parrot.getVariant() == null) {
/* 327 */       return null;
/*     */     }
/*     */     
/* 330 */     switch (parrot.getVariant()) {
/*     */       case BLACKSMITH:
/* 332 */         return Blue_Parrot;
/*     */       case BUTCHER:
/* 334 */         return Cyan_Parrot;
/*     */       case FARMER:
/* 336 */         return Gray_Parrot;
/*     */       case LIBRARIAN:
/* 338 */         return Green_Parrot;
/*     */       case NITWIT:
/* 340 */         return Red_Parrot;
/*     */     } 
/* 342 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static MobNames getRabbitName(Rabbit rabbit) {
/* 348 */     if (rabbit.getCustomName() != null && rabbit.getName().equals("Toast")) {
/* 349 */       return Toast_Rabbit;
/*     */     }
/*     */     
/* 352 */     if (rabbit.getRabbitType() == null) {
/* 353 */       return null;
/*     */     }
/*     */     
/* 356 */     switch (rabbit.getRabbitType()) {
/*     */       case BLACKSMITH:
/* 358 */         return Black_Rabbit;
/*     */       case BUTCHER:
/* 360 */         return Black_and_White_Rabbit;
/*     */       case FARMER:
/* 362 */         return Brown_Rabbit;
/*     */       case LIBRARIAN:
/* 364 */         return Gold_Rabbit;
/*     */       case NITWIT:
/* 366 */         return Salt_and_Pepper_Rabbit;
/*     */       case PRIEST:
/* 368 */         return The_Killer_Bunny;
/*     */       case null:
/* 370 */         return White_Rabbit;
/*     */     } 
/* 372 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static MobNames getSheepName(Sheep sheep) {
/* 379 */     if (sheep.getCustomName() != null && sheep.getCustomName().equals("jeb_")) {
/* 380 */       return Rainbow_Sheep;
/*     */     }
/*     */     
/* 383 */     if (sheep.getColor() == null) {
/* 384 */       return null;
/*     */     }
/*     */     
/* 387 */     switch (sheep.getColor()) {
/*     */       case BLACKSMITH:
/* 389 */         return Black_Sheep;
/*     */       case BUTCHER:
/* 391 */         return Blue_Sheep;
/*     */       case FARMER:
/* 393 */         return Brown_Sheep;
/*     */       case LIBRARIAN:
/* 395 */         return Cyan_Sheep;
/*     */       case NITWIT:
/* 397 */         return Gray_Sheep;
/*     */       case PRIEST:
/* 399 */         return Green_Sheep;
/*     */       case null:
/* 401 */         return Light_Blue_Sheep;
/*     */       case null:
/* 403 */         return Lime_Sheep;
/*     */       case null:
/* 405 */         return Magenta_Sheep;
/*     */       case null:
/* 407 */         return Orange_Sheep;
/*     */       case null:
/* 409 */         return Pink_Sheep;
/*     */       case null:
/* 411 */         return Purple_Sheep;
/*     */       case null:
/* 413 */         return Red_Sheep;
/*     */       case null:
/* 415 */         return Light_Gray_Sheep;
/*     */       case null:
/* 417 */         return White_Sheep;
/*     */       case null:
/* 419 */         return Yellow_Sheep;
/*     */     } 
/* 421 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static MobNames getWolfName(Wolf wolf) {
/* 428 */     if (wolf.isTamed()) {
/* 429 */       return Tamed_Wolf;
/*     */     }
/*     */     
/* 432 */     return Wild_Wolf;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static MobNames getZombieVillagerName(ZombieVillager zombieVillager) {
/* 439 */     if (zombieVillager.getVillagerProfession() == null) {
/* 440 */       return null;
/*     */     }
/*     */     
/* 443 */     switch (zombieVillager.getVillagerProfession()) {
/*     */       case BLACKSMITH:
/* 445 */         return Zombie_Smith_Villager;
/*     */       case BUTCHER:
/* 447 */         return Zombie_Butcher_Villager;
/*     */       case FARMER:
/* 449 */         return Zombie_Farmer_Villager;
/*     */       case LIBRARIAN:
/* 451 */         return Zombie_Librarian_Villager;
/*     */       case NITWIT:
/* 453 */         return Zombie_Nitwit_Villager;
/*     */       case PRIEST:
/* 455 */         return Zombie_Cleric_Villager;
/*     */     } 
/* 457 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static MobNames getFishName(Entity entity) {
/* 463 */     if (entity.getName() == null) {
/* 464 */       return null;
/*     */     }
/*     */     
/* 467 */     switch (entity.getName()) {
/*     */       case "Raw Cod":
/* 469 */         return Cod;
/*     */       case "Raw Salmon":
/* 471 */         return Salmon;
/*     */       case "Pufferfish":
/* 473 */         return Pufferfish;
/*     */       case "Tropical Fish":
/* 475 */         return Tropical_Fish;
/*     */     } 
/* 477 */     return null;
/*     */   }
/*     */ }


/* Location:              F:\Minecraft Servers\SpigotLobby\plugins\MobHeads 2.4.jar!\com\cyber\mobheads\Utilities\MobNames.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.7
 */