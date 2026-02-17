package dev.hellscaped.sukimod.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.hellscaped.sukimod.JarBlockEntity;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;


public class JarBlockEntityRenderer implements BlockEntityRenderer<JarBlockEntity, JarBlockEntityRenderState> {
    private final ItemModelResolver itemModelResolver;
    private ItemStackRenderState itemStackRenderState;
    public JarBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.itemModelResolver = context.itemModelResolver();
    }

    @Override
    public JarBlockEntityRenderState createRenderState() {
        return new JarBlockEntityRenderState();
    }

    @Override
    public void extractRenderState(JarBlockEntity blockEntity, JarBlockEntityRenderState state, float tickProgress, Vec3 cameraPos, @Nullable ModelFeatureRenderer.CrumblingOverlay crumblingOverlay) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, tickProgress, cameraPos, crumblingOverlay);

        int i = (int)blockEntity.getBlockPos().asLong();
        ItemStackRenderState itemStackRenderState = new ItemStackRenderState();
        this.itemModelResolver.updateForTopItem(itemStackRenderState, (ItemStack)blockEntity.getItem(0), ItemDisplayContext.FIXED, blockEntity.getLevel(), (ItemOwner)null, i);
        state.setHeld(blockEntity.getItem(0));
        this.itemStackRenderState = itemStackRenderState;
    }

    @Override
    public void submit(JarBlockEntityRenderState state, PoseStack poseStack, SubmitNodeCollector queue, CameraRenderState cameraState) {
            if (!itemStackRenderState.isEmpty()) {
                poseStack.pushPose();
                poseStack.translate(0.5F, 0.25F, 0.5F);
                poseStack.scale(0.4F, 0.4F, 0.4F);
                itemStackRenderState.submit(poseStack, queue, state.lightCoords, OverlayTexture.NO_OVERLAY, 0);
                poseStack.popPose();
            }
    }
}