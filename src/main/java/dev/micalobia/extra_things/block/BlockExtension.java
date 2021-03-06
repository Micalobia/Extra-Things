package dev.micalobia.extra_things.block;

import dev.micalobia.extra_things.ExtraThings;
import dev.micalobia.extra_things.mixin.block.PaneBlockFactory;
import dev.micalobia.extra_things.mixin.block.StairsBlockFactory;
import net.devtech.arrp.json.blockstate.JBlockModel;
import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.blockstate.JVariant;
import net.devtech.arrp.json.models.JModel;
import net.devtech.arrp.json.models.JTextures;
import net.devtech.arrp.json.recipe.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class BlockExtension {
	protected Block base;
	protected Identifier base_id;
	protected Identifier prefix;
	protected AbstractBlock.Settings settings;
	protected @Nullable Item.Settings itemSettings;
	protected boolean generateRecipes;
	protected Collection<Block> isCutFrom;

	@Environment(EnvType.CLIENT)
	protected RenderLayer renderLayer;

	protected BlockExtension(Identifier prefix, @Nullable Item.Settings itemSettings) {
		this.base_id = null;
		this.prefix = prefix;
		this.itemSettings = itemSettings;
		this.isCutFrom = Collections.emptyList();
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

	protected void base(Identifier id, AbstractBlock.Settings settings) {
		this.base_id = id;
		this.base = register(id, new Block(settings));
		this.settings = settings;
		generate_base_files();
	}

	protected void base(Block block) {
		this.base_id = Registry.BLOCK.getId(block);
		this.base = block;
		this.settings = AbstractBlock.Settings.copy(block);
	}

	protected void slab(@Nullable Identifier id) {
		Block block = register(append("_slab"), new SlabBlock(settings));
		if(ExtraThings.onClient())
			BlockRenderLayerMap.INSTANCE.putBlock(block, renderLayer);
		generate_slab_files(id);
	}

	protected void stairs(@Nullable Identifier id) {
		Block block = register(append("_stairs"), StairsBlockFactory.create(base.getDefaultState(), settings));
		if(ExtraThings.onClient())
			BlockRenderLayerMap.INSTANCE.putBlock(block, renderLayer);
		generate_stairs_files(id);
	}

	protected void fence(@Nullable Identifier id) {
		Block block = register(append("_fence"), new FenceBlock(settings));
		if(ExtraThings.onClient())
			BlockRenderLayerMap.INSTANCE.putBlock(block, renderLayer);
		generate_fence_files(id);
	}

	protected void wall(@Nullable Identifier id) {
		Block block = register(append("_wall"), new WallBlock(settings));
		if(ExtraThings.onClient())
			BlockRenderLayerMap.INSTANCE.putBlock(block, renderLayer);
		generate_wall_files(id);
	}

	protected void pane(@Nullable Identifier id) {
		Block block = register(append("_pane"), PaneBlockFactory.create(settings));
		if(ExtraThings.onClient())
			BlockRenderLayerMap.INSTANCE.putBlock(block, renderLayer);
		generate_pane_files(id);
	}

	protected void recipes() {
		this.generateRecipes = true;
	}

	protected void isCutFrom(Collection<Block> blocks) {
		isCutFrom = blocks;
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

	protected <T> T default_if_null(@Nullable T maybeNull, T defaultValue) {
		return maybeNull == null ? defaultValue : maybeNull;
	}

	protected void generate_base_files() {
		JVariant variant = new JVariant();
		variant.put("", new JBlockModel(blockpath()));
		ExtraThings.RESOURCE_PACK.addBlockState(JState.state(variant), base_id);


		JModel model = JModel.modelKeepElements("minecraft:block/cube_all");
		model.textures(JModel.textures().var("all", blockpath().toString()));
		ExtraThings.RESOURCE_PACK.addModel(model, blockpath());

		if(itemSettings != null) {
			parrot_parent(blockpath(), itempath());
			if(generateRecipes) {
				Identifier cutId;
				JIngredient base;
				JStackedResult result = JResult.stackedResult(base_id.toString(), 1);

				for(Block block : isCutFrom) {
					base = JIngredient.ingredient().item(block.asItem());
					cutId = new Identifier(prefix.getNamespace(), base_id.getPath() + "_from_" + Registry.ITEM.getId(block.asItem()).getPath() + "_stonecutting");
					ExtraThings.RESOURCE_PACK.addRecipe(cutId, JRecipe.stonecutting(base, result));
				}
			}
		}
	}

	protected void generate_slab_files(@Nullable Identifier id) {
		JVariant variant = new JVariant();
		variant.put("type=bottom", new JBlockModel(bappend("_slab")));
		variant.put("type=double", new JBlockModel(blockpath()));
		variant.put("type=top", new JBlockModel(bappend("_slab_top")));
		ExtraThings.RESOURCE_PACK.addBlockState(JState.state(variant), append("_slab"));

		JModel template = JModel.modelKeepElements();
		JTextures textures = JModel.textures();
		textures.var("bottom", default_if_null(id, blockpath()).toString());
		textures.var("top", default_if_null(id, blockpath()).toString());
		textures.var("side", default_if_null(id, blockpath()).toString());
		template.textures(textures);
		ExtraThings.RESOURCE_PACK.addModel(template.clone().parent("minecraft:block/slab_top"), bappend("_slab_top"));
		ExtraThings.RESOURCE_PACK.addModel(template.clone().parent("minecraft:block/slab"), bappend("_slab"));

		if(itemSettings != null) {
			parrot_parent(bappend("_slab"), iappend("_slab"));
			if(generateRecipes) {
				JIngredient base = JIngredient.ingredient().item(this.base.asItem());
				JKeys key = JKeys.keys().key("#", base);

				JPattern pattern = JPattern.pattern("###");
				JStackedResult result = JResult.stackedResult(append("_slab").toString(), 6);
				ExtraThings.RESOURCE_PACK.addRecipe(append("_slab"), JRecipe.shaped(pattern, key, result));

				Identifier cutId = append("_slab_from_" + base_id.getPath() + "_stonecutting");
				result = JResult.stackedResult(append("_slab").toString(), 2);
				ExtraThings.RESOURCE_PACK.addRecipe(cutId, JRecipe.stonecutting(base, result));

				for(Block block : isCutFrom) {
					base = JIngredient.ingredient().item(block.asItem());
					cutId = append("_slab_from_" + Registry.ITEM.getId(block.asItem()).getPath() + "_stonecutting");
					ExtraThings.RESOURCE_PACK.addRecipe(cutId, JRecipe.stonecutting(base, result));
				}
			}
		}
	}

	protected void generate_stairs_files(@Nullable Identifier id) {
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
		textures.var("bottom", default_if_null(id, blockpath()).toString());
		textures.var("top", default_if_null(id, blockpath()).toString());
		textures.var("side", default_if_null(id, blockpath()).toString());
		template.textures(textures);
		ExtraThings.RESOURCE_PACK.addModel(template.clone().parent("minecraft:block/stairs"), bappend("_stairs"));
		ExtraThings.RESOURCE_PACK.addModel(template.clone().parent("minecraft:block/inner_stairs"), bappend("_stairs_inner"));
		ExtraThings.RESOURCE_PACK.addModel(template.clone().parent("minecraft:block/outer_stairs"), bappend("_stairs_outer"));

		if(itemSettings != null) {
			parrot_parent(bappend("_stairs"), iappend("_stairs"));
			if(generateRecipes) {
				JIngredient base = JIngredient.ingredient().item(this.base.asItem());
				JKeys key = JKeys.keys().key("#", base);

				JPattern pattern = JPattern.pattern("#  ", "## ", "###");
				JStackedResult result = JResult.stackedResult(append("_stairs").toString(), 4);
				ExtraThings.RESOURCE_PACK.addRecipe(append("_stairs"), JRecipe.shaped(pattern, key, result));

				Identifier cutId = append("_stairs_from_" + base_id.getPath() + "_stonecutting");
				result = JResult.stackedResult(append("_stairs").toString(), 1);
				ExtraThings.RESOURCE_PACK.addRecipe(cutId, JRecipe.stonecutting(base, result));

				for(Block block : isCutFrom) {
					base = JIngredient.ingredient().item(block.asItem());
					cutId = append("_stairs_from_" + Registry.ITEM.getId(block.asItem()).getPath() + "_stonecutting");
					ExtraThings.RESOURCE_PACK.addRecipe(cutId, JRecipe.stonecutting(base, result));
				}
			}
		}
	}

	protected void generate_wall_files(@Nullable Identifier id) {
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
		textures.var("wall", default_if_null(id, blockpath()).toString());
		template.textures(textures);
		ExtraThings.RESOURCE_PACK.addModel(template.clone().parent("minecraft:block/template_wall_side_tall"), bappend("_wall_side_tall"));
		ExtraThings.RESOURCE_PACK.addModel(template.clone().parent("minecraft:block/template_wall_side"), bappend("_wall_side"));
		ExtraThings.RESOURCE_PACK.addModel(template.clone().parent("minecraft:block/template_wall_post"), bappend("_wall_post"));
		ExtraThings.RESOURCE_PACK.addModel(template.clone().parent("minecraft:block/wall_inventory"), bappend("_wall_inventory"));

		if(itemSettings != null) {
			parrot_parent(bappend("_wall_inventory"), iappend("_wall"));
			if(generateRecipes) {
				JIngredient base = JIngredient.ingredient().item(this.base.asItem());
				JKeys key = JKeys.keys().key("#", base);

				JPattern pattern = JPattern.pattern("###", "###");
				JStackedResult result = JResult.stackedResult(append("_wall").toString(), 6);
				ExtraThings.RESOURCE_PACK.addRecipe(append("_wall"), JRecipe.shaped(pattern, key, result));

				Identifier cutId = append("_wall_from_" + base_id.getPath() + "_stonecutting");
				result = JResult.stackedResult(append("_wall").toString(), 1);
				ExtraThings.RESOURCE_PACK.addRecipe(cutId, JRecipe.stonecutting(base, result));

				for(Block block : isCutFrom) {
					base = JIngredient.ingredient().item(block.asItem());
					cutId = append("_wall_from_" + Registry.ITEM.getId(block.asItem()).getPath() + "_stonecutting");
					ExtraThings.RESOURCE_PACK.addRecipe(cutId, JRecipe.stonecutting(base, result));
				}
			}
		}
	}

	protected void generate_fence_files(@Nullable Identifier id) {
		JState state = JState.state();
		state.add(JState.multipart(JState.model(bappend("_fence_post"))));
		state.add(JState.multipart(JState.model(bappend("_fence_side")).uvlock()).when(JState.when().add("north", "true")));
		state.add(JState.multipart(JState.model(bappend("_fence_side")).y(90).uvlock()).when(JState.when().add("east", "true")));
		state.add(JState.multipart(JState.model(bappend("_fence_side")).y(180).uvlock()).when(JState.when().add("south", "true")));
		state.add(JState.multipart(JState.model(bappend("_fence_side")).y(270).uvlock()).when(JState.when().add("west", "true")));
		ExtraThings.RESOURCE_PACK.addBlockState(state, append("_fence"));

		JModel template = JModel.modelKeepElements();
		JTextures textures = JModel.textures();
		textures.var("texture", default_if_null(id, blockpath()).toString());
		template.textures(textures);
		ExtraThings.RESOURCE_PACK.addModel(template.clone().parent("minecraft:block/fence_side"), bappend("_fence_side"));
		ExtraThings.RESOURCE_PACK.addModel(template.clone().parent("minecraft:block/fence_post"), bappend("_fence_post"));
		ExtraThings.RESOURCE_PACK.addModel(template.clone().parent("minecraft:block/fence_inventory"), bappend("_fence_inventory"));

		if(itemSettings != null) {
			parrot_parent(bappend("_fence_inventory"), iappend("_fence"));
		}
	}

	protected void generate_pane_files(@Nullable Identifier id) {
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
		texturesOne.var("pane", default_if_null(id, blockpath()).toString());
		templateOne.textures(texturesOne);
		ExtraThings.RESOURCE_PACK.addModel(templateOne.clone().parent("minecraft:block/template_glass_pane_noside_alt"), bappend("_pane_noside_alt"));
		ExtraThings.RESOURCE_PACK.addModel(templateOne.clone().parent("minecraft:block/template_glass_pane_noside"), bappend("_pane_noside"));

		JModel templateTwo = JModel.modelKeepElements();
		JTextures texturesTwo = JModel.textures();
		texturesTwo.var("pane", default_if_null(id, blockpath()).toString());
		texturesTwo.var("edge", default_if_null(id, bappend("_pane_top")).toString());
		templateTwo.textures(texturesTwo);
		ExtraThings.RESOURCE_PACK.addModel(templateTwo.clone().parent("minecraft:block/template_glass_pane_post"), bappend("_pane_post"));
		ExtraThings.RESOURCE_PACK.addModel(templateTwo.clone().parent("minecraft:block/template_glass_pane_side"), bappend("_pane_side"));
		ExtraThings.RESOURCE_PACK.addModel(templateTwo.clone().parent("minecraft:block/template_glass_pane_side_alt"), bappend("_pane_side_alt"));

		if(itemSettings != null) {
			parrot_parent(bappend("_pane_inventory"), iappend("_pane"));
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
		protected Map<BlockType, Identifier> customTextures;
		protected boolean includeRecipes;
		protected Collection<Block> cutFrom;

		@Environment(EnvType.CLIENT)
		protected RenderLayer renderLayer = null;

		protected Builder() {
			blocks = EnumSet.of(BlockType.BASE);
			customTextures = new HashMap<>();
			includeRecipes = true;
			cutFrom = Collections.emptyList();
		}

		protected Builder generic(BlockType type) {
			blocks.add(type);
			return this;
		}

		protected Builder generic(BlockType type, Identifier customTexture) {
			blocks.add(type);
			customTextures.put(type, customTexture);
			return this;
		}

		protected Builder generic(BlockType type, String customTexture) {
			blocks.add(type);
			customTextures.put(type, new Identifier(customTexture));
			return this;
		}

		public Builder slab() {
			return generic(BlockType.SLAB);
		}

		public Builder slab(Identifier custom_texture) {
			return generic(BlockType.SLAB, custom_texture);
		}

		public Builder slab(String custom_texture) {
			return generic(BlockType.SLAB, custom_texture);
		}

		public Builder stairs() {
			return generic(BlockType.STAIRS);
		}

		public Builder stairs(Identifier custom_texture) {
			return generic(BlockType.STAIRS, custom_texture);
		}

		public Builder stairs(String custom_texture) {
			return generic(BlockType.STAIRS, custom_texture);
		}

		public Builder wall() {
			return generic(BlockType.WALL);
		}

		public Builder wall(Identifier custom_texture) {
			return generic(BlockType.WALL, custom_texture);
		}

		public Builder wall(String custom_texture) {
			return generic(BlockType.WALL, custom_texture);
		}

		public Builder fence() {
			return generic(BlockType.FENCE);
		}

		public Builder fence(Identifier custom_texture) {
			return generic(BlockType.FENCE, custom_texture);
		}

		public Builder fence(String custom_texture) {
			return generic(BlockType.FENCE, custom_texture);
		}

		public Builder pane() {
			return generic(BlockType.PANE);
		}

		public Builder pane(Identifier custom_texture) {
			return generic(BlockType.PANE, custom_texture);
		}

		public Builder pane(String custom_texture) {
			return generic(BlockType.PANE, custom_texture);
		}

		public Builder isCutFrom(Block block) {
			cutFrom = List.of(block);
			return this;
		}

		public Builder isCutFrom(Block... blocks) {
			cutFrom = Arrays.asList(blocks);
			return this;
		}

		public Builder isCutFrom(Collection<Block> blocks) {
			cutFrom = blocks;
			return this;
		}

		public Builder noRecipes() {
			includeRecipes = false;
			return this;
		}

		public Builder solid() {
			if(ExtraThings.onClient())
				renderLayer = RenderLayer.getSolid();
			return this;
		}

		public Builder transparent() {
			if(ExtraThings.onClient())
				renderLayer = RenderLayer.getCutout();
			return this;
		}

		public Builder translucent() {
			if(ExtraThings.onClient())
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
			BlockExtension be = new BlockExtension(prefix, this.itemSettings);
			if(includeRecipes) {
				be.recipes();
				be.isCutFrom(cutFrom);
			}
			if(this.block != null)
				be.base(block);
			else
				be.base(blockId, blockSettings);
			if(ExtraThings.onClient()) {
				if(renderLayer != null) {
					be.setRenderLayer(renderLayer);
					if(block == null)
						BlockRenderLayerMap.INSTANCE.putBlock(be.base, renderLayer);
				} else
					be.setRenderLayer();
			}
			if(blocks.contains(BlockType.SLAB)) be.slab(customTextures.get(BlockType.SLAB));
			if(blocks.contains(BlockType.STAIRS)) be.stairs(customTextures.get(BlockType.STAIRS));
			if(blocks.contains(BlockType.WALL)) be.wall(customTextures.get(BlockType.WALL));
			if(blocks.contains(BlockType.FENCE)) be.fence(customTextures.get(BlockType.FENCE));
			if(blocks.contains(BlockType.PANE)) be.pane(customTextures.get(BlockType.PANE));
		}
	}
}

