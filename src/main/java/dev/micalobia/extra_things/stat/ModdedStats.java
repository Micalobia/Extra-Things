package dev.micalobia.extra_things.stat;

import dev.micalobia.extra_things.ExtraThings;
import net.minecraft.stat.StatFormatter;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModdedStats {
	public static final Identifier INTERACT_WITH_LUMBERMILL;

	static {
		INTERACT_WITH_LUMBERMILL = register("interact_with_lumbermill", StatFormatter.DEFAULT);
	}

	private static Identifier register(String path, StatFormatter statFormatter) {
		Identifier id = ExtraThings.id(path);
		Registry.register(Registry.CUSTOM_STAT, id, id);
		Stats.CUSTOM.getOrCreateStat(id, statFormatter);
		return id;
	}
}
