package dev.micalobia.extra_things.items;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.state.property.Property;
import net.minecraft.util.collection.DefaultedList;

public class BlockStateItem<T extends Comparable<T>> extends BlockItem {
	private final Property<T> block_property;
	private final T[] block_states;

	@SafeVarargs
	public BlockStateItem(Block block, Settings settings, Property<T> property, T... states) {
		super(block, settings);
		block_property = property;
		block_states = states;
	}

	@Override
	public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {
		if(this.isIn(group)) {
			int i = -1;
			for(T t : block_states) {
				++i;
				ItemStack stack = new ItemStack(this);
				NbtCompound nbt = stack.getOrCreateTag();
				nbt.putInt("CustomModelData", i);
				NbtCompound state_nbt = new NbtCompound();
				state_nbt.putString(block_property.getName(), t.toString());
				nbt.put("BlockStateTag", state_nbt);
				stacks.add(stack);
			}
		}
	}
}
