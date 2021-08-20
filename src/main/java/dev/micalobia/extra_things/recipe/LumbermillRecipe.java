package dev.micalobia.extra_things.recipe;

import dev.micalobia.extra_things.block.ModdedBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.CuttingRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class LumbermillRecipe extends CuttingRecipe {
	public LumbermillRecipe(Identifier id, String group, Ingredient input, ItemStack output) {
		super(ModdedRecipes.LUMBERMILL, ModdedRecipes.LUMBERMILL_SERIALIZER, id, group, input, output);
	}

	@Override
	public boolean matches(Inventory inventory, World world) {
		return this.input.test(inventory.getStack(0));
	}

	@Override
	public ItemStack createIcon() {
		return new ItemStack(ModdedBlocks.LUMBERMILL);
	}
}
