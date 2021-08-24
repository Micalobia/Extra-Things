package dev.micalobia.extra_things.block.entity;

import dev.micalobia.extra_things.ExtraThings;
import dev.micalobia.extra_things.block.ModdedBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;

public class ModdedBlockEntities {
	public static final BlockEntityType<PotionCauldronBlockEntity> POTION_CAULDRON;
	public static final BlockEntityType<DyeCauldronBlockEntity> DYE_CAULDRON;
	public static final BlockEntityType<KilnBlockEntity> KILN;

	static {
		POTION_CAULDRON = register("potion_cauldron_block_entity", PotionCauldronBlockEntity::new, ModdedBlocks.POTION_CAULDRON);
		DYE_CAULDRON = register("dye_cauldron_block_entity", DyeCauldronBlockEntity::new, ModdedBlocks.DYE_CAULDRON);
		KILN = register("kiln", KilnBlockEntity::new, ModdedBlocks.KILN);
	}

	private static <T extends BlockEntity> BlockEntityType<T> register(String id, FabricBlockEntityTypeBuilder.Factory<T> factory, Block... blocks) {
		return Registry.register(Registry.BLOCK_ENTITY_TYPE, ExtraThings.id(id), FabricBlockEntityTypeBuilder.create(factory, blocks).build());
	}

	public static void init() {
	}
}
