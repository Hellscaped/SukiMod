package dev.hellscaped.sukimod.client;

import dev.hellscaped.sukimod.ModBlockEntities;
import dev.hellscaped.sukimod.ModBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;

public class SukimodClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.putBlock(ModBlocks.JAR, ChunkSectionLayer.CUTOUT);
        BlockEntityRenderers.register(ModBlockEntities.JAR_BLOCK_ENTITY, JarBlockEntityRenderer::new);
    }
}
