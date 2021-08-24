package dev.micalobia.extra_things.mixin.client;

import dev.micalobia.extra_things.block.ModdedBlocks;
import dev.micalobia.extra_things.block.entity.ModdedBlockEntities;
import dev.micalobia.extra_things.block.entity.PotionCauldronBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

@Environment(EnvType.CLIENT)
@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
	private BlockEntity blockEntityCapture;

	@Shadow
	protected abstract ItemStack addBlockEntityNbt(ItemStack stack, BlockEntity blockEntity);

	private boolean isPotionCauldron() {
		return blockEntityCapture.getType() == ModdedBlockEntities.POTION_CAULDRON;
	}

	@Redirect(method = "doItemPick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/Screen;hasControlDown()Z"))
	private boolean redirectControlDown() {
		return true;
	}

	@Redirect(method = "doItemPick", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;hasBlockEntity()Z"))
	private boolean redirectHasBlockEntity(BlockState state) {
		if(state.isOf(ModdedBlocks.POTION_CAULDRON))
			return Screen.hasControlDown() || Screen.hasShiftDown();
		else
			return Screen.hasControlDown() && state.hasBlockEntity();
	}

	@ModifyVariable(method = "doItemPick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getInventory()Lnet/minecraft/entity/player/PlayerInventory;"))
	public BlockEntity storeBlockEntity(BlockEntity blockEntity) {
		return blockEntityCapture = blockEntity;
	}

	@ModifyVariable(method = "doItemPick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;addBlockEntityNbt(Lnet/minecraft/item/ItemStack;Lnet/minecraft/block/entity/BlockEntity;)Lnet/minecraft/item/ItemStack;"))
	public ItemStack changeItemStack(ItemStack itemStack3) {
		if(isPotionCauldron() && Screen.hasShiftDown()) {
			PotionCauldronBlockEntity entity = (PotionCauldronBlockEntity) blockEntityCapture;
			ItemStack potion = Items.POTION.getDefaultStack();
			PotionUtil.setPotion(potion, entity.getPotion());
			PotionUtil.setCustomPotionEffects(potion, entity.getCustomPotionEffects());
			return potion;
		}
		return itemStack3;
	}

	@Redirect(method = "doItemPick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;addBlockEntityNbt(Lnet/minecraft/item/ItemStack;Lnet/minecraft/block/entity/BlockEntity;)Lnet/minecraft/item/ItemStack;"))
	public ItemStack redirectAddBlockEntityNbt(MinecraftClient minecraftClient, ItemStack stack, BlockEntity blockEntity) {
		if(isPotionCauldron() && Screen.hasShiftDown()) return null;
		return addBlockEntityNbt(stack, blockEntity);
	}
}
