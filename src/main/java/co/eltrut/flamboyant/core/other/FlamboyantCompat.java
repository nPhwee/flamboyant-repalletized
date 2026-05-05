package co.eltrut.flamboyant.core.other;

import co.eltrut.flamboyant.client.renderer.FBedBlockEntityRenderer;
import co.eltrut.flamboyant.client.renderer.FSheepRenderer;
import co.eltrut.flamboyant.core.registry.FlamboyantBlocks;
import co.eltrut.flamboyant.core.registry.FlamboyantTileEntities;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.client.event.EntityRenderersEvent;

public class FlamboyantCompat {

    public static void registerEntityRenderers() {
        BlockEntityRenderers.register(FlamboyantTileEntities.BED.get(), FBedBlockEntityRenderer::new);

        
        
        
        FlamboyantBlocks.STAINED_GLASS.forEach(ro ->
            ItemBlockRenderTypes.setRenderLayer(ro.get(), RenderType.translucent())
        );
        FlamboyantBlocks.STAINED_GLASS_PANES.forEach(ro ->
            ItemBlockRenderTypes.setRenderLayer(ro.get(), RenderType.translucent())
        );
    }

    
    public static void registerSheepRenderer(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityType.SHEEP, FSheepRenderer::new);
    }
}
