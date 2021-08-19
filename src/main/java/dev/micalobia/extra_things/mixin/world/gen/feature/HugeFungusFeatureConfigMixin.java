package dev.micalobia.extra_things.mixin.world.gen.feature;

import dev.micalobia.extra_things.block.ModdedBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.HugeFungusFeatureConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(HugeFungusFeatureConfig.class)
public class HugeFungusFeatureConfigMixin {
	// This causes my blocks to turn into ghost blocks, keeping because it's better if it ever stops being dumb
//	@SuppressWarnings("UnresolvedMixinReference")
//	@ModifyArg(
//			method = "<clinit>",
//			at = @At(value = "INVOKE", target = "Lnet/minecraft/world/gen/feature/HugeFungusFeatureConfig;<init>(Lnet/minecraft/block/BlockState;Lnet/minecraft/block/BlockState;Lnet/minecraft/block/BlockState;Lnet/minecraft/block/BlockState;Z)V", ordinal = 2),
//			index = 0)
//	private static BlockState changeBaseOfWarpedTree(BlockState state) {
//		return ModdedBlocks.BLUE_WARPED_NYLIUM.getDefaultState();
//	}
}
