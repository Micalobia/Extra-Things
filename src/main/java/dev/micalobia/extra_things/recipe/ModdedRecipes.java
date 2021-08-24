package dev.micalobia.extra_things.recipe;

import dev.micalobia.extra_things.ExtraThings;
import dev.micalobia.extra_things.mixin.recipe.CuttingRecipeSerializerFactory;
import net.minecraft.recipe.CookingRecipeSerializer;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.registry.Registry;

public class ModdedRecipes {
	public static final RecipeSerializer<LumbermillRecipe> LUMBERMILL_SERIALIZER;
	public static final RecipeSerializer<FiringRecipe> FIRING_SERIALIZER;
	public static final RecipeType<LumbermillRecipe> LUMBERMILL;
	public static final RecipeType<FiringRecipe> FIRING;

	static {
		LUMBERMILL_SERIALIZER = register_serializer("lumbermill", CuttingRecipeSerializerFactory.create(LumbermillRecipe::new));
		FIRING_SERIALIZER = register_serializer("firing", new CookingRecipeSerializer<>(FiringRecipe::new, 100));
		LUMBERMILL = register_type("lumbermill");
		FIRING = register_type("firing");
	}

	static <S extends RecipeSerializer<T>, T extends Recipe<?>> S register_serializer(String id, S serializer) {
		return Registry.register(Registry.RECIPE_SERIALIZER, ExtraThings.id(id), serializer);
	}

	static <T extends Recipe<?>> RecipeType<T> register_type(final String id) {
		return Registry.register(Registry.RECIPE_TYPE, ExtraThings.id(id), new RecipeType<T>() {
			public String toString() {
				return id;
			}
		});
	}

	public static void init() {
	}
}
