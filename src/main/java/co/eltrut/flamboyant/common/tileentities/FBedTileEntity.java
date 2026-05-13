package co.eltrut.flamboyant.common.tileentities;

import co.eltrut.flamboyant.common.color.FDyeColor;
import co.eltrut.flamboyant.common.color.FDyeColors;
import co.eltrut.flamboyant.core.registry.FlamboyantTileEntities;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class FBedTileEntity extends BlockEntity {

    private FDyeColor color;

    public FBedTileEntity(BlockPos worldPosition, BlockState blockState) {
        super(FlamboyantTileEntities.BED.get(), worldPosition, blockState);
    }

    public FBedTileEntity(BlockPos worldPosition, BlockState blockState, FDyeColor colorIn) {
        this(worldPosition, blockState);
        this.setColor(colorIn);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (this.color != null) {
            tag.putString("color", this.color.getSerializedName());
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("color")) {
            this.color = FDyeColor.byTranslationKey(tag.getString("color"), null);
        }
    }

    public FDyeColor getColor() {
        if (this.color == null) {
            ResourceLocation blockKey = ForgeRegistries.BLOCKS.getKey(this.getBlockState().getBlock());
            if (blockKey != null) {
                // Registry name is like "flamboyant:amber_bed"; strip the "_bed" suffix to get the color name
                String path = blockKey.getPath();
                String colorName = path.endsWith("_bed") ? path.substring(0, path.length() - 4) : path;
                this.color = FDyeColor.byTranslationKey(colorName, FDyeColors.AMBER);
            } else {
                this.color = FDyeColors.AMBER;
            }
        }
        return this.color;
    }

    public void setColor(FDyeColor color) {
        this.color = color;
    }
}
