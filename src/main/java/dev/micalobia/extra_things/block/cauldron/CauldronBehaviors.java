package dev.micalobia.extra_things.block.cauldron;

import dev.micalobia.extra_things.block.ModdedBlocks;
import dev.micalobia.extra_things.block.PotionCauldronBlock;
import dev.micalobia.extra_things.block.entity.DyeCauldronBlockEntity;
import dev.micalobia.extra_things.block.entity.PotionCauldronBlockEntity;
import net.fabricmc.fabric.api.rendering.data.v1.RenderAttachedBlockView;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.*;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.event.GameEvent;

import java.util.List;
import java.util.Map;

import static net.minecraft.block.cauldron.CauldronBehavior.*;

public interface CauldronBehaviors {
	Map<Item, CauldronBehavior> POTION_CAULDRON_BEHAVIOR = createMap();
	Map<Item, CauldronBehavior> DYE_CAULDRON_BEHAVIOR = createMap();

	CauldronBehavior DYE_WATER = (state, world, pos, player, hand, stack) -> {
		Item item = stack.getItem();
		if(!(item instanceof DyeItem dyeItem))
			return ActionResult.PASS;
		DyeColor dyeColor = dyeItem.getColor();
		if(!world.isClient) {
			if(state.isOf(Blocks.WATER_CAULDRON))
				world.setBlockState(pos, ModdedBlocks.DYE_CAULDRON.getDefaultState().with(LeveledCauldronBlock.LEVEL, state.get(LeveledCauldronBlock.LEVEL)));
			DyeCauldronBlockEntity entity = (DyeCauldronBlockEntity) world.getBlockEntity(pos);
			assert entity != null;
			entity.blendAndSetColor(dyeColor);
			entity.sync();
			world.playSound(null, pos, SoundEvents.ITEM_DYE_USE, SoundCategory.BLOCKS, 1f, 1f);
		}
		return ActionResult.success(world.isClient);
	};

	CauldronBehavior DYE_DYEABLE_ITEM = (state, world, pos, player, hand, stack) -> {
		Item item = stack.getItem();
		if(!(item instanceof DyeableItem dyeableItem))
			return ActionResult.PASS;
		if(!world.isClient) {
			DyeCauldronBlockEntity entity = (DyeCauldronBlockEntity) world.getBlockEntity(pos);
			assert entity != null;
			dyeableItem.setColor(stack, entity.getColor());
			LeveledCauldronBlock.decrementFluidLevel(state, world, pos);
		}
		return ActionResult.success(world.isClient);
	};

	@SuppressWarnings("unused")
	static int colorProvider(BlockState state, BlockView view, BlockPos pos, int tintIndex) {
		RenderAttachedBlockView renderView = (RenderAttachedBlockView) view;
		Object ret = renderView.getBlockEntityRenderAttachment(pos);
		if(ret == null)
			return 0xFFFFFF;
		return (int) ret;
	}

	static void registerBehavior() {
		POTION_CAULDRON_BEHAVIOR.put(Items.POTION, (state, world, pos, player, hand, stack) -> {
			if(state.get(PotionCauldronBlock.LEVEL) == 3)
				return ActionResult.PASS;
			Potion potion = PotionUtil.getPotion(stack);
			if(potion == Potions.WATER)
				return ActionResult.PASS;

			PotionCauldronBlockEntity entity = (PotionCauldronBlockEntity) world.getBlockEntity(pos);
			assert entity != null;
			List<StatusEffectInstance> effects = PotionUtil.getCustomPotionEffects(stack);
			if(entity.getPotion() != potion)
				return ActionResult.PASS;
			boolean sameCustom = entity.getCustomPotionEffects().containsAll(effects) && effects.containsAll(entity.getCustomPotionEffects());
			if(!sameCustom)
				return ActionResult.PASS;
			if(!world.isClient) {
				if(!player.getAbilities().creativeMode)
					player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(Items.GLASS_BOTTLE)));
				player.incrementStat(Stats.USE_CAULDRON);
				player.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));
				world.setBlockState(pos, state.cycle(PotionCauldronBlock.LEVEL));
				world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0f, 1.0f);
				world.emitGameEvent(null, GameEvent.FLUID_PLACE, pos);
				entity.sync();
			}
			return ActionResult.success(world.isClient);
		});
		POTION_CAULDRON_BEHAVIOR.put(Items.GLASS_BOTTLE, (state, world, pos, player, hand, stack) -> {
			if(!world.isClient) {
				Item item = stack.getItem();
				ItemStack newStack = new ItemStack(Items.POTION);
				PotionCauldronBlockEntity entity = (PotionCauldronBlockEntity) world.getBlockEntity(pos);
				assert entity != null;
				PotionUtil.setPotion(newStack, entity.getPotion());
				PotionUtil.setCustomPotionEffects(newStack, entity.getCustomPotionEffects());
				player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, newStack));
				player.incrementStat(Stats.USE_CAULDRON);
				player.incrementStat(Stats.USED.getOrCreateStat(item));
				LeveledCauldronBlock.decrementFluidLevel(state, world, pos);
				world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
				world.emitGameEvent(null, GameEvent.FLUID_PICKUP, pos);
			}

			return ActionResult.success(world.isClient);
		});
		POTION_CAULDRON_BEHAVIOR.put(Items.ARROW, (state, world, pos, player, hand, stack) -> {
			if(!world.isClient) {
				ItemStack newStack = new ItemStack(Items.TIPPED_ARROW);
				PotionCauldronBlockEntity entity = (PotionCauldronBlockEntity) world.getBlockEntity(pos);
				assert entity != null;
				PotionUtil.setPotion(newStack, entity.getPotion());
				PotionUtil.setCustomPotionEffects(newStack, entity.getCustomPotionEffects());
				int count = Math.min(8, stack.getCount());
				if(!player.getAbilities().creativeMode)
					stack.decrement(8);
				else
					count = 8;
				newStack.setCount(count);
				if(stack.isEmpty())
					player.setStackInHand(hand, newStack);
				else if(player.getInventory().insertStack(newStack))
					player.playerScreenHandler.syncState();
				else
					player.dropItem(newStack, false);
				LeveledCauldronBlock.decrementFluidLevel(state, world, pos);
			}
			return ActionResult.success(world.isClient);
		});
		POTION_CAULDRON_BEHAVIOR.put(Items.WATER_BUCKET, FILL_WITH_WATER);
		POTION_CAULDRON_BEHAVIOR.put(Items.LAVA_BUCKET, FILL_WITH_LAVA);
		POTION_CAULDRON_BEHAVIOR.put(Items.POWDER_SNOW_BUCKET, FILL_WITH_POWDER_SNOW);
		EMPTY_CAULDRON_BEHAVIOR.put(Items.POTION, (state, world, pos, player, hand, stack) -> {
			Potion potion = PotionUtil.getPotion(stack);
			if(!world.isClient) {
				Item item = stack.getItem();
				player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(Items.GLASS_BOTTLE)));
				player.incrementStat(Stats.USE_CAULDRON);
				player.incrementStat(Stats.USED.getOrCreateStat(item));
				if(potion == Potions.WATER)
					world.setBlockState(pos, Blocks.WATER_CAULDRON.getDefaultState());
				else {
					BlockState newState = ModdedBlocks.POTION_CAULDRON.getDefaultState();
					world.setBlockState(pos, newState);
					PotionCauldronBlockEntity entity = (PotionCauldronBlockEntity) world.getBlockEntity(pos);
					assert entity != null;
					entity.setPotion(PotionUtil.getPotion(stack));
					entity.setCustomPotionEffects(PotionUtil.getCustomPotionEffects(stack));
					entity.sync();
				}
				world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
				world.emitGameEvent(null, GameEvent.FLUID_PLACE, pos);
			}
			return ActionResult.success(world.isClient);
		});
		DYE_CAULDRON_BEHAVIOR.put(Items.WHITE_DYE, DYE_WATER);
		DYE_CAULDRON_BEHAVIOR.put(Items.GRAY_DYE, DYE_WATER);
		DYE_CAULDRON_BEHAVIOR.put(Items.BLACK_DYE, DYE_WATER);
		DYE_CAULDRON_BEHAVIOR.put(Items.BLUE_DYE, DYE_WATER);
		DYE_CAULDRON_BEHAVIOR.put(Items.BROWN_DYE, DYE_WATER);
		DYE_CAULDRON_BEHAVIOR.put(Items.CYAN_DYE, DYE_WATER);
		DYE_CAULDRON_BEHAVIOR.put(Items.GREEN_DYE, DYE_WATER);
		DYE_CAULDRON_BEHAVIOR.put(Items.LIGHT_BLUE_DYE, DYE_WATER);
		DYE_CAULDRON_BEHAVIOR.put(Items.LIGHT_GRAY_DYE, DYE_WATER);
		DYE_CAULDRON_BEHAVIOR.put(Items.LIME_DYE, DYE_WATER);
		DYE_CAULDRON_BEHAVIOR.put(Items.MAGENTA_DYE, DYE_WATER);
		DYE_CAULDRON_BEHAVIOR.put(Items.ORANGE_DYE, DYE_WATER);
		DYE_CAULDRON_BEHAVIOR.put(Items.PINK_DYE, DYE_WATER);
		DYE_CAULDRON_BEHAVIOR.put(Items.PURPLE_DYE, DYE_WATER);
		DYE_CAULDRON_BEHAVIOR.put(Items.RED_DYE, DYE_WATER);
		DYE_CAULDRON_BEHAVIOR.put(Items.YELLOW_DYE, DYE_WATER);
		DYE_CAULDRON_BEHAVIOR.put(Items.LEATHER_BOOTS, DYE_DYEABLE_ITEM);
		DYE_CAULDRON_BEHAVIOR.put(Items.LEATHER_LEGGINGS, DYE_DYEABLE_ITEM);
		DYE_CAULDRON_BEHAVIOR.put(Items.LEATHER_CHESTPLATE, DYE_DYEABLE_ITEM);
		DYE_CAULDRON_BEHAVIOR.put(Items.LEATHER_HELMET, DYE_DYEABLE_ITEM);
		DYE_CAULDRON_BEHAVIOR.put(Items.LEATHER_HORSE_ARMOR, DYE_DYEABLE_ITEM);
		DYE_CAULDRON_BEHAVIOR.put(Items.WATER_BUCKET, FILL_WITH_WATER);
		DYE_CAULDRON_BEHAVIOR.put(Items.LAVA_BUCKET, FILL_WITH_LAVA);
		DYE_CAULDRON_BEHAVIOR.put(Items.POWDER_SNOW_BUCKET, FILL_WITH_POWDER_SNOW);

		CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(Items.WHITE_DYE, DYE_WATER);
		CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(Items.GRAY_DYE, DYE_WATER);
		CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(Items.BLACK_DYE, DYE_WATER);
		CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(Items.BLUE_DYE, DYE_WATER);
		CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(Items.BROWN_DYE, DYE_WATER);
		CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(Items.CYAN_DYE, DYE_WATER);
		CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(Items.GREEN_DYE, DYE_WATER);
		CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(Items.LIGHT_BLUE_DYE, DYE_WATER);
		CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(Items.LIGHT_GRAY_DYE, DYE_WATER);
		CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(Items.LIME_DYE, DYE_WATER);
		CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(Items.MAGENTA_DYE, DYE_WATER);
		CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(Items.ORANGE_DYE, DYE_WATER);
		CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(Items.PINK_DYE, DYE_WATER);
		CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(Items.PURPLE_DYE, DYE_WATER);
		CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(Items.RED_DYE, DYE_WATER);
		CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(Items.YELLOW_DYE, DYE_WATER);
	}
}
