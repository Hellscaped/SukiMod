package dev.hellscaped.sukimod.client;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.world.item.ItemStack;

public class JarBlockEntityRenderState extends BlockEntityRenderState {
    private ItemStack held = ItemStack.EMPTY;

    public ItemStack getHeld() {
        return held;
    }

    public void setHeld(ItemStack itemStack) {
        this.held = held;
    }
}
