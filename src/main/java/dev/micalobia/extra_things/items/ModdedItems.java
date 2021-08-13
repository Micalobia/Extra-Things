package dev.micalobia.extra_things.items;

import dev.micalobia.extra_things.ExtraThings;
import dev.micalobia.extra_things.block.NetherBlocks;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.FoodComponents;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Settings;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModdedItems {
	public static final Item SWEET_BERRY_PIE;
	public static final Item GLOW_BERRY_PIE;

	static {
		SWEET_BERRY_PIE = register("sweet_berry_pie", new Item((new Settings()).group(ItemGroup.FOOD).food(FoodComponents.PUMPKIN_PIE)));
		GLOW_BERRY_PIE = register("glow_berry_pie", new Item((new Settings()).group(ItemGroup.FOOD).food(FoodComponents.PUMPKIN_PIE)));
		NetherItems.init();
		//register("warped_wart", new AliasedBlockItem(Blocks.WARPED_WART, new Item.Settings().group(ItemGroup.MATERIALS)));
	}

	public static void init() {
	}

	public static Item register(String id, Item item) {
		return register(ExtraThings.id(id), item);
	}

	public static Item register(Identifier id, Item item) {
		if(item instanceof BlockItem) {
			((BlockItem) item).appendBlocks(Item.BLOCK_ITEMS, item);
		}

		return Registry.register(Registry.ITEM, id, item);
	}

	public static Item register(String id, Block block, Settings settings) {
		return register(id, new BlockItem(block, settings));
	}

	public static Item register(Block block, Settings settings) {
		Identifier id = Registry.BLOCK.getId(block);
		return register(id.getNamespace(), block, settings);
	}

	public static Item register(Block block, ItemGroup group) {
		return register(new BlockItem(block, new FabricItemSettings().group(group)));
	}

	public static Item register(BlockItem item) {
		return register(item.getBlock(), item);
	}

	public static Item register(Block block, Item item) {
		return register(Registry.BLOCK.getId(block), item);
	}
}
