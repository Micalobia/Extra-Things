package dev.micalobia.extra_things.mixin.block;

import dev.micalobia.extra_things.tag.ModdedBlockTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.NyliumBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(NyliumBlock.class)
public class NyliumBlockMixin {
	@Redirect(method = "grow", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z", ordinal = 0))
	private boolean changeCrimsonCheck(BlockState state, Block block) {
		return state.isIn(ModdedBlockTags.CRIMSON_NYLIUM);
	}

	@Redirect(method = "grow", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z", ordinal = 1))
	private boolean changeWarpedCheck(BlockState state, Block block) {
		return state.isIn(ModdedBlockTags.WARPED_NYLIUM);
	}
}
