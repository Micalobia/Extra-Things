package dev.micalobia.extra_things.mixin.block;

import dev.micalobia.extra_things.ExtraThings;
import dev.micalobia.extra_things.block.NetherBlock;
import dev.micalobia.extra_things.block.NetherSlabBlock;
import dev.micalobia.extra_things.block.NetherStairsBlock;
import net.minecraft.block.AbstractBlock.Settings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Blocks.class)
public class BlocksMixin {
//	@SuppressWarnings("UnresolvedMixinReference")
//	@Redirect(method = "<clinit>",
//			at = @At(value = "NEW", target = "Lnet/minecraft/block/Block;(Lnet/minecraft/block/AbstractBlock$Settings;)V", ordinal = 0)
//			/*slice = @Slice(from = @At(shift= Shift.BY, by=-2, value = "CONSTANT", args = "stringValue=nether_bricks"))*/)
//	private static Block changeNetherBricks(AbstractBlock.Settings settings) {
//		return new NetherBlock(AbstractBlock.Settings
//				.of(Material.STONE, MapColor.DARK_RED)
//				.requiresTool()
//				.strength(2.0F, 6.0F)
//				.sounds(BlockSoundGroup.NETHER_BRICKS));
//	}

	private static Block register_pure(String id, Block block) {
		return Registry.register(Registry.BLOCK, id, block);
	}

	@Inject(method = "register", at = @At("HEAD"), cancellable = true)
	private static void changeNetherBlocks(String id, Block block, CallbackInfoReturnable<Block> cir) {
		switch(id) {
			case "nether_bricks", "red_nether_bricks", "cracked_nether_bricks", "chiseled_nether_bricks" -> {
				cir.setReturnValue(register_pure(id, new NetherBlock(Settings.copy(block))));
			}
			case "nether_brick_stairs", "red_nether_brick_stairs" -> {
				StairsBlockAccessor stairs = (StairsBlockAccessor) block;
				Block base = stairs.getBaseBlock();
				ExtraThings.LOGGER.info(base);
				cir.setReturnValue(register_pure(id, new NetherStairsBlock(base.getDefaultState(), Settings.copy(base))));
			}
			case "nether_brick_slab", "red_nether_brick_slab" -> {
				cir.setReturnValue(register_pure(id, new NetherSlabBlock(Settings.copy(block))));
			}
		}
	}
}
