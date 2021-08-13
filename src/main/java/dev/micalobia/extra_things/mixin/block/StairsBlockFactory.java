package dev.micalobia.extra_things.mixin.block;

import net.minecraft.block.AbstractBlock.Settings;
import net.minecraft.block.BlockState;
import net.minecraft.block.StairsBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(StairsBlock.class)
public interface StairsBlockFactory {
	@Invoker("<init>")
	static StairsBlock create(BlockState state, Settings settings) {
		throw new AssertionError();
	}
}
