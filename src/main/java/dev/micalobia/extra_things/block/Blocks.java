package dev.micalobia.extra_things.block;

import dev.micalobia.extra_things.ExtraThings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder.Factory;
import net.minecraft.block.AbstractBlock.Settings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockView;


public class Blocks {
	public static final Block TINTED_GLASS_PANE;

	static {
		TINTED_GLASS_PANE = register("tinted_glass_pane", new TintedPaneBlock(Settings.copy(net.minecraft.block.Blocks.TINTED_GLASS)));
	}

	private static boolean never(BlockState state, BlockView blockView, BlockPos blockPos) {
		return false;
	}

	private static boolean never(BlockState state, BlockView world, BlockPos pos, EntityType<?> type) {
		return false;
	}

	private static Block register(String id, Block block) {
		return Registry.register(Registry.BLOCK, ExtraThings.id(id), block);
	}

	private static <T extends BlockEntity> BlockEntityType<T> register_entity(String id, Factory<T> supplier, Block block) {
		return Registry.register(Registry.BLOCK_ENTITY_TYPE, ExtraThings.id(id), FabricBlockEntityTypeBuilder.create(supplier, block).build());
	}

	public static void init() {
	}
}
