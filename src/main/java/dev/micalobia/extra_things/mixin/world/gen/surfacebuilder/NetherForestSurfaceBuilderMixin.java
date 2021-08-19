package dev.micalobia.extra_things.mixin.world.gen.surfacebuilder;

import net.minecraft.world.gen.surfacebuilder.NetherForestSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import static dev.micalobia.extra_things.block.ModdedBlocks.BLUE_NETHERRACK;
import static dev.micalobia.extra_things.block.ModdedBlocks.BLUE_WARPED_NYLIUM;
import static net.minecraft.block.Blocks.WARPED_NYLIUM;

@Mixin(NetherForestSurfaceBuilder.class)
public class NetherForestSurfaceBuilderMixin {
	@ModifyVariable(method = "generate", at = @At("HEAD"))
	private TernarySurfaceConfig changeWarpedForest(TernarySurfaceConfig config) {
		if(config.getTopMaterial().isOf(WARPED_NYLIUM))
			return new TernarySurfaceConfig(BLUE_WARPED_NYLIUM.getDefaultState(), BLUE_NETHERRACK.getDefaultState(), config.getUnderwaterMaterial());
		return config;
	}
}
