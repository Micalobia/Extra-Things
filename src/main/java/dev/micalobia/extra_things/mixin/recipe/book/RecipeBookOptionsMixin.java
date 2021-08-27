package dev.micalobia.extra_things.mixin.recipe.book;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import dev.micalobia.extra_things.recipe.book.ModdedRecipeBookCategory;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.recipe.book.RecipeBookOptions;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(RecipeBookOptions.class)
public class RecipeBookOptionsMixin {
    @Shadow
    @Final
    @Mutable
    private static Map<RecipeBookCategory, Pair<String, String>> CATEGORY_OPTION_NAMES;

    @SuppressWarnings("UnresolvedMixinReference")
    @Inject(method = "<clinit>", at = @At(
            value = "FIELD",
            opcode = Opcodes.PUTSTATIC,
            target = "Lnet/minecraft/recipe/book/RecipeBookOptions;CATEGORY_OPTION_NAMES:Ljava/util/Map;",
            shift = At.Shift.AFTER
    ))
    private static void addCategoryNames(CallbackInfo ci) {
        //noinspection ResultOfMethodCallIgnored
        RecipeBookCategory.values();
        Map<RecipeBookCategory, Pair<String, String>> map = Maps.newHashMap(CATEGORY_OPTION_NAMES);
        map.put(ModdedRecipeBookCategory.KILN, Pair.of("isKilnGuiOpen", "isKilnFilteringCraftable"));
        CATEGORY_OPTION_NAMES = ImmutableMap.copyOf(map);
    }
}
