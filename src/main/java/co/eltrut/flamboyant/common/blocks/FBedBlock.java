package co.eltrut.flamboyant.common.blocks;

import co.eltrut.flamboyant.common.color.FDyeColor;
import co.eltrut.flamboyant.core.registry.FlamboyantTileEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class FBedBlock extends BedBlock {

    private final FDyeColor fColor;

    public FBedBlock(FDyeColor pColor, BlockBehaviour.Properties pProperties) {
        
        
        
        super(DyeColor.WHITE, pProperties);
        this.fColor = pColor;
    }

    public FDyeColor getFColor() {
        return this.fColor;
    }

    
    
    

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return FlamboyantTileEntities.BED.get().create(pos, state);
    }

    
    
    

    @Override
    public boolean isBed(BlockState state, BlockGetter level, BlockPos pos, Entity entity) {
        return true;
    }
}
