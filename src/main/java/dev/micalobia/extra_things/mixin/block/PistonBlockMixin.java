package dev.micalobia.extra_things.mixin.block;

import dev.micalobia.extra_things.tag.BlockTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PistonBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PistonBlock.class)
public class PistonBlockMixin {
	@Redirect(method = "isMovable", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z", ordinal = 0))
	private static boolean changeHardcodedImmovable0(BlockState state, Block block) {
		return state.isIn(BlockTags.IMMOVABLE);
	}

	@Redirect(method = "isMovable", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z", ordinal = 1))
	private static boolean changeHardcodedImmovable1(BlockState state, Block block) {
		return false;
	}

	@Redirect(method = "isMovable", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z", ordinal = 2))
	private static boolean changeHardcodedImmovable2(BlockState state, Block block) {
		return false;
	}
}
