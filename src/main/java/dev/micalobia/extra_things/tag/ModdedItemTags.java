package dev.micalobia.extra_things.tag;

import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class ModdedItemTags {
	public static final Tag<Item> NETHER_WART;

	static {
		NETHER_WART = TagRegistry.item(common("nether_wart"));
	}

	private static Identifier common(String path) {
		return new Identifier("c", path);
	}
}
