package dev.micalobia.extra_things.block;

import dev.micalobia.extra_things.ExtraThings;
import dev.micalobia.extra_things.mixin.block.StairsBlockFactory;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder.Factory;
import net.minecraft.block.*;
import net.minecraft.block.AbstractBlock.Settings;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EntityType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockView;


public class ModdedBlocks {
	public static final Block TINTED_GLASS_PANE;

	static {
		TINTED_GLASS_PANE = register("tinted_glass_pane", new TintedPaneBlock(Settings.copy(Blocks.TINTED_GLASS)));
		NetherBlocks.init();
	}

	public static Block register(String id, Block block) {
		return Registry.register(Registry.BLOCK, ExtraThings.id(id), block);
	}

	public static <T extends BlockEntity> BlockEntityType<T> register_entity(String id, Factory<T> supplier, Block block) {
		return Registry.register(Registry.BLOCK_ENTITY_TYPE, ExtraThings.id(id), FabricBlockEntityTypeBuilder.create(supplier, block).build());
	}

	public static void init() {
	}
}
