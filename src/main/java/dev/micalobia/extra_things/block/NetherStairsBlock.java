package dev.micalobia.extra_things.block;

import dev.micalobia.extra_things.block.enums.NetherColor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.StairsBlock;
import net.minecraft.state.StateManager.Builder;

public class NetherStairsBlock extends StairsBlock implements INetherBlock {
	public NetherStairsBlock(BlockState baseBlockState, Settings settings) {
		super(baseBlockState, settings);
		setDefaultState(getDefaultState().with(COLOR, NetherColor.RED));
	}

	@Override
	public void appendProperties(Builder<Block, BlockState> builder) {
		super.appendProperties(builder);
		INetherBlock.super.appendProperties(builder);
	}
}
