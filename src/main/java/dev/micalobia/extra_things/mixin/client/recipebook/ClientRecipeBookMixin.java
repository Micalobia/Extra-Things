package dev.micalobia.extra_things.mixin.client.recipebook;

import dev.micalobia.extra_things.client.recipebook.ModdedRecipeBookGroup;
import dev.micalobia.extra_things.recipe.ModdedRecipes;
import net.minecraft.client.recipebook.ClientRecipeBook;
import net.minecraft.client.recipebook.RecipeBookGroup;
import net.minecraft.item.BlockItem;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientRecipeBook.class)
public class ClientRecipeBookMixin {
    @Inject(method = "getGroupForRecipe", at = @At("HEAD"), cancellable = true)
    private static void getKilnRecipeGroup(Recipe<?> recipe, CallbackInfoReturnable<RecipeBookGroup> cir) {
        RecipeType<?> type = recipe.getType();
        if (type == ModdedRecipes.FIRING)
            cir.setReturnValue(recipe.getOutput().getItem() instanceof BlockItem ? ModdedRecipeBookGroup.KILN_BLOCKS : ModdedRecipeBookGroup.KILN_MISC);
    }
}
