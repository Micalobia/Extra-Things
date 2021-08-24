package dev.micalobia.extra_things.client;

import dev.micalobia.extra_things.block.ModdedBlocks;
import dev.micalobia.extra_things.screen.ModdedScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class ExtraThingsClient implements ClientModInitializer {
	public void onInitializeClient() {
		ModdedBlocks.clientInit();
		ModdedScreenHandlers.clientInit();
	}
}
