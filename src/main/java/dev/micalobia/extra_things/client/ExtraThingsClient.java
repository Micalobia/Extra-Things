package dev.micalobia.extra_things.client;

import dev.micalobia.extra_things.block.ModdedBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

@Environment(EnvType.CLIENT)
public class ExtraThingsClient implements ClientModInitializer {
	public void onInitializeClient() {
		//BlockRenderLayerMap.INSTANCE.putBlock(ModdedBlocks.TINTED_GLASS_PANE, RenderLayer.getTranslucent());
	}
}
