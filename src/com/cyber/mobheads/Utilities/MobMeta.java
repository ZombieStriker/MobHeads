package com.cyber.mobheads.Utilities;

import java.util.List;

public class MobMeta {
	private final MobNames mobName;
	private final String displayName;
	private final String UUID;
	private final String textureCode;
	private final double dropChance;
	private final double dropBonus;
	private final boolean shouldBroadcast;
	private final boolean chargedCreeperDrop;
	private final List<String> advancements;

	public MobMeta(MobNames mobName, String displayName, String UUID, String textureCode, double dropChance, double dropBonus, boolean shouldBroadcast, boolean chargedCreeperDrop, List<String> advancements) {
		this.mobName = mobName;
		this.displayName = displayName;
		this.UUID = UUID;
		this.textureCode = textureCode;
		this.dropChance = dropChance;
		this.dropBonus = dropBonus;
		this.shouldBroadcast = shouldBroadcast;
		this.chargedCreeperDrop = chargedCreeperDrop;
		this.advancements = advancements;
	}


	public MobNames getMobName() {
		return this.mobName;
	}



	public String getUsedDisplayName() {
		if(this.displayName.equals("[vanilla]")){
			return SkullFactory.getVanillaName(this.mobName);
		}
		return this.displayName;
	}

	public String getDisplayName() {
		return this.displayName;
	}


	public String getUUID() {
		return this.UUID;
	}


	public String getTextureCode() {
		return this.textureCode;
	}


	public double getDropChance() {
		return this.dropChance;
	}


	public double getDropBonus() {
		return this.dropBonus;
	}


	public boolean isShouldBroadcast() {
		return this.shouldBroadcast;
	}


	public boolean isChargedCreeperDrop() {
		return this.chargedCreeperDrop;
	}


	public List<String> getAdvancements() {
		return this.advancements;
	}
}
