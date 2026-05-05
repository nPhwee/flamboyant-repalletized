package co.eltrut.flamboyant.core;

import co.eltrut.flamboyant.core.other.FlamboyantCompat;
import co.eltrut.flamboyant.core.registry.FlamboyantBlocks;
import co.eltrut.flamboyant.core.registry.FlamboyantItems;
import co.eltrut.flamboyant.core.registry.FlamboyantTileEntities;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Mod("flamboyant")
@Mod.EventBusSubscriber(modid = "flamboyant", bus = Bus.MOD)
public class Flamboyant {
    public static final String MOD_ID = "flamboyant";
    public static Flamboyant instance;

    public Flamboyant() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, FlamboyantConfig.COMMON_SPEC);

        modEventBus.addListener(this::doCommonStuff);
        modEventBus.addListener(this::doClientStuff);
        modEventBus.addListener(this::buildCreativeTab);
        
        modEventBus.addListener(FlamboyantCompat::registerSheepRenderer);
        instance = this;

        FlamboyantBlocks.BLOCKS.register(modEventBus);
        FlamboyantBlocks.BLOCK_ITEMS.register(modEventBus);
        FlamboyantItems.ITEMS.register(modEventBus);
        FlamboyantTileEntities.BLOCK_ENTITIES.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void doCommonStuff(final FMLCommonSetupEvent event) {
        event.enqueueWork(this::replaceBedPOI);
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        
        
        event.enqueueWork(FlamboyantCompat::registerEntityRenderers);
    }

    private void buildCreativeTab(final BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.COLORED_BLOCKS) {
            FlamboyantBlocks.WOOLS.forEach(ro -> event.accept(ro.get()));
            FlamboyantBlocks.QUILTED_WOOLS.forEach(ro -> event.accept(ro.get()));
            FlamboyantBlocks.QUILTED_WOOL_CARPETS.forEach(ro -> event.accept(ro.get()));
            FlamboyantBlocks.CARPETS.forEach(ro -> event.accept(ro.get()));
            FlamboyantBlocks.TERRACOTTAS.forEach(ro -> event.accept(ro.get()));
            FlamboyantBlocks.GLAZED_TERRACOTTAS.forEach(ro -> event.accept(ro.get()));
            FlamboyantBlocks.CONCRETES.forEach(ro -> event.accept(ro.get()));
            FlamboyantBlocks.CONCRETE_POWDERS.forEach(ro -> event.accept(ro.get()));
            FlamboyantBlocks.STAINED_GLASS.forEach(ro -> event.accept(ro.get()));
            FlamboyantBlocks.STAINED_GLASS_PANES.forEach(ro -> event.accept(ro.get()));
            FlamboyantBlocks.BEDS.forEach(ro -> event.accept(ro.get()));
        }
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            FlamboyantItems.DYES.forEach(ro -> event.accept(ro.get()));
        }
    }

    @SuppressWarnings("unchecked")
    private void replaceBedPOI() {
        PoiType homePoiType = ForgeRegistries.POI_TYPES.getValue(
            net.minecraft.resources.ResourceLocation.tryParse("minecraft:home")
        );
        if (homePoiType == null) return;

        try {
            Field matchingStatesField = null;
            for (String fieldName : new String[]{"f_218857_", "matchingStates"}) {
                try {
                    matchingStatesField = PoiType.class.getDeclaredField(fieldName);
                    break;
                } catch (NoSuchFieldException ignored) {}
            }
            if (matchingStatesField == null) return;
            matchingStatesField.setAccessible(true);

            Set<BlockState> currentStates = (Set<BlockState>) matchingStatesField.get(homePoiType);
            Set<BlockState> newStates = new HashSet<>(currentStates);

            Map<BlockState, PoiType> map = ObfuscationReflectionHelper.getPrivateValue(
                PoiType.class, null, "f_218859_"
            );

            FlamboyantBlocks.BEDS.stream()
                .flatMap(s -> s.get().getStateDefinition().getPossibleStates().stream())
                .filter(s -> s.getValue(BedBlock.PART) == BedPart.HEAD)
                .forEach(s -> {
                    if (map != null) map.put(s, homePoiType);
                    newStates.add(s);
                });

            matchingStatesField.set(homePoiType, newStates);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
