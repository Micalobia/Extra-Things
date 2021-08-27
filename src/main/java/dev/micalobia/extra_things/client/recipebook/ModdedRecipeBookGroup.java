package dev.micalobia.extra_things.client.recipebook;

import com.google.common.collect.ImmutableList;
import dev.micalobia.extra_things.ExtraThings;
import net.minecraft.client.recipebook.RecipeBookGroup;

import java.util.List;

public class ModdedRecipeBookGroup {
    public static List<RecipeBookGroup> KILN;
    public static RecipeBookGroup KILN_SEARCH;
    public static RecipeBookGroup KILN_BLOCKS;
    public static RecipeBookGroup KILN_MISC;

    static {
        //noinspection ResultOfMethodCallIgnored
        RecipeBookGroup.values();
    }
}
