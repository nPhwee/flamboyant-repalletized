package co.eltrut.flamboyant.common.blocks;

import net.minecraft.world.level.block.Block;

public class FQuiltedWoolBlock extends Block {

    public FQuiltedWoolBlock(Properties properties) {
        super(properties);
    }

    @Override
    public int getFlammability(net.minecraft.world.level.block.state.BlockState state,
            net.minecraft.world.level.BlockGetter world,
            net.minecraft.core.BlockPos pos,
            net.minecraft.core.Direction face) {
        return 60;
    }

    @Override
    public int getFireSpreadSpeed(net.minecraft.world.level.block.state.BlockState state,
            net.minecraft.world.level.BlockGetter world,
            net.minecraft.core.BlockPos pos,
            net.minecraft.core.Direction face) {
        return 30;
    }
}
