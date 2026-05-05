package co.eltrut.flamboyant.common.items;

import co.eltrut.flamboyant.client.renderer.FBedItemEntityRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public class FBedItem extends BlockItem {

    public FBedItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    
    
    
    

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private FBedItemEntityRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (renderer == null) {
                    renderer = new FBedItemEntityRenderer(
                        Minecraft.getInstance().getBlockEntityRenderDispatcher(),
                        Minecraft.getInstance().getEntityModels()
                    );
                }
                return renderer;
            }
        });
    }
}
