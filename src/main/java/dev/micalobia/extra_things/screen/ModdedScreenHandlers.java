package dev.micalobia.extra_things.screen;

import dev.micalobia.extra_things.ExtraThings;
import dev.micalobia.extra_things.client.gui.screen.ingame.KilnScreen;
import dev.micalobia.extra_things.client.gui.screen.ingame.LumbermillScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry.SimpleClientHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;

public class ModdedScreenHandlers {
	public static final ScreenHandlerType<LumbermillScreenHandler> LUMBERMILL;
	public static final ScreenHandlerType<KilnScreenHandler> KILN;

	static {
		LUMBERMILL = register("lumbermill", LumbermillScreenHandler::new);
		KILN = register("kiln", KilnScreenHandler::new);
	}

	private static <T extends ScreenHandler> ScreenHandlerType<T> register(String id, SimpleClientHandlerFactory<T> factory) {
		return ScreenHandlerRegistry.registerSimple(ExtraThings.id(id), factory);
	}

	public static void init() {
	}

	@Environment(EnvType.CLIENT)
	public static void clientInit() {
		ScreenRegistry.register(LUMBERMILL, LumbermillScreen::new);
		ScreenRegistry.register(KILN, KilnScreen::new);
	}
}
