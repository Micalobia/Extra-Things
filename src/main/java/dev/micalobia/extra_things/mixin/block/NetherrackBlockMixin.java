package dev.micalobia.extra_things.mixin.block;

import dev.micalobia.extra_things.block.NetherBlocks;
import dev.micalobia.extra_things.tag.BlockTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.NetherrackBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Random;

@Mixin(NetherrackBlock.class)
public class NetherrackBlockMixin {
	@Redirect(method = "grow", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z", ordinal = 0))
	private boolean isWarped(BlockState state, Block block) {
		return state.isIn(BlockTags.WARPED_NYLIUM);
	}

	@Redirect(method = "grow", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z", ordinal = 1))
	private boolean isCrimson(BlockState state, Block block) {
		return state.isIn(BlockTags.CRIMSON_NYLIUM);
	}

	@Redirect(method = "grow", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;getDefaultState()Lnet/minecraft/block/BlockState;", ordinal = 0))
	private BlockState getRandomWarped(Block block, ServerWorld world, Random random, BlockPos pos, BlockState state) {
		if(state.isOf(NetherBlocks.BLUE_NETHERRACK))
			return NetherBlocks.BLUE_WARPED_NYLIUM.getDefaultState();
		return Blocks.WARPED_NYLIUM.getDefaultState();
	}

	//private BlockState get
}
