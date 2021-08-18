package dev.micalobia.extra_things.block;

import dev.micalobia.extra_things.ExtraThings;
import dev.micalobia.extra_things.mixin.block.PaneBlockFactory;
import dev.micalobia.extra_things.mixin.block.StairsBlockFactory;
import net.devtech.arrp.json.blockstate.JBlockModel;
import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.blockstate.JVariant;
import net.devtech.arrp.json.models.JModel;
import net.devtech.arrp.json.models.JTextures;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class BlockExtension {
	protected Block base;
	protected Identifier base_id;
	protected Identifier prefix;
	protected AbstractBlock.Settings settings;
	protected @Nullable Item.Settings itemSettings;

	@Environment(EnvType.CLIENT)
	protected RenderLayer renderLayer;

	protected BlockExtension(Identifier prefix, Identifier base, AbstractBlock.Settings settings, @Nullable Item.Settings itemSettings, @Nullable RenderLayer renderLayer) {
		this.base_id = base;
		this.prefix = prefix;
		this.settings = settings;
		this.itemSettings = itemSettings;
		this.base = register(base, new Block(settings));
		if(ExtraThings.onClient()) {
			if(renderLayer != null)
				setRenderLayer(renderLayer);
			else
				setRenderLayer();
			BlockRenderLayerMap.INSTANCE.putBlock(this.base, this.renderLayer);
		}
		generate_base_files();
	}

	protected BlockExtension(Identifier prefix, Block base, @Nullable Item.Settings itemSettings, @Nullable RenderLayer renderLayer) {
		this.base = base;
		this.base_id = Registry.BLOCK.getId(base);
		this.prefix = prefix;
		this.settings = AbstractBlock.Settings.copy(base);
		this.itemSettings = itemSettings;
		if(ExtraThings.onClient()) {
			if(renderLayer != null)
				setRenderLayer(renderLayer);
			else
				setRenderLayer();
		}
	}

	public static Builder start(Identifier blockId, AbstractBlock.Settings blockSettings) {
		return new Builder().base(blockId, blockSettings);
	}

	static Builder start(String blockId, AbstractBlock.Settings blockSettings) {
		return new Builder().base(ExtraThings.id(blockId), blockSettings);
	}

	public static Builder start(Block block) {
		return new Builder().base(block);
	}

	protected Identifier append(String suffix) {
		return new Identifier(prefix.getNamespace(), prefix.getPath() + suffix);
	}

	protected Block register(Identifier id, Block block) {
		block = Registry.register(Registry.BLOCK, id, block);
		if(itemSettings != null) Registry.register(Registry.ITEM, id, new BlockItem(block, itemSettings));
		return block;
	}

	@Environment(EnvType.CLIENT)
	protected void setRenderLayer() {
		this.renderLayer = RenderLayers.getBlockLayer(base.getDefaultState());
	}

	@Environment(EnvType.CLIENT)
	protected void setRenderLayer(RenderLayer layer) {
		this.renderLayer = layer;
	}

	protected void slab() {
		Block block = register(append("_slab"), new SlabBlock(settings));
		if(FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT)
			BlockRenderLayerMap.INSTANCE.putBlock(block, renderLayer);
		generate_slab_files();
	}

	protected void stairs() {
		Block block = register(append("_stairs"), StairsBlockFactory.create(base.getDefaultState(), settings));
		if(FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT)
			BlockRenderLayerMap.INSTANCE.putBlock(block, renderLayer);
		generate_stairs_files();
	}

	protected void fence() {
		Block block = register(append("_fence"), new FenceBlock(settings));
		if(FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT)
			BlockRenderLayerMap.INSTANCE.putBlock(block, renderLayer);
		generate_fence_files();
	}

	protected void wall() {
		Block block = register(append("_wall"), new WallBlock(settings));
		if(FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT)
			BlockRenderLayerMap.INSTANCE.putBlock(block, renderLayer);
		generate_wall_files();
	}

	protected void pane() {
		Block block = register(append("_pane"), PaneBlockFactory.create(settings));
		if(FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT)
			BlockRenderLayerMap.INSTANCE.putBlock(block, renderLayer);
		generate_pane_files();
	}

	protected Identifier blockpath() {
		return new Identifier(base_id.getNamespace(), "block/" + base_id.getPath());
	}

	protected Identifier itempath() {
		return new Identifier(base_id.getNamespace(), "item/" + base_id.getPath());
	}

	protected Identifier bappend(String suffix) {
		return new Identifier(prefix.getNamespace(), "block/" + prefix.getPath() + suffix);
	}

	protected Identifier iappend(String suffix) {
		return new Identifier(prefix.getNamespace(), "item/" + prefix.getPath() + suffix);
	}

	protected void parrot_parent(Identifier parent, Identifier parrot) {
		ExtraThings.RESOURCE_PACK.addModel(JModel.modelKeepElements(parent.toString()), parrot);
	}

	protected void generate_base_files() {
		JVariant variant = new JVariant();
		variant.put("", new JBlockModel(blockpath()));
		ExtraThings.RESOURCE_PACK.addBlockState(JState.state(variant), base_id);


		JModel block = JModel.modelKeepElements("minecraft:block/cube_all");
		block.textures(JModel.textures().var("all", blockpath().toString()));
		ExtraThings.RESOURCE_PACK.addModel(block, blockpath());

		if(itemSettings != null) {
			parrot_parent(blockpath(), itempath());
		}
	}

	protected void generate_slab_files() {
		JVariant variant = new JVariant();
		variant.put("type=bottom", new JBlockModel(bappend("_slab")));
		variant.put("type=double", new JBlockModel(blockpath()));
		variant.put("type=top", new JBlockModel(bappend("_slab_top")));
		ExtraThings.RESOURCE_PACK.addBlockState(JState.state(variant), append("_slab"));

		JModel template = JModel.modelKeepElements();
		JTextures textures = JModel.textures();
		textures.var("bottom", blockpath().toString());
		textures.var("top", blockpath().toString());
		textures.var("side", blockpath().toString());
		template.textures(textures);
		ExtraThings.RESOURCE_PACK.addModel(template.clone().parent("minecraft:block/slab_top"), bappend("_slab_top"));
		ExtraThings.RESOURCE_PACK.addModel(template.clone().parent("minecraft:block/slab"), bappend("_slab"));

		if(itemSettings != null) {
			parrot_parent(bappend("_slab"), iappend("_slab"));
		}
	}

	protected void generate_stairs_files() {
		JVariant variant = new JVariant();
		variant.put("facing=east,half=bottom,shape=inner_left", JState.model(bappend("_stairs_inner")).y(270).uvlock());
		variant.put("facing=east,half=bottom,shape=inner_right", JState.model(bappend("_stairs_inner")));
		variant.put("facing=east,half=bottom,shape=outer_left", JState.model(bappend("_stairs_outer")).y(270).uvlock());
		variant.put("facing=east,half=bottom,shape=outer_right", JState.model(bappend("_stairs_outer")));
		variant.put("facing=east,half=bottom,shape=straight", JState.model(bappend("_stairs")));

		variant.put("facing=east,half=top,shape=inner_left", JState.model(bappend("_stairs_inner")).x(180).y(0).uvlock());
		variant.put("facing=east,half=top,shape=inner_right", JState.model(bappend("_stairs_inner")).x(180).y(90).uvlock());
		variant.put("facing=east,half=top,shape=outer_left", JState.model(bappend("_stairs_outer")).x(180).y(0).uvlock());
		variant.put("facing=east,half=top,shape=outer_right", JState.model(bappend("_stairs_outer")).x(180).y(90).uvlock());
		variant.put("facing=east,half=top,shape=straight", JState.model(bappend("_stairs")).x(180).y(0).uvlock());

		variant.put("facing=north,half=bottom,shape=inner_left", JState.model(bappend("_stairs_inner")).y(180).uvlock());
		variant.put("facing=north,half=bottom,shape=inner_right", JState.model(bappend("_stairs_inner")).y(270).uvlock());
		variant.put("facing=north,half=bottom,shape=outer_left", JState.model(bappend("_stairs_outer")).y(180).uvlock());
		variant.put("facing=north,half=bottom,shape=outer_right", JState.model(bappend("_stairs_outer")).y(270).uvlock());
		variant.put("facing=north,half=bottom,shape=straight", JState.model(bappend("_stairs")).y(270).uvlock());

		variant.put("facing=north,half=top,shape=inner_left", JState.model(bappend("_stairs_inner")).x(180).y(270).uvlock());
		variant.put("facing=north,half=top,shape=inner_right", JState.model(bappend("_stairs_inner")).x(180).uvlock());
		variant.put("facing=north,half=top,shape=outer_left", JState.model(bappend("_stairs_outer")).x(180).y(270).uvlock());
		variant.put("facing=north,half=top,shape=outer_right", JState.model(bappend("_stairs_outer")).x(180).uvlock());
		variant.put("facing=north,half=top,shape=straight", JState.model(bappend("_stairs")).x(180).y(270).uvlock());

		variant.put("facing=south,half=bottom,shape=inner_left", JState.model(bappend("_stairs_inner")));
		variant.put("facing=south,half=bottom,shape=inner_right", JState.model(bappend("_stairs_inner")).y(90).uvlock());
		variant.put("facing=south,half=bottom,shape=outer_left", JState.model(bappend("_stairs_outer")));
		variant.put("facing=south,half=bottom,shape=outer_right", JState.model(bappend("_stairs_outer")).y(90).uvlock());
		variant.put("facing=south,half=bottom,shape=straight", JState.model(bappend("_stairs")).y(90).uvlock());

		variant.put("facing=south,half=top,shape=inner_left", JState.model(bappend("_stairs_inner")).x(180).y(90).uvlock());
		variant.put("facing=south,half=top,shape=inner_right", JState.model(bappend("_stairs_inner")).x(180).y(180).uvlock());
		variant.put("facing=south,half=top,shape=outer_left", JState.model(bappend("_stairs_outer")).x(180).y(90).uvlock());
		variant.put("facing=south,half=top,shape=outer_right", JState.model(bappend("_stairs_outer")).x(180).y(180).uvlock());
		variant.put("facing=south,half=top,shape=straight", JState.model(bappend("_stairs")).x(180).y(90).uvlock());

		variant.put("facing=west,half=bottom,shape=inner_left", JState.model(bappend("_stairs_inner")).y(90).uvlock());
		variant.put("facing=west,half=bottom,shape=inner_right", JState.model(bappend("_stairs_inner")).y(180).uvlock());
		variant.put("facing=west,half=bottom,shape=outer_left", JState.model(bappend("_stairs_outer")).y(90).uvlock());
		variant.put("facing=west,half=bottom,shape=outer_right", JState.model(bappend("_stairs_outer")).y(180).uvlock());
		variant.put("facing=west,half=bottom,shape=straight", JState.model(bappend("_stairs")).y(180).uvlock());

		variant.put("facing=west,half=top,shape=inner_left", JState.model(bappend("_stairs_inner")).x(180).y(180).uvlock());
		variant.put("facing=west,half=top,shape=inner_right", JState.model(bappend("_stairs_inner")).x(180).y(270).uvlock());
		variant.put("facing=west,half=top,shape=outer_left", JState.model(bappend("_stairs_outer")).x(180).y(180).uvlock());
		variant.put("facing=west,half=top,shape=outer_right", JState.model(bappend("_stairs_outer")).x(180).y(270).uvlock());
		variant.put("facing=west,half=top,shape=straight", JState.model(bappend("_stairs")).x(180).y(180).uvlock());

		ExtraThings.RESOURCE_PACK.addBlockState(JState.state(variant), append("_stairs"));

		JModel template = JModel.modelKeepElements();
		JTextures textures = JModel.textures();
		textures.var("bottom", blockpath().toString());
		textures.var("top", blockpath().toString());
		textures.var("side", blockpath().toString());
		template.textures(textures);
		ExtraThings.RESOURCE_PACK.addModel(template.clone().parent("minecraft:block/stairs"), bappend("_stairs"));
		ExtraThings.RESOURCE_PACK.addModel(template.clone().parent("minecraft:block/inner_stairs"), bappend("_stairs_inner"));
		ExtraThings.RESOURCE_PACK.addModel(template.clone().parent("minecraft:block/outer_stairs"), bappend("_stairs_outer"));

		if(itemSettings != null) {
			parrot_parent(bappend("_stairs"), iappend("_stairs"));
		}
	}

	protected void generate_wall_files() {
		JState state = JState.state();
		state.add(JState.multipart(JState.model(bappend("_wall_post"))).when(JState.when().add("up", "true")));
		state.add(JState.multipart(JState.model(bappend("_wall_side")).uvlock()).when(JState.when().add("north", "low")));
		state.add(JState.multipart(JState.model(bappend("_wall_side")).y(90).uvlock()).when(JState.when().add("east", "low")));
		state.add(JState.multipart(JState.model(bappend("_wall_side")).y(180).uvlock()).when(JState.when().add("south", "low")));
		state.add(JState.multipart(JState.model(bappend("_wall_side")).y(270).uvlock()).when(JState.when().add("west", "low")));
		state.add(JState.multipart(JState.model(bappend("_wall_side_tall")).uvlock()).when(JState.when().add("north", "tall")));
		state.add(JState.multipart(JState.model(bappend("_wall_side_tall")).y(90).uvlock()).when(JState.when().add("east", "tall")));
		state.add(JState.multipart(JState.model(bappend("_wall_side_tall")).y(180).uvlock()).when(JState.when().add("south", "tall")));
		state.add(JState.multipart(JState.model(bappend("_wall_side_tall")).y(270).uvlock()).when(JState.when().add("west", "tall")));
		ExtraThings.RESOURCE_PACK.addBlockState(state, append("_wall"));

		JModel template = JModel.modelKeepElements();
		JTextures textures = JModel.textures();
		textures.var("wall", blockpath().toString());
		template.textures(textures);
		ExtraThings.RESOURCE_PACK.addModel(template.clone().parent("minecraft:block/template_wall_side_tall"), bappend("_wall_side_tall"));
		ExtraThings.RESOURCE_PACK.addModel(template.clone().parent("minecraft:block/template_wall_side"), bappend("_wall_side"));
		ExtraThings.RESOURCE_PACK.addModel(template.clone().parent("minecraft:block/template_wall_post"), bappend("_wall_post"));
		ExtraThings.RESOURCE_PACK.addModel(template.clone().parent("minecraft:block/wall_inventory"), bappend("_wall_inventory"));

		if(itemSettings != null) {
			parrot_parent(bappend("_wall_inventory"), iappend("_wall"));
		}
	}

	protected void generate_fence_files() {
		JState state = JState.state();
		state.add(JState.multipart(JState.model(bappend("_fence_post"))));
		state.add(JState.multipart(JState.model(bappend("_fence_side")).uvlock()).when(JState.when().add("north", "true")));
		state.add(JState.multipart(JState.model(bappend("_fence_side")).y(90).uvlock()).when(JState.when().add("east", "true")));
		state.add(JState.multipart(JState.model(bappend("_fence_side")).y(180).uvlock()).when(JState.when().add("south", "true")));
		state.add(JState.multipart(JState.model(bappend("_fence_side")).y(270).uvlock()).when(JState.when().add("west", "true")));
		ExtraThings.RESOURCE_PACK.addBlockState(state, append("_fence"));

		JModel template = JModel.modelKeepElements();
		JTextures textures = JModel.textures();
		textures.var("texture", blockpath().toString());
		template.textures(textures);
		ExtraThings.RESOURCE_PACK.addModel(template.clone().parent("minecraft:block/fence_side"), bappend("_fence_side"));
		ExtraThings.RESOURCE_PACK.addModel(template.clone().parent("minecraft:block/fence_post"), bappend("_fence_post"));
		ExtraThings.RESOURCE_PACK.addModel(template.clone().parent("minecraft:block/fence_inventory"), bappend("_fence_inventory"));

		if(itemSettings != null) {
			parrot_parent(bappend("_fence_inventory"), iappend("_fence"));
		}
	}

	protected void generate_pane_files() {
		JState state = JState.state();
		state.add(JState.multipart(JState.model(bappend("_pane_post"))));
		state.add(JState.multipart(JState.model(bappend("_pane_side"))).when(JState.when().add("north", "true")));
		state.add(JState.multipart(JState.model(bappend("_pane_side")).y(90)).when(JState.when().add("east", "true")));
		state.add(JState.multipart(JState.model(bappend("_pane_side_alt"))).when(JState.when().add("south", "true")));
		state.add(JState.multipart(JState.model(bappend("_pane_side_alt")).y(90)).when(JState.when().add("west", "true")));
		state.add(JState.multipart(JState.model(bappend("_pane_noside"))).when(JState.when().add("north", "false")));
		state.add(JState.multipart(JState.model(bappend("_pane_noside_alt"))).when(JState.when().add("east", "false")));
		state.add(JState.multipart(JState.model(bappend("_pane_noside_alt")).y(90)).when(JState.when().add("south", "false")));
		state.add(JState.multipart(JState.model(bappend("_pane_noside")).y(270)).when(JState.when().add("west", "false")));
		ExtraThings.RESOURCE_PACK.addBlockState(state, append("_pane"));

		JModel templateOne = JModel.modelKeepElements();
		JTextures texturesOne = JModel.textures();
		texturesOne.var("pane", blockpath().toString());
		templateOne.textures(texturesOne);
		ExtraThings.RESOURCE_PACK.addModel(templateOne.clone().parent("minecraft:block/template_glass_pane_noside_alt"), bappend("_pane_noside_alt"));
		ExtraThings.RESOURCE_PACK.addModel(templateOne.clone().parent("minecraft:block/template_glass_pane_noside"), bappend("_pane_noside"));

		JModel templateTwo = JModel.modelKeepElements();
		JTextures texturesTwo = JModel.textures();
		texturesTwo.var("pane", blockpath().toString());
		texturesTwo.var("edge", bappend("_pane_top").toString());
		templateTwo.textures(texturesTwo);
		ExtraThings.RESOURCE_PACK.addModel(templateTwo.clone().parent("minecraft:block/template_glass_pane_post"), bappend("_pane_post"));
		ExtraThings.RESOURCE_PACK.addModel(templateTwo.clone().parent("minecraft:block/template_glass_pane_side"), bappend("_pane_side"));
		ExtraThings.RESOURCE_PACK.addModel(templateTwo.clone().parent("minecraft:block/template_glass_pane_side_alt"), bappend("_pane_side_alt"));

		if(itemSettings != null) {
			parrot_parent(bappend("_fence_inventory"), iappend("_fence"));
		}
	}

	protected enum BlockType {
		BASE,
		SLAB,
		STAIRS,
		WALL,
		FENCE,
		PANE;
	}

	public static class Builder {
		protected EnumSet<BlockType> blocks;
		protected Item.Settings itemSettings = null;
		protected AbstractBlock.Settings blockSettings = null;
		protected Block block = null;
		protected Identifier blockId = null;

		@Environment(EnvType.CLIENT)
		protected RenderLayer renderLayer = null;

		protected Builder() {
			blocks = EnumSet.of(BlockType.BASE);
		}

		public Builder slab() {
			blocks.add(BlockType.SLAB);
			return this;
		}

		public Builder stairs() {
			blocks.add(BlockType.STAIRS);
			return this;
		}

		public Builder wall() {
			blocks.add(BlockType.WALL);
			return this;
		}

		public Builder fence() {
			blocks.add(BlockType.FENCE);
			return this;
		}

		public Builder pane() {
			blocks.add(BlockType.PANE);
			return this;
		}

		public Builder solid() {
			if(FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT)
				renderLayer = RenderLayer.getSolid();
			return this;
		}

		public Builder transparent() {
			if(FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT)
				renderLayer = RenderLayer.getCutout();
			return this;
		}

		public Builder translucent() {
			if(FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT)
				renderLayer = RenderLayer.getTranslucent();
			return this;
		}

		public Builder item(Item.Settings settings) {
			itemSettings = settings;
			return this;
		}

		public Builder item(ItemGroup itemGroup) {
			itemSettings = new FabricItemSettings().group(itemGroup);
			return this;
		}

		protected Builder base(Identifier blockId, AbstractBlock.Settings blockSettings) {
			this.blockId = blockId;
			this.blockSettings = blockSettings;
			return this;
		}

		protected Builder base(Block block) {
			this.block = block;
			return this;
		}

		void build(String prefix) {
			this.build(ExtraThings.id(prefix));
		}

		void build() {
			assert this.block != null || (this.blockId != null && this.blockSettings != null);
			if(this.blockId != null) build(this.blockId);
			else build(ExtraThings.id(Registry.BLOCK.getId(this.block).getPath()));
		}

		public void build(Identifier prefix) {
			assert this.block != null || (this.blockId != null && this.blockSettings != null);
			BlockExtension be;
			if(this.block != null)
				be = new BlockExtension(prefix, this.block, itemSettings, this.renderLayer);
			else
				be = new BlockExtension(prefix, this.blockId, this.blockSettings, itemSettings, this.renderLayer);
			if(blocks.contains(BlockType.SLAB)) be.slab();
			if(blocks.contains(BlockType.STAIRS)) be.stairs();
			if(blocks.contains(BlockType.WALL)) be.wall();
			if(blocks.contains(BlockType.FENCE)) be.fence();
			if(blocks.contains(BlockType.PANE)) be.pane();

		}

	}

}

