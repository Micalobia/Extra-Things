package dev.micalobia.extra_things.client.recipebook;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.recipebook.RecipeBookGroup;

import java.util.List;

public class ModdedRecipeBookGroup {
    public static RecipeBookGroup KILN_SEARCH;
    public static RecipeBookGroup KILN_BLOCKS;
    public static RecipeBookGroup KILN_MISC;

    @SuppressWarnings("ConstantConditions")
    public static final List<RecipeBookGroup> KILN = ImmutableList.of(KILN_SEARCH, KILN_BLOCKS, KILN_MISC);

    static {
        //noinspection ResultOfMethodCallIgnored
        RecipeBookGroup.values();
    }
}
