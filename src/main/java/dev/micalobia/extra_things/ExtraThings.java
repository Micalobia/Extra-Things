package dev.micalobia.extra_things;

import dev.micalobia.extra_things.block.Blocks;
import dev.micalobia.extra_things.items.Items;
import net.fabricmc.api.ModInitializer;
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

	static {
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

	@Override
	public void onInitialize() {
		Blocks.init();
		Items.init();
	}
}
