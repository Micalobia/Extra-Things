package dev.micalobia.extra_things.mixin.block;

import dev.micalobia.extra_things.block.enums.NetherColor;
import dev.micalobia.extra_things.util.MixinTemplate;
import dev.micalobia.extra_things.block.INetherBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.NyliumBlock;
import net.minecraft.state.StateManager.Builder;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(NyliumBlock.class)
public abstract class NyliumBlockMixin extends Block implements MixinTemplate<NyliumBlock>, INetherBlock {
	public NyliumBlockMixin(Settings settings) {
		super(settings);
		setDefaultState(getDefaultState().with(COLOR, NetherColor.RED));
	}

	@Override
	public void appendProperties(Builder<Block, BlockState> builder) {
		_appendProperties(builder);
	}
}
