package dev.micalobia.extra_things.state.property;

import dev.micalobia.extra_things.block.enums.NetherColor;
import net.minecraft.state.property.EnumProperty;

public class Properties {
	public static final EnumProperty<NetherColor> NETHER_COLOR;

	static {
		NETHER_COLOR = EnumProperty.of("color", NetherColor.class);
	}
}
