package dev.hellscaped.sukimod;

import net.fabricmc.api.ModInitializer;

public class Sukimod implements ModInitializer {

    public static final String MOD_ID = "sukimod";

    @Override
    public void onInitialize() {
        ModItems.initialize();
        ModBlocks.initialize();
    }
}
