package com.cyber.mobheads.Utilities;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.*;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import org.bukkit.Location;

import javax.annotation.Nullable;

public class WorldGuardSupport {
	public static boolean canDropHeads(Location loc) {
		WorldGuard wGuard = WorldGuard.getInstance();
		for (ProtectedRegion k : wGuard.getPlatform().getRegionContainer().get(BukkitAdapter.adapt(loc.getWorld())).getRegions().values()) {
			if (k.contains(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ())) {
				RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
				RegionQuery query = container.createQuery();
				ApplicableRegionSet set = query.getApplicableRegions(BukkitAdapter.adapt(loc));
				return set.testState(null, flag);
			}
		}
		return true;
	}

	static StateFlag flag;

	public static void initiate() {

		com.sk89q.worldguard.WorldGuard instance = com.sk89q.worldguard.WorldGuard.getInstance();

		FlagRegistry registry = WorldGuard.getInstance().getFlagRegistry();
		try {
			// create a flag with the name "my-custom-flag", defaulting to true
			flag = new StateFlag("dropheads", true);
			registry.register(flag);
		} catch (FlagConflictException e) {
			// some other plugin registered a flag by the same name already.
			// you can use the existing flag, but this may cause conflicts - be sure to check type
			Flag<?> existing = registry.get("dropheads");
			if (existing instanceof StateFlag) {
				flag = (StateFlag) existing;
			} else {
				// types don't match - this is bad news! some other plugin conflicts with you
				// hopefully this never actually happens
			}
		}
	}
}
