package dev.micalobia.extra_things.block.enums;

import net.minecraft.util.StringIdentifiable;

public enum NetherColor implements StringIdentifiable {
	RED("red"),
	BLUE("blue");

	private final String name;

	NetherColor(String name) {
		this.name = name;
	}

	public String toString() {
		return this.name;
	}

	public String asString() {
		return this.name;
	}
}
