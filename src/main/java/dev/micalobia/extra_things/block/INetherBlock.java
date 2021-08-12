package dev.micalobia.extra_things.block;

import dev.micalobia.extra_things.block.enums.NetherColor;
import dev.micalobia.extra_things.state.property.Properties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;

public interface INetherBlock {
	EnumProperty<NetherColor> COLOR = Properties.NETHER_COLOR;

	default void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(COLOR);
	}

	default void _appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(COLOR);
	}
}
