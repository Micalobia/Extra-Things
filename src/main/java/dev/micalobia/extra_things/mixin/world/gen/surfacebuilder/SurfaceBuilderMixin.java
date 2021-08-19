package dev.micalobia.extra_things.mixin.world.gen.surfacebuilder;

import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SurfaceBuilder.class)
public class SurfaceBuilderMixin {
	// This causes my blocks to turn into ghost blocks, keeping because it's better if it ever stops being dumb
	//	@SuppressWarnings("UnresolvedMixinReference")
	//	@ModifyArg(
	//			method = "<clinit>",
	//			slice = @Slice(from = @At(shift = Shift.BEFORE, value = "FIELD", target = "Lnet/minecraft/world/gen/surfacebuilder/SurfaceBuilder;WARPED_NYLIUM_CONFIG:Lnet/minecraft/world/gen/surfacebuilder/TernarySurfaceConfig;")),
	//			at = @At(value = "INVOKE", target = "Lnet/minecraft/world/gen/surfacebuilder/TernarySurfaceConfig;<init>(Lnet/minecraft/block/BlockState;Lnet/minecraft/block/BlockState;Lnet/minecraft/block/BlockState;)V", ordinal = 0),
	//			index = 0
	//	)
	//	private static BlockState changeWarpedNylium(BlockState state) {
	//		ExtraThings.LOGGER.info(state);
	//		return ModdedBlocks.BLUE_WARPED_NYLIUM.getDefaultState();
	//	}
	//
	//	@SuppressWarnings("UnresolvedMixinReference")
	//	@ModifyArg(
	//			method = "<clinit>",
	//			slice = @Slice(from = @At(shift = Shift.BEFORE, value = "FIELD", target = "Lnet/minecraft/world/gen/surfacebuilder/SurfaceBuilder;WARPED_NYLIUM_CONFIG:Lnet/minecraft/world/gen/surfacebuilder/TernarySurfaceConfig;")),
	//			at = @At(value = "INVOKE", target = "Lnet/minecraft/world/gen/surfacebuilder/TernarySurfaceConfig;<init>(Lnet/minecraft/block/BlockState;Lnet/minecraft/block/BlockState;Lnet/minecraft/block/BlockState;)V", ordinal = 0),
	//			index = 1
	//	)
	//	private static BlockState changeWarpedNetherrack(BlockState state) {
	//		ExtraThings.LOGGER.info(state);
	//		return Blocks.ACACIA_PLANKS.getDefaultState();
	//	}
}
