package dev.micalobia.extra_things.util;

public interface MixinTemplate<TMain> {
	@SuppressWarnings("unchecked")
	default TMain self() {
		return (TMain) this;
	}
}
