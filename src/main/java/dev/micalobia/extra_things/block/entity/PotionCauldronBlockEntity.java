package dev.micalobia.extra_things.block.entity;

import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.fabricmc.fabric.api.rendering.data.v1.RenderAttachmentBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PotionCauldronBlockEntity extends BlockEntity implements BlockEntityClientSerializable, RenderAttachmentBlockEntity {
	protected Potion potion;
	protected List<StatusEffectInstance> customPotionEffects;

	public PotionCauldronBlockEntity(BlockPos pos, BlockState state) {
		super(ModdedBlockEntities.POTION_CAULDRON, pos, state);
		potion = Potions.EMPTY;
		customPotionEffects = new ArrayList<>();
	}

	public Potion getPotion() {
		return potion == null ? Potions.EMPTY : potion;
	}

	public void setPotion(Potion potion) {
		this.potion = potion == null ? Potions.EMPTY : potion;
	}

	public List<StatusEffectInstance> getCustomPotionEffects() {
		return customPotionEffects;
	}

	public void setCustomPotionEffects(Collection<StatusEffectInstance> custom) {
		customPotionEffects = List.copyOf(custom);
	}

	@Override
	public void readNbt(NbtCompound nbt) {
		super.readNbt(nbt);
		readSpecialNbt(nbt);
	}

	@Override
	public NbtCompound writeNbt(NbtCompound nbt) {
		super.writeNbt(nbt);
		return writeSpecialNbt(nbt);
	}

	@Override
	public void fromClientTag(NbtCompound nbt) {
		readSpecialNbt(nbt);
		MinecraftClient client = MinecraftClient.getInstance();
		// world, oldState, and newState are completely unused by the function, so technically even the first parameter is redundant
		client.worldRenderer.updateBlock(client.world, this.getPos(), null, null, 8);
	}

	@Override
	public NbtCompound toClientTag(NbtCompound nbt) {
		return writeSpecialNbt(nbt);
	}

	protected void readSpecialNbt(NbtCompound nbt) {
		potion = PotionUtil.getPotion(nbt);
		customPotionEffects = PotionUtil.getCustomPotionEffects(nbt);
	}

	protected NbtCompound writeSpecialNbt(NbtCompound nbt) {
		nbt.putString("Potion", Registry.POTION.getId(potion).toString());
		NbtList list = new NbtList();
		for(StatusEffectInstance effect : customPotionEffects) {
			list.add(effect.writeNbt(new NbtCompound()));
		}
		nbt.put("CustomPotionEffects", list);
		return nbt;
	}

	public int getColor() {
		return PotionUtil.getColor(PotionUtil.getPotionEffects(potion, customPotionEffects));
	}

	@Override
	public @Nullable Object getRenderAttachmentData() {
		return getColor();
	}


}
