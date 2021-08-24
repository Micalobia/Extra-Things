package dev.micalobia.extra_things.tag;

import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.Block;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class ModdedBlockTags {
	public static final Tag<Block> WARPED_NYLIUM;
	public static final Tag<Block> CRIMSON_NYLIUM;
	public static final Tag<Block> NETHERRACK;
	public static final Tag<Block> IMMOVABLE;
	public static final Tag<Block> NETHER_WART;

	static {
		WARPED_NYLIUM = TagRegistry.block(common("warped_nylium"));
		CRIMSON_NYLIUM = TagRegistry.block(common("crimson_nylium"));
		NETHERRACK = TagRegistry.block(common("netherrack"));
		IMMOVABLE = TagRegistry.block(common("immovable"));
		NETHER_WART = TagRegistry.block(common("nether_wart"));
	}

	private static Identifier common(String path) {
		return new Identifier("c", path);
	}
}
