package dev.micalobia.extra_things.util;

public interface Self<TMain> {
	@SuppressWarnings("unchecked")
	default TMain self() {
		return (TMain) this;
	}
}
