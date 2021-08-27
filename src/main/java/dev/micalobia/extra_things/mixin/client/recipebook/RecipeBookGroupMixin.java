package dev.micalobia.extra_things.mixin.client.recipebook;

import com.google.common.collect.ImmutableList;
import dev.micalobia.extra_things.client.recipebook.ModdedRecipeBookGroup;
import dev.micalobia.extra_things.recipe.book.ModdedRecipeBookCategory;
import net.minecraft.block.Blocks;
import net.minecraft.client.recipebook.RecipeBookGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeBookCategory;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Mixin(RecipeBookGroup.class)
public class RecipeBookGroupMixin {
    @Shadow
    @Final
    @Mutable
    public static Map<RecipeBookGroup, List<RecipeBookGroup>> SEARCH_MAP;

    @SuppressWarnings("ShadowTarget")
    @Shadow
    @Final
    @Mutable
    private static RecipeBookGroup[] field_1805;

    @SuppressWarnings("InvokerTarget")
    @Invoker("<init>")
    private static RecipeBookGroup create(String internalName, int ordinal, ItemStack... entries) {
        throw new AssertionError();
    }

    private static RecipeBookGroup add(List<RecipeBookGroup> list, String internalName, int ordinal, ItemStack... entries) {
        RecipeBookGroup ret = create(internalName, ordinal, entries);
        list.add(ret);
        return ret;
    }

    @SuppressWarnings("UnresolvedMixinReference")
    @Inject(method = "<clinit>", at = @At(
            value = "FIELD",
            opcode = Opcodes.PUTSTATIC,
            target = "Lnet/minecraft/client/recipebook/RecipeBookGroup;field_1805:[Lnet/minecraft/client/recipebook/RecipeBookGroup;",
            shift = At.Shift.AFTER
    ))
    private static void addEnum(CallbackInfo ci) {
        List<RecipeBookGroup> objs = new ArrayList<>(Arrays.asList(field_1805));
        RecipeBookGroup last = objs.get(objs.size() - 1);
        int ordinal = last.ordinal();
        ModdedRecipeBookGroup.KILN_SEARCH = add(objs, "KILN_SEARCH", ++ordinal, new ItemStack(Items.COMPASS));
        ModdedRecipeBookGroup.KILN_BLOCKS = add(objs, "KILN_BLOCKS", ++ordinal, new ItemStack(Blocks.SAND));
        ModdedRecipeBookGroup.KILN_MISC = add(objs, "KILN_MISC", ++ordinal, new ItemStack(Blocks.NETHERRACK));
        field_1805 = objs.toArray(new RecipeBookGroup[0]);
    }

    @SuppressWarnings("UnresolvedMixinReference")
    @Inject(method = "<clinit>", at = @At(
            value = "FIELD",
            opcode = Opcodes.PUTSTATIC,
            target = "Lnet/minecraft/client/recipebook/RecipeBookGroup;SEARCH_MAP:Ljava/util/Map;",
            shift = At.Shift.AFTER
    ))
    private static void addMap(CallbackInfo ci) {
        SEARCH_MAP.put(ModdedRecipeBookGroup.KILN_SEARCH, ImmutableList.of(ModdedRecipeBookGroup.KILN_BLOCKS, ModdedRecipeBookGroup.KILN_MISC));
    }

    @Inject(method = "getGroups", at = @At("HEAD"), cancellable = true)
    private static void getKilnGroup(RecipeBookCategory category, CallbackInfoReturnable<List<RecipeBookGroup>> cir) {
        if (category == ModdedRecipeBookCategory.KILN)
            cir.setReturnValue(ModdedRecipeBookGroup.KILN);
    }
}
