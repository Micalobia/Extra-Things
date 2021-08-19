package dev.micalobia.extra_things.mixin.world.gen.feature;

import dev.micalobia.extra_things.block.ModdedBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.feature.HugeFungusFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(HugeFungusFeature.class)
public class HugeFungusFeatureMixin {
	@ModifyVariable(method = "generate", at = @At(shift = Shift.BY, by = 2, value = "INVOKE", target = "Lnet/minecraft/block/BlockState;getBlock()Lnet/minecraft/block/Block;"))
	private Block changeWarpedNylium(Block block) {
		if(block == Blocks.WARPED_NYLIUM)
			return ModdedBlocks.BLUE_WARPED_NYLIUM;
		return block;
	}
}
