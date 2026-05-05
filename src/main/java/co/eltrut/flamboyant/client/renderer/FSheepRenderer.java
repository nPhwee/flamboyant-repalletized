package co.eltrut.flamboyant.client.renderer;

import co.eltrut.flamboyant.common.color.FDyeColors;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.SheepFurModel;
import net.minecraft.client.model.SheepModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Sheep;

public class FSheepRenderer extends MobRenderer<Sheep, SheepModel<Sheep>> {

    
    private static final ResourceLocation SHEEP_TEXTURE =
        new ResourceLocation("textures/entity/sheep/sheep.png");
    private static final ResourceLocation SHEEP_FUR_TEXTURE =
        new ResourceLocation("textures/entity/sheep/sheep_fur.png");

    
    public static final String NBT_KEY = "FlamboyantDyeId";

    public FSheepRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new SheepModel<>(ctx.bakeLayer(ModelLayers.SHEEP)), 0.7F);
        this.addLayer(new FSheepFurLayer(this, ctx));
    }

    @Override
    public ResourceLocation getTextureLocation(Sheep sheep) {
        return SHEEP_TEXTURE;
    }

    
    public static float[] getFurColor(Sheep sheep) {
        CompoundTag tag = sheep.getPersistentData();
        if (tag.contains(NBT_KEY)) {
            int id = tag.getInt(NBT_KEY);
            if (id >= 0 && id < FDyeColors.COLORS.length) {
                return FDyeColors.COLORS[id].getColorComponentValues();
            }
        }
        return Sheep.getColorArray(sheep.getColor());
    }

    
    
    

    
    private static class FSheepFurLayer extends RenderLayer<Sheep, SheepModel<Sheep>> {

        private final SheepFurModel<Sheep> furModel;

        FSheepFurLayer(FSheepRenderer parent, EntityRendererProvider.Context ctx) {
            super(parent);
            this.furModel = new SheepFurModel<>(ctx.bakeLayer(ModelLayers.SHEEP_FUR));
        }

        @Override
        public void render(PoseStack poseStack, MultiBufferSource bufferSource,
                           int packedLight, Sheep sheep,
                           float limbSwing, float limbSwingAmount,
                           float partialTick, float ageInTicks,
                           float netHeadYaw, float headPitch) {

            if (sheep.isSheared()) return;

            float[] color = getFurColor(sheep);

            
            
            coloredCutoutModelCopyLayerRender(
                (EntityModel<Sheep>) getParentModel(),
                (EntityModel<Sheep>) furModel,
                SHEEP_FUR_TEXTURE,
                poseStack,
                bufferSource,
                packedLight,
                sheep,
                limbSwing,
                limbSwingAmount,
                ageInTicks,
                netHeadYaw,
                headPitch,
                partialTick,
                color[0], color[1], color[2]
            );
        }
    }
}
