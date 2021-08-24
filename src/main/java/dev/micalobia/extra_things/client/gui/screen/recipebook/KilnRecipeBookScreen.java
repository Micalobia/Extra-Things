package dev.micalobia.extra_things.client.gui.screen.recipebook;

import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.client.gui.screen.recipebook.AbstractFurnaceRecipeBookScreen;
import net.minecraft.item.Item;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import java.util.Set;

public class KilnRecipeBookScreen extends AbstractFurnaceRecipeBookScreen {
	private static final Text LABEL = new TranslatableText("extra_things.gui.recipebook.toggleRecipes.firable");

	protected Text getToggleCraftableButtonText() {
		return LABEL;
	}

	@Override
	protected Set<Item> getAllowedFuels() {
		return AbstractFurnaceBlockEntity.createFuelTimeMap().keySet();
	}
}
