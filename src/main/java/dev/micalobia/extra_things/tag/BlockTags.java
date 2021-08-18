package dev.micalobia.extra_things.tag;

import dev.micalobia.extra_things.ExtraThings;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.Block;
import net.minecraft.tag.Tag;

public class BlockTags {
	public static final Tag<Block> WARPED_NYLIUM;
	public static final Tag<Block> CRIMSON_NYLIUM;
	public static final Tag<Block> NETHERRACK;
	public static final Tag<Block> IMMOVABLE;

	static {
		WARPED_NYLIUM = TagRegistry.block(ExtraThings.id("warped_nylium"));
		CRIMSON_NYLIUM = TagRegistry.block(ExtraThings.id("crimson_nylium"));
		NETHERRACK = TagRegistry.block(ExtraThings.id("netherrack"));
		IMMOVABLE = TagRegistry.block(ExtraThings.id("immovable"));
	}
}
