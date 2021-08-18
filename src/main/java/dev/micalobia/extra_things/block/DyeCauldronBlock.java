package dev.micalobia.extra_things.block;

import dev.micalobia.extra_things.block.cauldron.CauldronBehaviors;
import dev.micalobia.extra_things.block.entity.DyeCauldronBlockEntity;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.Fluid;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome.Precipitation;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class DyeCauldronBlock extends LeveledCauldronBlock implements BlockEntityProvider {
	public DyeCauldronBlock(Settings settings) {
		super(settings, null, CauldronBehaviors.DYE_CAULDRON_BEHAVIOR);
	}

	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new DyeCauldronBlockEntity(pos, state);
	}

	@Override
	protected boolean canBeFilledByDripstone(Fluid fluid) {
		return false;
	}

	@Override
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
	}

	@Override
	protected void onFireCollision(BlockState state, World world, BlockPos pos) {
	}

	@Override
	public void precipitationTick(BlockState state, World world, BlockPos pos, Precipitation precipitation) {
	}

	@Override
	protected void fillFromDripstone(BlockState state, World world, BlockPos pos, Fluid fluid) {
	}

	@Override
	public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
	}

}
