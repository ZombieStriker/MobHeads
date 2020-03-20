package com.cyber.mobheads.Utilities;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.*;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Location;

import javax.annotation.Nullable;

public class WorldGuardSupport {
	public static boolean canDropHeads(Location loc){
	    WorldGuard wGuard = WorldGuard.getInstance();
		for (ProtectedRegion k : wGuard.getPlatform().getRegionContainer().get(BukkitAdapter.adapt(loc.getWorld())).getRegions().values()) {
			if (k.contains(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ())) {
				return k.getFlag(flag).booleanValue();
			}
		}
		return true;
	}

	static DropHeadFlag flag = new DropHeadFlag("dropheads",RegionGroup.ALL);

	public static void initiate(){

		com.sk89q.worldguard.WorldGuard instance = com.sk89q.worldguard.WorldGuard.getInstance();

		instance.getFlagRegistry().register(flag);
	}

	static class DropHeadFlag extends Flag<Boolean>{

		protected DropHeadFlag(String name, @Nullable RegionGroup defaultGroup) {
			super(name, defaultGroup);
		}

		@Override
		public Boolean parseInput(FlagContext context) throws InvalidFlagFormat {
			return Boolean.parseBoolean(context.getUserInput());
		}

		@Override
		public Boolean unmarshal(@Nullable Object o) {
			return null;
		}

		@Override
		public Object marshal(Boolean o) {
			return null;
		}
	}
}
