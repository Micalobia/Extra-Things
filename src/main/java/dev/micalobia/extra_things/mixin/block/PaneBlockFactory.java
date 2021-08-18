package dev.micalobia.extra_things.mixin.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.PaneBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(PaneBlock.class)
public interface PaneBlockFactory {
	@Invoker("<init>")
	static PaneBlock create(AbstractBlock.Settings settings) {
		throw new AssertionError();
	}
}
