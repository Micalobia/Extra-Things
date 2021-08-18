package dev.micalobia.extra_things.block;

import dev.micalobia.extra_things.block.cauldron.CauldronBehaviors;
import dev.micalobia.extra_things.block.entity.PotionCauldronBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.rendering.data.v1.RenderAttachedBlockView;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome.Precipitation;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class PotionCauldronBlock extends LeveledCauldronBlock implements BlockEntityProvider {
	public PotionCauldronBlock(Settings settings) {
		super(settings, null, CauldronBehaviors.POTION_CAULDRON_BEHAVIOR);
	}

	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new PotionCauldronBlockEntity(pos, state);
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
	public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
		return super.getComparatorOutput(state, world, pos) + 3;
	}

	@Override
	protected void fillFromDripstone(BlockState state, World world, BlockPos pos, Fluid fluid) {
	}

	@Override
	public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
	}

	@Override
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
		if(random.nextInt(state.get(LEVEL) + 4) < 2) {
			int j = random.nextInt(2) * 2 - 1;
			int k = random.nextInt(2) * 2 - 1;
			float d = pos.getX() + 0.5f + j * .375f * random.nextFloat();
			float e = (float) (pos.getY() + getFluidHeight(state));
			float f = pos.getZ() + 0.5f + k * .375f * random.nextFloat();
			PotionCauldronBlockEntity entity = (PotionCauldronBlockEntity) world.getBlockEntity(pos);
			assert entity != null;
			int color = entity.getColor();
			double r = (color >> 16 & 255) / 255.0D;
			double g = (color >> 8 & 255) / 255.0D;
			double b = (color & 255) / 255.0D;
			world.addParticle(state.get(LEVEL) == 3 ? ParticleTypes.ENTITY_EFFECT : ParticleTypes.AMBIENT_ENTITY_EFFECT, d, e, f, r, g, b);
		}
	}

	@Override
	public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
		return super.getPickStack(world, pos, state);
	}

	//	@Override
//	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
//		ItemStack stack = player.getStackInHand(hand);
//		if(stack.isOf(Items.POTION)) {
//			PotionCauldronBlockEntity entity = (PotionCauldronBlockEntity) world.getBlockEntity(pos);
//			assert entity != null;
//			entity.setPotion(PotionUtil.getPotion(stack));
//			if(!world.isClient) {
//				entity.sync();
//			}
//			world.updateListeners(pos, state, state, 4);
//			entity.markDirty();
//			return ActionResult.SUCCESS;
//		}
//		return ActionResult.PASS;
//	}
}
