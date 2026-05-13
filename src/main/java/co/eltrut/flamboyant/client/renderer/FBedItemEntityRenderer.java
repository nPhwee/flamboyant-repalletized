package co.eltrut.flamboyant.client.renderer;

import co.eltrut.flamboyant.common.blocks.FBedBlock;
import co.eltrut.flamboyant.common.color.FDyeColor;
import co.eltrut.flamboyant.common.color.FDyeColors;
import co.eltrut.flamboyant.common.tileentities.FBedTileEntity;
import co.eltrut.flamboyant.core.registry.FlamboyantBlocks;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

public class FBedItemEntityRenderer extends BlockEntityWithoutLevelRenderer {

    private final BlockEntityRenderDispatcher dispatcher;

    public FBedItemEntityRenderer(BlockEntityRenderDispatcher pBlockEntityRenderDispatcher, EntityModelSet pEntityModelSet) {
        super(pBlockEntityRenderDispatcher, pEntityModelSet);
        this.dispatcher = pBlockEntityRenderDispatcher;
    }

    @Override
    public void renderByItem(ItemStack stack, net.minecraft.world.item.ItemDisplayContext transformType, PoseStack poseStack, MultiBufferSource source, int packedLight, int packedOverlay) {
        Item item = stack.getItem();
        if (item instanceof BlockItem blockItem) {
            Block block = blockItem.getBlock();
            if (block instanceof FBedBlock bedBlock) {
                ResourceLocation key = ForgeRegistries.BLOCKS.getKey(bedBlock);
                String colorName = (key != null && key.getPath().endsWith("_bed"))
                    ? key.getPath().substring(0, key.getPath().length() - 4)
                    : "";
                FDyeColor color = FDyeColor.byTranslationKey(colorName, FDyeColors.AMBER);
                FBedTileEntity bed = new FBedTileEntity(BlockPos.ZERO, FlamboyantBlocks.BEDS.get(0).get().defaultBlockState());
                bed.setColor(color);
                this.dispatcher.renderItem(bed, poseStack, source, packedLight, packedOverlay);
            }

        }
    }
}
