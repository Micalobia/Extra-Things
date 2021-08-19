package dev.micalobia.extra_things.mixin.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.NetherWartBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(NetherWartBlock.class)
public interface NetherWartBlockFactory {
	@Invoker("<init>")
	static NetherWartBlock create(AbstractBlock.Settings settings) {
		throw new AssertionError();
	}
}
