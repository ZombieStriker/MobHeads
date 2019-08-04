package com.cyber.mobheads.listeners;

import com.cyber.mobheads.Config.ConfigController;
import com.cyber.mobheads.Utilities.Broadcaster;
import com.cyber.mobheads.Utilities.MobMeta;
import com.cyber.mobheads.Utilities.MobNames;
import com.cyber.mobheads.Utilities.SkullFactory;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;


public class FishListener implements Listener {
	@EventHandler
	public void fishCaught(PlayerFishEvent event) {
		if (!event.getPlayer().hasPermission("com.cyber.mobheads.behead.fish")) {
			return;
		}

		if (event.getState().equals(PlayerFishEvent.State.CAUGHT_FISH)) {
			MobNames mobname = MobNames.getName(event.getCaught());

			if (mobname == null) {
				return;
			}

			MobMeta mobmeta = ConfigController.getRandomConfigMobMeta(mobname, true);


			int luckOfTheSeaLevel = event.getPlayer().getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.LUCK);
			double dropBonus = mobmeta.getDropBonus();
			double dropChance = mobmeta.getDropChance();

			double chance = dropChance + luckOfTheSeaLevel * dropBonus;


			if (willDrop(chance)) {

				ItemStack skull = (new SkullFactory()).getMobSkull(mobmeta, event.getPlayer());

				Item fish = (Item) event.getCaught();
				Item skullItem = fish.getWorld().dropItem(fish.getLocation(), skull);
				skullItem.setVelocity(fish.getVelocity());

				event.getCaught().setPassenger(skullItem);

				if (mobmeta.isShouldBroadcast()) {
					Broadcaster.broadCastMobHead(event.getPlayer(), mobmeta.getDisplayName());
				}
			}
		}
	}

	private boolean willDrop(double dropChance) {
		double randomDouble = (new Random()).nextDouble();
		return (randomDouble <= dropChance);
	}
}


/* Location:              F:\Minecraft Servers\SpigotLobby\plugins\MobHeads 2.4.jar!\com\cyber\mobheads\listeners\FishListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.7
 */