package dev.micalobia.extra_things.block.entity;

import dev.micalobia.extra_things.recipe.ModdedRecipes;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import org.apache.commons.lang3.NotImplementedException;

public class KilnBlockEntity extends AbstractFurnaceBlockEntity {
	public KilnBlockEntity(BlockPos pos, BlockState state) {
		super(ModdedBlockEntities.KILN, pos, state, ModdedRecipes.FIRING);
	}

	@Override
	protected Text getContainerName() {
		return new TranslatableText("extra_things.container.kiln");
	}

	@Override
	protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
		throw new NotImplementedException("TODO");
		//return new KilnScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
	}

	@Override
	protected int getFuelTime(ItemStack fuel) {
		return super.getFuelTime(fuel) / 2;
	}
}
