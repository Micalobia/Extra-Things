package dev.micalobia.extra_things.block;

import dev.micalobia.extra_things.screen.LumbermillScreenHandler;
import dev.micalobia.extra_things.stat.ModdedStats;
import net.minecraft.block.BlockState;
import net.minecraft.block.StonecutterBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class LumbermillBlock extends StonecutterBlock {
	private static final TranslatableText TITLE = new TranslatableText("extra_things.container.lumbermill");

	public LumbermillBlock(Settings settings) {
		super(settings);
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if(world.isClient)
			return ActionResult.SUCCESS;
		else {
			player.openHandledScreen(state.createScreenHandlerFactory(world, pos));
			player.incrementStat(ModdedStats.INTERACT_WITH_LUMBERMILL);
			return ActionResult.CONSUME;
		}
	}

	@Nullable
	@Override
	public NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
		return new SimpleNamedScreenHandlerFactory(((syncId, inv, player) -> new LumbermillScreenHandler(syncId, inv, ScreenHandlerContext.create(world, pos))), TITLE);
	}
}
