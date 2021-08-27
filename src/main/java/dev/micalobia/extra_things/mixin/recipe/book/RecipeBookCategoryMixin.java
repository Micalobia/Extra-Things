package dev.micalobia.extra_things.mixin.recipe.book;

import dev.micalobia.extra_things.recipe.book.ModdedRecipeBookCategory;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mixin(RecipeBookCategory.class)
public class RecipeBookCategoryMixin {
    @SuppressWarnings("ShadowTarget")
    @Shadow
    @Final
    @Mutable
    private static RecipeBookCategory[] field_25767;

    @SuppressWarnings("InvokerTarget")
    @Invoker("<init>")
    private static RecipeBookCategory create(String internalName, int ordinal) {
        throw new AssertionError();
    }

    private static RecipeBookCategory add(List<RecipeBookCategory> list, String internalName, int ordinal) {
        RecipeBookCategory ret = create(internalName, ordinal);
        list.add(ret);
        return ret;
    }

    @SuppressWarnings("UnresolvedMixinReference")
    @Inject(method = "<clinit>", at = @At(
            value = "FIELD",
            opcode = Opcodes.PUTSTATIC,
            target = "Lnet/minecraft/recipe/book/RecipeBookCategory;field_25767:[Lnet/minecraft/recipe/book/RecipeBookCategory;",
            shift = At.Shift.AFTER
    ))
    private static void addEnum(CallbackInfo ci) {
        List<RecipeBookCategory> objs = new ArrayList<>(Arrays.asList(field_25767));
        RecipeBookCategory last = objs.get(objs.size() - 1);
        int ordinal = last.ordinal();
        ModdedRecipeBookCategory.KILN = add(objs, "KILN", ++ordinal);
        field_25767 = objs.toArray(new RecipeBookCategory[0]);
    }
}
