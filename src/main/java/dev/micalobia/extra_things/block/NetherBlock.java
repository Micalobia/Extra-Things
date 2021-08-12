package dev.micalobia.extra_things.block;

import dev.micalobia.extra_things.block.enums.NetherColor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.state.StateManager.Builder;

public class NetherBlock extends Block implements INetherBlock {
	public NetherBlock(Settings settings) {
		super(settings);
		setDefaultState(getDefaultState().with(COLOR, NetherColor.RED));
	}

	@Override
	public void appendProperties(Builder<Block, BlockState> builder) {
		INetherBlock.super.appendProperties(builder);
	}
}
