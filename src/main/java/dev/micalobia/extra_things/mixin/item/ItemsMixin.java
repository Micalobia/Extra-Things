package dev.micalobia.extra_things.mixin.item;

import dev.micalobia.extra_things.block.enums.NetherColor;
import dev.micalobia.extra_things.items.BlockStateItem;
import dev.micalobia.extra_things.block.INetherBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


// TODO: MAKE AN EVENT FOR INTERCEPTING register(Block) + ALIASES


@Mixin(Items.class)
public abstract class ItemsMixin {
	@Shadow
	private static Item register(BlockItem item) {
		return null;
	}

	@Inject(method = "register(Lnet/minecraft/block/Block;Lnet/minecraft/item/ItemGroup;)Lnet/minecraft/item/Item;", at = @At("HEAD"), cancellable = true)
	private static void registerNetherBlocksDifferently(Block block, ItemGroup group, CallbackInfoReturnable<Item> cir) {
		if(block instanceof INetherBlock) {
			cir.setReturnValue(
					register(new BlockStateItem<>(
							block,
							new FabricItemSettings().group(group),
							INetherBlock.COLOR,
							NetherColor.RED, NetherColor.BLUE
					)));
		}
	}
}
