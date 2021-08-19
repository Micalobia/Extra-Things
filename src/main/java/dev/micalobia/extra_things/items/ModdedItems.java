package dev.micalobia.extra_things.items;

import dev.micalobia.extra_things.ExtraThings;
import dev.micalobia.extra_things.block.ModdedBlocks;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraft.item.Item.Settings;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModdedItems {
	public static final Item SWEET_BERRY_PIE;
	public static final Item GLOW_BERRY_PIE;
	public static final Item TINTED_GLASS_PANE;
	public static final Item BLUE_NETHERRACK;
	public static final Item BLUE_CRIMSON_NYLIUM;
	public static final Item BLUE_WARPED_NYLIUM;
	public static final Item BLUE_NETHER_BRICK;

	static {
		SWEET_BERRY_PIE = register("sweet_berry_pie", new Item((new Settings()).group(ItemGroup.FOOD).food(FoodComponents.PUMPKIN_PIE)));
		GLOW_BERRY_PIE = register("glow_berry_pie", new Item((new Settings()).group(ItemGroup.FOOD).food(FoodComponents.PUMPKIN_PIE)));
		TINTED_GLASS_PANE = register(ModdedBlocks.TINTED_GLASS_PANE, ItemGroup.DECORATIONS);

		BLUE_NETHERRACK = ModdedItems.register(ModdedBlocks.BLUE_NETHERRACK, ItemGroup.BUILDING_BLOCKS);
		BLUE_CRIMSON_NYLIUM = ModdedItems.register(ModdedBlocks.BLUE_CRIMSON_NYLIUM, ItemGroup.BUILDING_BLOCKS);
		BLUE_WARPED_NYLIUM = ModdedItems.register(ModdedBlocks.BLUE_WARPED_NYLIUM, ItemGroup.BUILDING_BLOCKS);
		BLUE_NETHER_BRICK = ModdedItems.register("blue_nether_brick", new Item(new FabricItemSettings().group(ItemGroup.MATERIALS)));
		Item.BLOCK_ITEMS.put(ModdedBlocks.POTION_CAULDRON, Items.CAULDRON);
		Item.BLOCK_ITEMS.put(ModdedBlocks.DYE_CAULDRON, Items.CAULDRON);
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
