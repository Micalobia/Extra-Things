package dev.micalobia.extra_things.client.gui.screen.ingame;

import dev.micalobia.extra_things.ExtraThings;
import dev.micalobia.extra_things.client.gui.screen.recipebook.KilnRecipeBookScreen;
import dev.micalobia.extra_things.screen.KilnScreenHandler;
import net.minecraft.client.gui.screen.ingame.AbstractFurnaceScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class KilnScreen extends AbstractFurnaceScreen<KilnScreenHandler> {
	private static final Identifier TEXTURE = ExtraThings.id("textures/gui/container/kiln.png");

	public KilnScreen(KilnScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, new KilnRecipeBookScreen(), inventory, title, TEXTURE);
	}
}
