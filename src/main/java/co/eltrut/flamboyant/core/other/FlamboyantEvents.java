package co.eltrut.flamboyant.core.other;

import co.eltrut.flamboyant.client.renderer.FSheepRenderer;
import co.eltrut.flamboyant.common.blocks.FCarpetBlock;
import co.eltrut.flamboyant.common.color.FDyeColor;
import co.eltrut.flamboyant.common.color.FDyeColors;
import co.eltrut.flamboyant.common.items.FDyeItem;
import co.eltrut.flamboyant.core.Flamboyant;
import co.eltrut.flamboyant.core.registry.FlamboyantBlocks;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.WoolCarpetBlock;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ForgeRegistries;

import java.lang.reflect.Method;

@EventBusSubscriber(modid = Flamboyant.MOD_ID)
public class FlamboyantEvents {

    
    private static void llamaSetSwag(Llama llama, DyeColor color) {
        try {
            Method m = Llama.class.getDeclaredMethod("setSwag", DyeColor.class);
            m.setAccessible(true);
            m.invoke(llama, color);
        } catch (Exception e) {
            throw new RuntimeException("Failed to call Llama#setSwag", e);
        }
    }

    
    
    

    
    @SubscribeEvent
    public static void onPlayerInteractSheep(PlayerInteractEvent.EntityInteract event) {
        if (!(event.getTarget() instanceof Sheep sheep)) return;
        if (event.getHand() != InteractionHand.MAIN_HAND) return;

        ItemStack stack = event.getItemStack();
        if (!(stack.getItem() instanceof FDyeItem dyeItem)) return;

        if (!sheep.isAlive() || sheep.isSheared()) return;

        
        
        event.setCanceled(true);

        if (!event.getLevel().isClientSide()) {
            FDyeColor fColor = dyeItem.getDyeColor();

            
            sheep.getPersistentData().putInt(FSheepRenderer.NBT_KEY, fColor.getId());

            
            
            sheep.setColor(fColor.toVanilla());

            event.getLevel().playSound(null, sheep,
                    SoundEvents.DYE_USE, SoundSource.PLAYERS, 1.0F, 1.0F);

            if (!event.getEntity().getAbilities().instabuild) {
                stack.shrink(1);
            }
        }
    }

    
    
    

    
    @SubscribeEvent
    public static void onSheepDrops(LivingDropsEvent event) {
        if (!(event.getEntity() instanceof Sheep sheep)) return;

        var tag = sheep.getPersistentData();
        if (!tag.contains(FSheepRenderer.NBT_KEY)) return;

        int id = tag.getInt(FSheepRenderer.NBT_KEY);
        if (id < 0 || id >= FDyeColors.COLORS.length) return;

        String colorName = FDyeColors.COLORS[id].getSerializedName();

        FlamboyantBlocks.WOOLS.stream()
            .filter(ro -> {
                var loc = ForgeRegistries.BLOCKS.getKey(ro.get());
                return loc != null && loc.getPath().equals(colorName + "_wool");
            })
            .findFirst()
            .ifPresent(ro -> {
                
                event.getDrops().removeIf(e ->
                    e.getItem().getItem() instanceof BlockItem bi
                        && bi.getBlock().defaultBlockState().is(BlockTags.WOOL));

                int count = 1 + sheep.getRandom().nextInt(3);
                event.getDrops().add(new ItemEntity(
                    sheep.level(),
                    sheep.getX(), sheep.getY(), sheep.getZ(),
                    new ItemStack(ro.get().asItem(), count)));
            });
    }

    
    
    

    
    @SubscribeEvent
    public static void onPlayerInteractLlama(PlayerInteractEvent.EntityInteract event) {
        if (!(event.getTarget() instanceof Llama llama)) return;
        if (event.getHand() != InteractionHand.MAIN_HAND) return;

        ItemStack stack = event.getItemStack();
        if (!(stack.getItem() instanceof BlockItem bi)) return;
        if (!(bi.getBlock() instanceof FCarpetBlock carpet)) return;

        if (!llama.isAlive() || !llama.isTamed()) return;

        
        if (llama.getSwag() != null) return;

        event.setCanceled(true);

        if (!event.getLevel().isClientSide()) {
            
            
            
            DyeColor carpetColor = ((WoolCarpetBlock) carpet).getColor();
            llamaSetSwag(llama, carpetColor);

            event.getLevel().playSound(null, llama,
                    SoundEvents.ARMOR_EQUIP_GENERIC, SoundSource.PLAYERS, 1.0F, 1.0F);

            if (!event.getEntity().getAbilities().instabuild) {
                stack.shrink(1);
            }
        }
    }
}
