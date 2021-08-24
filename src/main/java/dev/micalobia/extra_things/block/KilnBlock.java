package dev.micalobia.extra_things.block;

import dev.micalobia.extra_things.block.entity.KilnBlockEntity;
import dev.micalobia.extra_things.stat.ModdedStats;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class KilnBlock extends AbstractFurnaceBlock {
	protected KilnBlock(Settings settings) {
		super(settings);
	}

	@Override
	protected void openScreen(World world, BlockPos pos, PlayerEntity player) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if(blockEntity instanceof KilnBlockEntity) {
			player.openHandledScreen((NamedScreenHandlerFactory) blockEntity);
			player.incrementStat(ModdedStats.INTERACT_WITH_KILN);
		}
	}

	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new KilnBlockEntity(pos, state);
	}
}