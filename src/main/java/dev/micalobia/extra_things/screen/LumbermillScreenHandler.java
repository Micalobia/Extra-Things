package dev.micalobia.extra_things.screen;

import dev.micalobia.extra_things.block.ModdedBlocks;
import dev.micalobia.extra_things.recipe.LumbermillRecipe;
import dev.micalobia.extra_things.recipe.ModdedRecipes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.Property;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class LumbermillScreenHandler extends ScreenHandler {
	public final Inventory input;
	final Slot inputSlot;
	final Slot outputSlot;
	private final CraftingResultInventory output;
	private final ScreenHandlerContext context;
	private final Property selectedRecipe;
	private final World world;
	private List<LumbermillRecipe> availableRecipes;
	private ItemStack inputStack;
	private long lastTakeTime;
	private Runnable contentsChangedListener;

	public LumbermillScreenHandler(int syncId, PlayerInventory playerInventory) {
		this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
	}

	public LumbermillScreenHandler(int syncId, PlayerInventory playerInventory, final ScreenHandlerContext context) {
		super(ModdedScreenHandlers.LUMBERMILL, syncId);
		this.selectedRecipe = Property.create();
		this.availableRecipes = new ArrayList<>();
		this.inputStack = ItemStack.EMPTY;
		this.contentsChangedListener = () -> {
		};
		this.input = new SimpleInventory(1) {
			public void markDirty() {
				super.markDirty();
				LumbermillScreenHandler.this.onContentChanged(this);
				LumbermillScreenHandler.this.contentsChangedListener.run();
			}
		};
		this.output = new CraftingResultInventory();
		this.context = context;
		this.world = playerInventory.player.world;
		this.inputSlot = this.addSlot(new Slot(this.input, 0, 20, 33));
		this.outputSlot = this.addSlot(new Slot(this.output, 1, 143, 33) {
			public void onTakeItem(PlayerEntity player, ItemStack stack) {
				stack.onCraft(player.world, player, stack.getCount());
				LumbermillScreenHandler.this.output.unlockLastRecipe(player);
				ItemStack itemStack = LumbermillScreenHandler.this.inputSlot.takeStack(1);
				if(!itemStack.isEmpty()) {
					LumbermillScreenHandler.this.populateResult();
				}

				context.run((world, blockPos) -> {
					long l = world.getTime();
					if(LumbermillScreenHandler.this.lastTakeTime != l) {
						world.playSound(null, blockPos, SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundCategory.BLOCKS, 1.0F, 1.0F);
						LumbermillScreenHandler.this.lastTakeTime = l;
					}

				});
				super.onTakeItem(player, stack);
			}

			public boolean canInsert(ItemStack stack) {
				return false;
			}
		});

		for(int i = 0; i < 3; ++i) {
			for(int j = 0; j < 9; ++j) {
				this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for(int i = 0; i < 9; ++i) {
			this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
		}

		this.addProperty(this.selectedRecipe);
	}

	public int getSelectedRecipe() {
		return this.selectedRecipe.get();
	}

	public List<LumbermillRecipe> getAvailableRecipes() {
		return this.availableRecipes;
	}

	public int getAvailableRecipeCount() {
		return this.availableRecipes.size();
	}

	public boolean canCraft() {
		return this.inputSlot.hasStack() && !this.availableRecipes.isEmpty();
	}

	private void updateInput(Inventory input, ItemStack stack) {
		this.availableRecipes.clear();
		this.selectedRecipe.set(-1);
		this.outputSlot.setStack(ItemStack.EMPTY);
		if(!stack.isEmpty()) {
			this.availableRecipes = this.world.getRecipeManager().getAllMatches(ModdedRecipes.LUMBERMILL, input, this.world);
		}
	}

	private boolean method_30160(int i) {
		return i >= 0 && i < this.availableRecipes.size();
	}

	void populateResult() {
		if(!this.availableRecipes.isEmpty() && this.method_30160(this.selectedRecipe.get())) {
			LumbermillRecipe recipe = this.availableRecipes.get(this.selectedRecipe.get());
			this.output.setLastRecipe(recipe);
			this.outputSlot.setStack(recipe.craft(this.input));
		} else {
			this.outputSlot.setStack(ItemStack.EMPTY);
		}

		this.sendContentUpdates();
	}

	public ScreenHandlerType<?> getType() {
		return ModdedScreenHandlers.LUMBERMILL;
	}

	public boolean onButtonClick(PlayerEntity player, int id) {
		if(this.method_30160(id)) {
			this.selectedRecipe.set(id);
			this.populateResult();
		}
		return true;
	}

	public ItemStack transferSlot(PlayerEntity player, int index) {
		ItemStack itemStack = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		if(slot.hasStack()) {
			ItemStack itemStack2 = slot.getStack();
			Item item = itemStack2.getItem();
			itemStack = itemStack2.copy();
			if(index == 1) {
				item.onCraft(itemStack2, player.world, player);
				if(!this.insertItem(itemStack2, 2, 38, true)) {
					return ItemStack.EMPTY;
				}

				slot.onQuickTransfer(itemStack2, itemStack);
			} else if(index == 0) {
				if(!this.insertItem(itemStack2, 2, 38, false)) {
					return ItemStack.EMPTY;
				}
			} else if(this.world.getRecipeManager().getFirstMatch(ModdedRecipes.LUMBERMILL, new SimpleInventory(itemStack2), this.world).isPresent()) {
				if(!this.insertItem(itemStack2, 0, 1, false)) {
					return ItemStack.EMPTY;
				}
			} else if(index >= 2 && index < 29) {
				if(!this.insertItem(itemStack2, 29, 38, false)) {
					return ItemStack.EMPTY;
				}
			} else if(index >= 29 && index < 38 && !this.insertItem(itemStack2, 2, 29, false)) {
				return ItemStack.EMPTY;
			}

			if(itemStack2.isEmpty()) {
				slot.setStack(ItemStack.EMPTY);
			}

			slot.markDirty();
			if(itemStack2.getCount() == itemStack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTakeItem(player, itemStack2);
			this.sendContentUpdates();
		}

		return itemStack;
	}

	public boolean canInsertIntoSlot(ItemStack stack, Slot slot) {
		return slot.inventory != this.output && super.canInsertIntoSlot(stack, slot);
	}

	public void close(PlayerEntity playerEntity) {
		super.close(playerEntity);
		this.output.removeStack(1);
		this.context.run((world, blockPos) -> {
			this.dropInventory(playerEntity, this.input);
		});
	}

	public void onContentChanged(Inventory inventory) {
		ItemStack itemStack = this.inputSlot.getStack();
		if(!itemStack.isOf(this.inputStack.getItem())) {
			this.inputStack = itemStack.copy();
			this.updateInput(inventory, itemStack);
		}
	}

	public boolean canUse(PlayerEntity player) {
		return canUse(this.context, player, ModdedBlocks.LUMBERMILL);
	}

	public void setContentsChangedListener(Runnable runnable) {
		this.contentsChangedListener = runnable;
	}
}
