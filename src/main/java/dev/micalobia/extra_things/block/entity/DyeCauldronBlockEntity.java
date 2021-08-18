package dev.micalobia.extra_things.block.entity;

import dev.micalobia.extra_things.util.Dyeable;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.fabricmc.fabric.api.rendering.data.v1.RenderAttachmentBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class DyeCauldronBlockEntity extends BlockEntity implements Dyeable<DyeCauldronBlockEntity>, BlockEntityClientSerializable, RenderAttachmentBlockEntity {
	int color;

	public DyeCauldronBlockEntity(BlockPos pos, BlockState state) {
		super(ModdedBlockEntities.DYE_CAULDRON_BLOCK_ENTITY, pos, state);
		color = -1;
	}

	@Override
	public boolean hasColor() {
		return color >= 0;
	}

	@Override
	public int getColor() {
		if(hasColor())
			return color;
		return 0x385DC6;
	}

	@Override
	public void setColor(int color) {
		if(color < 0 || color > 0xffffff)
			throw new IllegalArgumentException(color + " is not a valid color; Make sure it's between 0 and 16777215");
		this.color = color;
	}

	@Override
	public void removeColor() {
		color = -1;
	}

	@Override
	public void setRGB(int red, int green, int blue) {
		Dyeable.super.setRGB(red, green, blue);
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
		color = nbt.getInt("color");
	}

	protected NbtCompound writeSpecialNbt(NbtCompound nbt) {
		nbt.putInt("color", color);
		return nbt;
	}

	@Override
	public @Nullable Object getRenderAttachmentData() {
		return getColor();
	}


}
