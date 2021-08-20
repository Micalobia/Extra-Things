package dev.micalobia.extra_things;

import dev.micalobia.extra_things.block.ModdedBlocks;
import dev.micalobia.extra_things.block.cauldron.CauldronBehaviors;
import dev.micalobia.extra_things.block.entity.ModdedBlockEntities;
import dev.micalobia.extra_things.items.ModdedItems;
import dev.micalobia.extra_things.recipe.ModdedRecipes;
import dev.micalobia.extra_things.screen.ModdedScreenHandlers;
import net.devtech.arrp.api.RRPCallback;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExtraThings implements ModInitializer {

	public static final String MOD_ID = "extra_things";
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
	public static final RuntimeResourcePack RESOURCE_PACK = RuntimeResourcePack.create(MOD_ID + ":pack");

	static {
		RRPCallback.BEFORE_VANILLA.register(a -> a.add(RESOURCE_PACK));
	}

	public static Identifier id(String path) {
		return new Identifier("extra_things", path);
	}

	static <T extends Recipe<?>> RecipeType<T> register(final String id) {
		return Registry.register(Registry.RECIPE_TYPE, id(id), new RecipeType<T>() {
			public String toString() {
				return id;
			}
		});
	}

	static <S extends RecipeSerializer<T>, T extends Recipe<?>> S register(String id, S serializer) {
		return Registry.register(Registry.RECIPE_SERIALIZER, id, serializer);
	}

	public static boolean onClient() {
		return FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;
	}

	@Override
	public void onInitialize() {
		CauldronBehaviors.registerBehavior();
		ModdedBlocks.init();
		ModdedBlockEntities.init();
		ModdedItems.init();
		ModdedRecipes.init();
		ModdedScreenHandlers.init();
	}
}
