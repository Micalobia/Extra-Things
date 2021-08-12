package dev.micalobia.extra_things.items;

import dev.micalobia.extra_things.ExtraThings;
import dev.micalobia.extra_things.block.Blocks;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraft.item.Item.Settings;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Items {
	public static final Item SWEET_BERRY_PIE;
	public static final Item GLOW_BERRY_PIE;

	static {
		SWEET_BERRY_PIE = register("sweet_berry_pie", new Item((new Settings()).group(ItemGroup.FOOD).food(FoodComponents.PUMPKIN_PIE)));
		GLOW_BERRY_PIE = register("glow_berry_pie", new Item((new Settings()).group(ItemGroup.FOOD).food(FoodComponents.PUMPKIN_PIE)));
		RegistryEntryAddedCallback.event(Registry.ITEM).register(Items::listenToRegister);
		//register("warped_wart", new AliasedBlockItem(Blocks.WARPED_WART, new Item.Settings().group(ItemGroup.MATERIALS)));
	}

	private static void listenToRegister(int i, Identifier identifier, Item item) {

	}

	public static void init() {
	}

	private static Item register(String id, Item item) {
		return register(ExtraThings.id(id), item);
	}

	private static Item register(Identifier id, Item item) {
		if(item instanceof BlockItem) {
			((BlockItem) item).appendBlocks(Item.BLOCK_ITEMS, item);
		}

		return Registry.register(Registry.ITEM, id, item);
	}

	private static Item register(String id, Block block, Settings settings) {
		return register(id, new BlockItem(block, settings));
	}

	private static Item register(Block block, Settings settings) {
		Identifier id = Registry.BLOCK.getId(block);
		return register(id.getNamespace(), block, settings);
	}
}
