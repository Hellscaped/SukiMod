package dev.hellscaped.sukimod;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;

public class JarBlock extends BaseEntityBlock {
    protected JarBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(JarBlock::new);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new JarBlockEntity(pos, state);
    }
    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (level.isClientSide()) return InteractionResult.SUCCESS;

        if (!(level.getBlockEntity(pos) instanceof JarBlockEntity blockEntity)) {
            return InteractionResult.PASS;
        }

        if (!player.getItemInHand(hand).isEmpty()) {
            if (blockEntity.getItem(0).isEmpty()) {
                blockEntity.setItem(0, player.getItemInHand(hand).copy());
                player.getItemInHand(hand).setCount(0);
            } else {
                ItemStack handItem = player.getItemInHand(hand).copy();
                player.getItemInHand(hand).setCount(0);
                player.getInventory().placeItemBackInInventory(blockEntity.getItem(0));
                blockEntity.setItem(0, handItem);
            }
        } else {
            if (!blockEntity.getItem(0).isEmpty()) {
                player.getInventory().placeItemBackInInventory(blockEntity.getItem(0));
                blockEntity.removeItem(0,blockEntity.getItem(0).getCount());
            } else {
                return InteractionResult.TRY_WITH_EMPTY_HAND;
            }
        }

        player.displayClientMessage(Component.literal("The jar holds ")
                .append(blockEntity.getItem(0).getItemName()),true);
        blockEntity.markUpdated();
        return InteractionResult.SUCCESS;
    }

    public VoxelShape makeShape(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.25, 0, 0.25, 0.75, 0.75, 0.75), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.375, 0.75, 0.375, 0.625, 0.875, 0.625), BooleanOp.OR);

        return shape;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return makeShape();
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return makeShape();
    }
}

