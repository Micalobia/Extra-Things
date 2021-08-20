package dev.micalobia.extra_things.mixin.recipe;

import net.minecraft.recipe.CuttingRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(CuttingRecipe.Serializer.class)
public interface CuttingRecipeSerializerFactory {
	@Invoker("<init>")
	static <T extends CuttingRecipe> CuttingRecipe.Serializer<T> create(CuttingRecipe.Serializer.RecipeFactory<T> factory) {
		throw new AssertionError();
	}
}
