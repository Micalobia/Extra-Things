package dev.micalobia.extra_things.mixin.block;

import dev.micalobia.extra_things.block.enums.NetherColor;
import dev.micalobia.extra_things.util.MixinTemplate;
import dev.micalobia.extra_things.block.INetherBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.NetherrackBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Random;

@Mixin(NetherrackBlock.class)
public abstract class NetherrackBlockMixin extends Block implements MixinTemplate<NetherrackBlock>, INetherBlock {
	public NetherrackBlockMixin(Settings settings) {
		super(settings);
		setDefaultState(getDefaultState().with(COLOR, NetherColor.RED));
	}

	@SuppressWarnings("ConstantConditions")
	private static NetherrackBlockMixin self(NetherrackBlock block) {
		return (NetherrackBlockMixin) (Object) block;
	}

	@Redirect(method = "grow", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;getDefaultState()Lnet/minecraft/block/BlockState;"))
	private BlockState carryColor(Block block, ServerWorld world, Random random, BlockPos pos, BlockState state) {
		NetherColor color = state.get(COLOR);
		return block.getDefaultState().with(COLOR, color);
	}

	@Override
	public void appendProperties(Builder<Block, BlockState> builder) {
		_appendProperties(builder);
	}
}
