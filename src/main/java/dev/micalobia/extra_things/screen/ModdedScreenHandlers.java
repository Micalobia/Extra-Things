package dev.micalobia.extra_things.screen;

import dev.micalobia.extra_things.ExtraThings;
import dev.micalobia.extra_things.client.gui.screen.ingame.LumbermillScreen;
import dev.micalobia.extra_things.mixin.client.gui.screen.ingame.HandledScreensAccessor;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry.SimpleClientHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;

public class ModdedScreenHandlers {
	public static final ScreenHandlerType<LumbermillScreenHandler> LUMBERMILL;

	static {
		LUMBERMILL = register("lumbermill", LumbermillScreenHandler::new);
		if(ExtraThings.onClient()) {
			HandledScreensAccessor.register(LUMBERMILL, LumbermillScreen::new);
		}
	}

	private static <T extends ScreenHandler> ScreenHandlerType<T> register(String id, SimpleClientHandlerFactory<T> factory) {
		return ScreenHandlerRegistry.registerSimple(ExtraThings.id(id), factory);
	}

	public static void init() {
	}
}
