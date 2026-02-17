package dev.hellscaped.sukimod;

import com.mojang.math.OctahedralGroup;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PlushBlock extends HorizontalDirectionalBlock {
    public static final MapCodec<PlushBlock> CODEC = simpleCodec(PlushBlock::new);

    public MapCodec<PlushBlock> codec() {
        return CODEC;
    }

    public PlushBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        return (BlockState)this.defaultBlockState().setValue(FACING, blockPlaceContext.getHorizontalDirection().getOpposite());
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{FACING});
    }

    public VoxelShape makeShape(BlockState state){
        VoxelShape voxelShape;
        Direction direction = state.getValue(FACING);
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.21875, 0.578125, 0.234375, 0.78125, 1.140625, 0.796875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.375, 0.173125, 0.405625, 0.625, 0.61125, 0.593125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.625, 0.171875, 0.40625, 0.78125, 0.609375, 0.59375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.21875, 0.171875, 0.40625, 0.375, 0.609375, 0.59375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.5, -0.109375, 0.265625, 0.65625, 0.296875, 0.421875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.34375, -0.109375, 0.265625, 0.5, 0.296875, 0.421875), BooleanOp.OR);
        switch (direction) {
            case WEST -> voxelShape = Shapes.rotate(shape, OctahedralGroup.BLOCK_ROT_Y_270);
            case EAST -> voxelShape = Shapes.rotate(shape, OctahedralGroup.BLOCK_ROT_Y_90);
            case SOUTH -> voxelShape = Shapes.rotate(shape, OctahedralGroup.BLOCK_ROT_Y_180);
            case NORTH -> voxelShape = shape;
            default -> throw new MatchException(null, null);
        }
        return voxelShape;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return makeShape(state);
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return makeShape(state);
    }
}
