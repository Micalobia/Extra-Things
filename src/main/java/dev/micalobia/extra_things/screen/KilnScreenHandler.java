package dev.micalobia.extra_things.screen;

import dev.micalobia.extra_things.recipe.ModdedRecipes;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.screen.AbstractFurnaceScreenHandler;
import net.minecraft.screen.PropertyDelegate;

public class KilnScreenHandler extends AbstractFurnaceScreenHandler {
	public KilnScreenHandler(int syncId, PlayerInventory playerInventory) {
		super(ModdedScreenHandlers.KILN, ModdedRecipes.FIRING, RecipeBookCategory.FURNACE, syncId, playerInventory);
	}

	public KilnScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
		super(ModdedScreenHandlers.KILN, ModdedRecipes.FIRING, RecipeBookCategory.FURNACE, syncId, playerInventory, inventory, propertyDelegate);
	}
}
