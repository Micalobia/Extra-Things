package dev.micalobia.extra_things.block;

import dev.micalobia.extra_things.ExtraThings;
import dev.micalobia.extra_things.block.cauldron.CauldronBehaviors;
import dev.micalobia.extra_things.mixin.block.NetherWartBlockFactory;
import dev.micalobia.extra_things.mixin.block.NyliumBlockFactory;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder.Factory;
import net.minecraft.block.*;
import net.minecraft.block.AbstractBlock.Settings;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;


public class ModdedBlocks {
	public static final Block TINTED_GLASS_PANE;
	public static final Block BLUE_NETHERRACK;
	public static final Block BLUE_CRIMSON_NYLIUM;
	public static final Block BLUE_WARPED_NYLIUM;
	public static final Block POTION_CAULDRON;
	public static final Block DYE_CAULDRON;
	public static final Block WARPED_WART;
	public static final Block LUMBERMILL;

	static {
		POTION_CAULDRON = register("potion_cauldron", new PotionCauldronBlock(AbstractBlock.Settings.copy(Blocks.CAULDRON)));
		DYE_CAULDRON = register("dye_cauldron", new DyeCauldronBlock(AbstractBlock.Settings.copy(Blocks.CAULDRON)));
		TINTED_GLASS_PANE = register("tinted_glass_pane", new TintedPaneBlock(AbstractBlock.Settings.copy(Blocks.TINTED_GLASS)));
		WARPED_WART = register("warped_wart", NetherWartBlockFactory.create(AbstractBlock.Settings.copy(Blocks.NETHER_WART).mapColor(MapColor.TEAL)));
		LUMBERMILL = register("lumbermill", new LumbermillBlock(AbstractBlock.Settings.copy(Blocks.STONECUTTER)));

		BLUE_NETHERRACK = register("blue_netherrack", new NetherrackBlock(Settings.copy(Blocks.NETHERRACK).mapColor(MapColor.DARK_AQUA)));
		BLUE_CRIMSON_NYLIUM = register("blue_crimson_nylium", NyliumBlockFactory.create(Settings.copy(Blocks.CRIMSON_NYLIUM)));
		BLUE_WARPED_NYLIUM = register("blue_warped_nylium", NyliumBlockFactory.create(Settings.copy(Blocks.WARPED_NYLIUM)));
		BlockExtension.start(Blocks.CRACKED_NETHER_BRICKS).slab().stairs().fence().wall().item(ItemGroup.BUILDING_BLOCKS).build("cracked_nether_brick");
		BlockExtension.start(Blocks.RED_NETHER_BRICKS).fence().item(ItemGroup.BUILDING_BLOCKS).build("red_nether_brick");
		BlockExtension.start("red_cracked_nether_bricks", Settings.copy(Blocks.RED_NETHER_BRICKS)).slab().stairs().fence().wall().item(ItemGroup.BUILDING_BLOCKS).build("red_cracked_nether_brick");
		BlockExtension.start("red_chiseled_nether_bricks", Settings.copy(Blocks.RED_NETHER_BRICKS)).item(ItemGroup.BUILDING_BLOCKS).build("red_chiseled_nether_brick");
		BlockExtension.start("blue_nether_bricks", Settings.copy(Blocks.NETHER_BRICKS).mapColor(MapColor.DARK_AQUA)).slab().stairs().fence().wall().item(ItemGroup.BUILDING_BLOCKS).build("blue_nether_brick");
		BlockExtension.start("blue_cracked_nether_bricks", Settings.copy(Blocks.NETHER_BRICKS).mapColor(MapColor.DARK_AQUA)).slab().stairs().fence().wall().item(ItemGroup.BUILDING_BLOCKS).build("blue_cracked_nether_brick");
		BlockExtension.start("blue_chiseled_nether_bricks", Settings.copy(Blocks.NETHER_BRICKS).mapColor(MapColor.DARK_AQUA)).item(ItemGroup.BUILDING_BLOCKS).build("blue_chiseled_nether_brick");
		BlockExtension.start("warped_nether_bricks", Settings.copy(Blocks.RED_NETHER_BRICKS).mapColor(MapColor.DARK_AQUA)).slab().stairs().fence().wall().item(ItemGroup.BUILDING_BLOCKS).build("warped_nether_brick");
		BlockExtension.start("warped_cracked_nether_bricks", Settings.copy(Blocks.RED_NETHER_BRICKS).mapColor(MapColor.DARK_AQUA)).slab().stairs().fence().wall().item(ItemGroup.BUILDING_BLOCKS).build("warped_cracked_nether_brick");
		BlockExtension.start("warped_chiseled_nether_bricks", Settings.copy(Blocks.RED_NETHER_BRICKS).mapColor(MapColor.DARK_AQUA)).item(ItemGroup.BUILDING_BLOCKS).build("warped_chiseled_nether_brick");
		BlockExtension.start(Blocks.OBSIDIAN).slab().stairs().wall().item(ItemGroup.BUILDING_BLOCKS).build();
		BlockExtension.start(Blocks.CRYING_OBSIDIAN).slab().stairs().wall().item(ItemGroup.BUILDING_BLOCKS).build();
		BlockExtension.start(Blocks.QUARTZ_BLOCK).wall("minecraft:block/quartz_block_top").item(ItemGroup.BUILDING_BLOCKS).build("quartz");
		BlockExtension.start(Blocks.QUARTZ_BRICKS).slab().stairs().wall().item(ItemGroup.BUILDING_BLOCKS).build("quartz_brick");
		BlockExtension.start(Blocks.CHISELED_QUARTZ_BLOCK).slab().stairs().wall().item(ItemGroup.BUILDING_BLOCKS).build("chiseled_quartz");
		BlockExtension.start(Blocks.SMOOTH_QUARTZ).wall("minecraft:block/quartz_block_bottom").item(ItemGroup.BUILDING_BLOCKS).build();
		if(ExtraThings.onClient())
			clientInit();
	}

	public static Block register(String id, Block block) {
		return Registry.register(Registry.BLOCK, ExtraThings.id(id), block);
	}

	public static <T extends BlockEntity> BlockEntityType<T> register_entity(String id, Factory<T> supplier, Block block) {
		return Registry.register(Registry.BLOCK_ENTITY_TYPE, ExtraThings.id(id), FabricBlockEntityTypeBuilder.create(supplier, block).build());
	}

	public static void init() {
	}

	@Environment(EnvType.CLIENT)
	public static void clientInit() {
		BlockRenderLayerMap.INSTANCE.putBlock(TINTED_GLASS_PANE, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(WARPED_WART, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(LUMBERMILL, RenderLayer.getCutout());
		ColorProviderRegistry.BLOCK.register(CauldronBehaviors::colorProvider, POTION_CAULDRON, DYE_CAULDRON);
	}
}
