package dev.micalobia.extra_things.screen;

import dev.micalobia.extra_things.recipe.ModdedRecipes;
import dev.micalobia.extra_things.recipe.book.ModdedRecipeBookCategory;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.AbstractFurnaceScreenHandler;
import net.minecraft.screen.PropertyDelegate;

public class KilnScreenHandler extends AbstractFurnaceScreenHandler {
    // Why in the *world* is RecipeBookCategory an enum
    // Why is it even required here, the enum gets used in a few places that would work
    // *significantly* better as a class that stores all it's own properties that
    // packages RecipeBookCategory, RecipeBookGroup, and any related abstracts into one neat little thing
    public KilnScreenHandler(int syncId, PlayerInventory playerInventory) {
        super(ModdedScreenHandlers.KILN, ModdedRecipes.FIRING, ModdedRecipeBookCategory.KILN, syncId, playerInventory);
    }

    public KilnScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        super(ModdedScreenHandlers.KILN, ModdedRecipes.FIRING, ModdedRecipeBookCategory.KILN, syncId, playerInventory, inventory, propertyDelegate);
    }
}
