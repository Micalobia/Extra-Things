package dev.micalobia.extra_things.mixin.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.NyliumBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(NyliumBlock.class)
public interface NyliumBlockFactory {
	@Invoker("<init>")
	static NyliumBlock create(AbstractBlock.Settings settings) {
		throw new AssertionError();
	}
}
