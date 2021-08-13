package dev.micalobia.extra_things.block;

import dev.micalobia.extra_things.mixin.block.NyliumBlockFactory;
import dev.micalobia.extra_things.mixin.block.StairsBlockFactory;
import net.minecraft.block.AbstractBlock.Settings;
import net.minecraft.block.*;

public class NetherBlocks {
	public static final Block BLUE_NETHERRACK;

	public static final Block CRACKED_NETHER_BRICK_SLAB;
	public static final Block CRACKED_NETHER_BRICK_WALL;
	public static final Block CRACKED_NETHER_BRICK_FENCE;
	public static final Block CRACKED_NETHER_BRICK_STAIRS;
	public static final Block CHISELED_NETHER_BRICK_SLAB;
	public static final Block CHISELED_NETHER_BRICK_WALL;
	public static final Block CHISELED_NETHER_BRICK_FENCE;
	public static final Block CHISELED_NETHER_BRICK_STAIRS;
	public static final Block RED_NETHER_BRICK_FENCE;
	public static final Block RED_CRACKED_NETHER_BRICKS;
	public static final Block RED_CRACKED_NETHER_BRICK_SLAB;
	public static final Block RED_CRACKED_NETHER_BRICK_WALL;
	public static final Block RED_CRACKED_NETHER_BRICK_FENCE;
	public static final Block RED_CRACKED_NETHER_BRICK_STAIRS;
	public static final Block RED_CHISELED_NETHER_BRICKS;
	public static final Block RED_CHISELED_NETHER_BRICK_SLAB;
	public static final Block RED_CHISELED_NETHER_BRICK_WALL;
	public static final Block RED_CHISELED_NETHER_BRICK_FENCE;
	public static final Block RED_CHISELED_NETHER_BRICK_STAIRS;
	public static final Block BLUE_NETHER_BRICKS;
	public static final Block BLUE_NETHER_BRICK_SLAB;
	public static final Block BLUE_NETHER_BRICK_WALL;
	public static final Block BLUE_NETHER_BRICK_FENCE;
	public static final Block BLUE_NETHER_BRICK_STAIRS;
	public static final Block BLUE_CRACKED_NETHER_BRICKS;
	public static final Block BLUE_CRACKED_NETHER_BRICK_SLAB;
	public static final Block BLUE_CRACKED_NETHER_BRICK_WALL;
	public static final Block BLUE_CRACKED_NETHER_BRICK_FENCE;
	public static final Block BLUE_CRACKED_NETHER_BRICK_STAIRS;
	public static final Block BLUE_CHISELED_NETHER_BRICKS;
	public static final Block BLUE_CHISELED_NETHER_BRICK_SLAB;
	public static final Block BLUE_CHISELED_NETHER_BRICK_WALL;
	public static final Block BLUE_CHISELED_NETHER_BRICK_FENCE;
	public static final Block BLUE_CHISELED_NETHER_BRICK_STAIRS;
	public static final Block WARPED_NETHER_BRICKS;
	public static final Block WARPED_NETHER_BRICK_SLAB;
	public static final Block WARPED_NETHER_BRICK_WALL;
	public static final Block WARPED_NETHER_BRICK_FENCE;
	public static final Block WARPED_NETHER_BRICK_STAIRS;
	public static final Block WARPED_CRACKED_NETHER_BRICKS;
	public static final Block WARPED_CRACKED_NETHER_BRICK_SLAB;
	public static final Block WARPED_CRACKED_NETHER_BRICK_WALL;
	public static final Block WARPED_CRACKED_NETHER_BRICK_FENCE;
	public static final Block WARPED_CRACKED_NETHER_BRICK_STAIRS;
	public static final Block WARPED_CHISELED_NETHER_BRICKS;
	public static final Block WARPED_CHISELED_NETHER_BRICK_SLAB;
	public static final Block WARPED_CHISELED_NETHER_BRICK_WALL;
	public static final Block WARPED_CHISELED_NETHER_BRICK_FENCE;
	public static final Block WARPED_CHISELED_NETHER_BRICK_STAIRS;
	public static final Block BLUE_CRIMSON_NYLIUM;
	public static final Block BLUE_WARPED_NYLIUM;

	static {
		BLUE_NETHERRACK = register("blue_netherrack", new NetherrackBlock(Settings.copy(Blocks.NETHERRACK).mapColor(MapColor.DARK_AQUA)));

		CRACKED_NETHER_BRICK_SLAB = register("cracked_nether_brick_slab", new SlabBlock(Settings.copy(Blocks.CRACKED_NETHER_BRICKS)));
		CRACKED_NETHER_BRICK_WALL = register("cracked_nether_brick_wall", new WallBlock(Settings.copy(Blocks.CRACKED_NETHER_BRICKS)));
		CRACKED_NETHER_BRICK_FENCE = register("cracked_nether_brick_fence", new FenceBlock(Settings.copy(Blocks.CRACKED_NETHER_BRICKS)));
		CRACKED_NETHER_BRICK_STAIRS = register("cracked_nether_brick_stairs", StairsBlockFactory.create(Blocks.CRACKED_NETHER_BRICKS.getDefaultState(), Settings.copy(Blocks.CRACKED_NETHER_BRICKS)));
		CHISELED_NETHER_BRICK_SLAB = register("chiseled_nether_brick_slab", new SlabBlock(Settings.copy(Blocks.CHISELED_NETHER_BRICKS)));
		CHISELED_NETHER_BRICK_WALL = register("chiseled_nether_brick_wall", new WallBlock(Settings.copy(Blocks.CHISELED_NETHER_BRICKS)));
		CHISELED_NETHER_BRICK_FENCE = register("chiseled_nether_brick_fence", new FenceBlock(Settings.copy(Blocks.CHISELED_NETHER_BRICKS)));
		CHISELED_NETHER_BRICK_STAIRS = register("chiseled_nether_brick_stairs", StairsBlockFactory.create(Blocks.CHISELED_NETHER_BRICKS.getDefaultState(), Settings.copy(Blocks.CHISELED_NETHER_BRICKS)));
		RED_NETHER_BRICK_FENCE = register("red_nether_brick_fence", new FenceBlock(Settings.copy(Blocks.RED_NETHER_BRICKS)));
		RED_CRACKED_NETHER_BRICKS = register("red_cracked_nether_bricks", new Block(Settings.copy(Blocks.RED_NETHER_BRICKS).mapColor(MapColor.DARK_RED)));
		RED_CRACKED_NETHER_BRICK_SLAB = register("red_cracked_nether_brick_slab", new SlabBlock(Settings.copy(RED_CRACKED_NETHER_BRICKS)));
		RED_CRACKED_NETHER_BRICK_WALL = register("red_cracked_nether_brick_wall", new WallBlock(Settings.copy(RED_CRACKED_NETHER_BRICKS)));
		RED_CRACKED_NETHER_BRICK_FENCE = register("red_cracked_nether_brick_fence", new FenceBlock(Settings.copy(RED_CRACKED_NETHER_BRICKS)));
		RED_CRACKED_NETHER_BRICK_STAIRS = register("red_cracked_nether_brick_stairs", StairsBlockFactory.create(RED_CRACKED_NETHER_BRICKS.getDefaultState(), Settings.copy(RED_CRACKED_NETHER_BRICKS)));
		RED_CHISELED_NETHER_BRICKS = register("red_chiseled_nether_bricks", new Block(Settings.copy(Blocks.RED_NETHER_BRICKS).mapColor(MapColor.DARK_RED)));
		RED_CHISELED_NETHER_BRICK_SLAB = register("red_chiseled_nether_brick_slab", new SlabBlock(Settings.copy(RED_CHISELED_NETHER_BRICKS)));
		RED_CHISELED_NETHER_BRICK_WALL = register("red_chiseled_nether_brick_wall", new WallBlock(Settings.copy(RED_CHISELED_NETHER_BRICKS)));
		RED_CHISELED_NETHER_BRICK_FENCE = register("red_chiseled_nether_brick_fence", new FenceBlock(Settings.copy(RED_CHISELED_NETHER_BRICKS)));
		RED_CHISELED_NETHER_BRICK_STAIRS = register("red_chiseled_nether_brick_stairs", StairsBlockFactory.create(RED_CHISELED_NETHER_BRICKS.getDefaultState(), Settings.copy(RED_CHISELED_NETHER_BRICKS)));
		BLUE_NETHER_BRICKS = register("blue_nether_bricks", new Block(Settings.copy(Blocks.NETHER_BRICKS).mapColor(MapColor.DARK_AQUA)));
		BLUE_NETHER_BRICK_SLAB = register("blue_nether_brick_slab", new SlabBlock(Settings.copy(BLUE_NETHER_BRICKS)));
		BLUE_NETHER_BRICK_WALL = register("blue_nether_brick_wall", new WallBlock(Settings.copy(BLUE_NETHER_BRICKS)));
		BLUE_NETHER_BRICK_FENCE = register("blue_nether_brick_fence", new FenceBlock(Settings.copy(BLUE_NETHER_BRICKS)));
		BLUE_NETHER_BRICK_STAIRS = register("blue_nether_brick_stairs", StairsBlockFactory.create(BLUE_NETHER_BRICKS.getDefaultState(), Settings.copy(BLUE_NETHER_BRICKS)));
		BLUE_CRACKED_NETHER_BRICKS = register("blue_cracked_nether_bricks", new Block(Settings.copy(Blocks.NETHER_BRICKS).mapColor(MapColor.DARK_AQUA)));
		BLUE_CRACKED_NETHER_BRICK_SLAB = register("blue_cracked_nether_brick_slab", new SlabBlock(Settings.copy(BLUE_CRACKED_NETHER_BRICKS)));
		BLUE_CRACKED_NETHER_BRICK_WALL = register("blue_cracked_nether_brick_wall", new WallBlock(Settings.copy(BLUE_CRACKED_NETHER_BRICKS)));
		BLUE_CRACKED_NETHER_BRICK_FENCE = register("blue_cracked_nether_brick_fence", new FenceBlock(Settings.copy(BLUE_CRACKED_NETHER_BRICKS)));
		BLUE_CRACKED_NETHER_BRICK_STAIRS = register("blue_cracked_nether_brick_stairs", StairsBlockFactory.create(BLUE_CRACKED_NETHER_BRICKS.getDefaultState(), Settings.copy(BLUE_CRACKED_NETHER_BRICKS)));
		BLUE_CHISELED_NETHER_BRICKS = register("blue_chiseled_nether_bricks", new Block(Settings.copy(Blocks.NETHER_BRICKS).mapColor(MapColor.DARK_AQUA)));
		BLUE_CHISELED_NETHER_BRICK_SLAB = register("blue_chiseled_nether_brick_slab", new SlabBlock(Settings.copy(BLUE_CHISELED_NETHER_BRICKS)));
		BLUE_CHISELED_NETHER_BRICK_WALL = register("blue_chiseled_nether_brick_wall", new WallBlock(Settings.copy(BLUE_CHISELED_NETHER_BRICKS)));
		BLUE_CHISELED_NETHER_BRICK_FENCE = register("blue_chiseled_nether_brick_fence", new FenceBlock(Settings.copy(BLUE_CHISELED_NETHER_BRICKS)));
		BLUE_CHISELED_NETHER_BRICK_STAIRS = register("blue_chiseled_nether_brick_stairs", StairsBlockFactory.create(BLUE_CHISELED_NETHER_BRICKS.getDefaultState(), Settings.copy(BLUE_CHISELED_NETHER_BRICKS)));
		WARPED_NETHER_BRICKS = register("warped_nether_bricks", new Block(Settings.copy(Blocks.RED_NETHER_BRICKS).mapColor(MapColor.DARK_AQUA)));
		WARPED_NETHER_BRICK_SLAB = register("warped_nether_brick_slab", new SlabBlock(Settings.copy(WARPED_NETHER_BRICKS)));
		WARPED_NETHER_BRICK_WALL = register("warped_nether_brick_wall", new WallBlock(Settings.copy(WARPED_NETHER_BRICKS)));
		WARPED_NETHER_BRICK_FENCE = register("warped_nether_brick_fence", new FenceBlock(Settings.copy(WARPED_NETHER_BRICKS)));
		WARPED_NETHER_BRICK_STAIRS = register("warped_nether_brick_stairs", StairsBlockFactory.create(WARPED_NETHER_BRICKS.getDefaultState(), Settings.copy(WARPED_NETHER_BRICKS)));
		WARPED_CRACKED_NETHER_BRICKS = register("warped_cracked_nether_bricks", new Block(Settings.copy(Blocks.RED_NETHER_BRICKS).mapColor(MapColor.DARK_AQUA)));
		WARPED_CRACKED_NETHER_BRICK_SLAB = register("warped_cracked_nether_brick_slab", new SlabBlock(Settings.copy(WARPED_CRACKED_NETHER_BRICKS)));
		WARPED_CRACKED_NETHER_BRICK_WALL = register("warped_cracked_nether_brick_wall", new WallBlock(Settings.copy(WARPED_CRACKED_NETHER_BRICKS)));
		WARPED_CRACKED_NETHER_BRICK_FENCE = register("warped_cracked_nether_brick_fence", new FenceBlock(Settings.copy(WARPED_CRACKED_NETHER_BRICKS)));
		WARPED_CRACKED_NETHER_BRICK_STAIRS = register("warped_cracked_nether_brick_stairs", StairsBlockFactory.create(WARPED_CRACKED_NETHER_BRICKS.getDefaultState(), Settings.copy(WARPED_CRACKED_NETHER_BRICKS)));
		WARPED_CHISELED_NETHER_BRICKS = register("warped_chiseled_nether_bricks", new Block(Settings.copy(Blocks.RED_NETHER_BRICKS).mapColor(MapColor.DARK_AQUA)));
		WARPED_CHISELED_NETHER_BRICK_SLAB = register("warped_chiseled_nether_brick_slab", new SlabBlock(Settings.copy(WARPED_CHISELED_NETHER_BRICKS)));
		WARPED_CHISELED_NETHER_BRICK_WALL = register("warped_chiseled_nether_brick_wall", new WallBlock(Settings.copy(WARPED_CHISELED_NETHER_BRICKS)));
		WARPED_CHISELED_NETHER_BRICK_FENCE = register("warped_chiseled_nether_brick_fence", new FenceBlock(Settings.copy(WARPED_CHISELED_NETHER_BRICKS)));
		WARPED_CHISELED_NETHER_BRICK_STAIRS = register("warped_chiseled_nether_brick_stairs", StairsBlockFactory.create(WARPED_CHISELED_NETHER_BRICKS.getDefaultState(), Settings.copy(WARPED_CHISELED_NETHER_BRICKS)));
		BLUE_CRIMSON_NYLIUM = register("blue_crimson_nylium", NyliumBlockFactory.create(Settings.copy(Blocks.CRIMSON_NYLIUM)));
		BLUE_WARPED_NYLIUM = register("blue_warped_nylium", NyliumBlockFactory.create(Settings.copy(Blocks.WARPED_NYLIUM)));
	}

	public static Block register(String id, Block block) {
		return ModdedBlocks.register(id, block);
	}

	public static void init() {

	}
}
