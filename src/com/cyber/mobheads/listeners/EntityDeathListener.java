package com.cyber.mobheads.listeners;

import com.cyber.mobheads.Config.ConfigController;
import com.cyber.mobheads.Utilities.Broadcaster;
import com.cyber.mobheads.Utilities.MobMeta;
import com.cyber.mobheads.Utilities.MobNames;
import com.cyber.mobheads.Utilities.SkullFactory;
import com.cyber.mobheads.advancements.AdvancementsManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class EntityDeathListener
		implements Listener {
	private final List<UUID> killedByChargedCreeper = new ArrayList();


	@EventHandler
	public void onChargedCreeperDeath(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof LivingEntity) {
			LivingEntity le = (LivingEntity) event.getEntity();
			if (le.getHealth() - event.getDamage() <= 0.0D &&
					event.getDamager() instanceof Creeper && (
					(Creeper) event.getDamager()).isPowered()) {

				MobNames mobName = MobNames.getName(event.getEntity());
				if ((mobName != null && ConfigController.chargedCreeperDrop(mobName)) || (event
						.getEntity() instanceof Player && ConfigController.chargedCreeperDropForPlayer())) {
					this.killedByChargedCreeper.add(event.getEntity().getUniqueId());
				}
			}
		}
	}


	@EventHandler(priority = EventPriority.MONITOR)
	public void onEntityDeath(EntityDeathEvent event) {
		LivingEntity livingEntity = event.getEntity();
		Player killer = event.getEntity().getKiller();


		try {
			if (livingEntity instanceof Ageable)
				if (!ConfigController.supportBabies() && !((Ageable) livingEntity).isAdult()) {
					return;
				}
		} catch (ClassCastException classCastException) {
		}


		if (this.killedByChargedCreeper.remove(livingEntity.getUniqueId())) {
			ItemStack skull;
			if (livingEntity instanceof Player) {
				skull = getPlayerHead((Player) livingEntity, null, true);
			} else {
				skull = getMobHead(killer, livingEntity, true);
			}
			boolean alreadyThere = false;
			for (ItemStack is : event.getDrops()) {
				if (is != null && is.getType() == skull.getType()) ;
				{
					alreadyThere = true;
					break;
				}
			}
			if (!alreadyThere)
				event.getDrops().add(skull);
			return;
		}

		if (killer == null)
			return;
		if (killer != null)
			if (livingEntity.getUniqueId().equals(killer.getUniqueId()) &&
					!ConfigController.allow_self_player_head_farming()) {
				return;
			}


		if (livingEntity instanceof Player) {

			ItemStack playerSkull = getPlayerHead((Player) livingEntity, killer, false);
			if (ConfigController.dropOutsideInventory()) {
				event.getEntity().getWorld().dropItem(event.getEntity().getLocation(), playerSkull);
			} else {

				event.getDrops().add(playerSkull);
			}

		} else {
			for (ItemStack drops : event.getDrops()) {
				if(drops!=null)
				if (drops.getType() == Material.WITHER_SKELETON_SKULL) {
					MobNames mobname = MobNames.getName(livingEntity);
					MobMeta mobmeta = ConfigController.getRandomConfigMobMeta(mobname, false);
					//TODO: Check if correct: This will show the advancements, but not duplicate head if already exists
					AdvancementsManager.triggerAdvancement(killer, mobmeta.getAdvancements());

					if (mobmeta.isShouldBroadcast()) {
						String name = mobmeta.getUsedDisplayName();
						if (name == null) {
							name = SkullFactory.getVanillaName(livingEntity);
						}
						Broadcaster.broadCastMobHead(killer, name);
					}
					return;
				}
			}
			event.getDrops().add(getMobHead(killer, livingEntity, false));
		}
	}


	private ItemStack getMobHead(Player killer, Entity mob, boolean forceDrop) {
		if (killer != null)
			if (!forceDrop && !killer.hasPermission("com.cyber.mobheads.behead.mobs")) {
				return null;
			}


		MobNames mobName = MobNames.getName(mob);

		if (mobName == null) {
			if (mob.getType().equals(EntityType.WITHER_SKELETON)) {
				return null;
			}

			Broadcaster.outputInfoConsole("The mob \"" +mob.getType().name()+
					"\" is not supported, please update!", 1);
			return null;
		}


		MobMeta mobmeta = ConfigController.getRandomConfigMobMeta(mobName, false);

		if (!forceDrop) {

			int lootingValue = killer.getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS);
			double dropBonus = mobmeta.getDropBonus();
			double dropChance = mobmeta.getDropChance();

			double chance = dropChance + (lootingValue * dropBonus);


			if (!willDrop(chance)) {
				return null;
			}
		}


		ItemStack skull = SkullFactory.getMobSkull(mobmeta, killer);

		if (mobmeta.isShouldBroadcast() && !forceDrop) {
			String name = mobmeta.getUsedDisplayName();
			if (name == null) {
				name = SkullFactory.getVanillaName(mob);
			}
			Broadcaster.broadCastMobHead(killer, name);
		}
		return skull;
	}


	private ItemStack getPlayerHead(Player deadPlayer, Player killer, boolean forceDrop) {
		if (forceDrop) {
			return SkullFactory.getPlayerSkull(deadPlayer.getName(), killer);
		}

		if (!killer.hasPermission("com.cyber.mobheads.behead.players")) {
			return null;
		}

		int lootingValue = killer.getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS);
		double dropChance = ConfigController.getDropChancePlayer(lootingValue);

		if (willDrop(dropChance)) {
			Broadcaster.broadCastPlayerHead(killer, deadPlayer);
			return SkullFactory.getPlayerSkull(deadPlayer.getName(), killer);
		}
		return null;
	}

	private boolean willDrop(double dropChance) {
		double randomDouble = (new Random()).nextDouble();
		return (randomDouble <= dropChance);
	}

}
