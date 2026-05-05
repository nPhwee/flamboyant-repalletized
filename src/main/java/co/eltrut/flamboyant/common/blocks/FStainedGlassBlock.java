package co.eltrut.flamboyant.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.AbstractGlassBlock;
import net.minecraft.world.level.block.BeaconBeamBlock;
import net.minecraft.world.level.block.state.BlockState;

public class FStainedGlassBlock extends AbstractGlassBlock implements BeaconBeamBlock {

    public FStainedGlassBlock(Properties properties) {
        super(properties);
    }

    @Override
    public DyeColor getColor() {
        return DyeColor.WHITE;
    }

    
    @Override
    public boolean skipRendering(BlockState state, BlockState adjacentState, net.minecraft.core.Direction direction) {
        return adjacentState.is(this) || super.skipRendering(state, adjacentState, direction);
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {
        return true;
    }
}
