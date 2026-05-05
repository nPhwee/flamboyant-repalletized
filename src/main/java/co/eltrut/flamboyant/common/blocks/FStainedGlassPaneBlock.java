package co.eltrut.flamboyant.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BeaconBeamBlock;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class FStainedGlassPaneBlock extends IronBarsBlock implements BeaconBeamBlock {

    public FStainedGlassPaneBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
            .setValue(NORTH, false).setValue(EAST, false)
            .setValue(SOUTH, false).setValue(WEST, false)
            .setValue(WATERLOGGED, false));
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
