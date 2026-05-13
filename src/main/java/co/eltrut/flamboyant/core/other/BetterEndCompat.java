package co.eltrut.flamboyant.core.other;

import co.eltrut.flamboyant.common.color.FDyeColor;
import co.eltrut.flamboyant.common.color.FDyeColors;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Registers colored HydraluxPetalBlock variants for BetterEnd under the
 * "betterend" namespace, replacing the broken ColoredMaterial integration
 * that BetterEnd ships (which creates blocks during FMLCommonSetupEvent,
 * after Forge has already frozen the registry).
 *
 * Each block gets its own pre-generated texture (generated via hue-shift of
 * the base hydralux_petal_block texture), so no runtime tinting is needed.
 */
public class BetterEndCompat {

    public static final boolean BETTEREND_LOADED =
        ModList.get().isLoaded("betterend");

    private static final DeferredRegister<Block> BETTEREND_BLOCKS =
        DeferredRegister.create(ForgeRegistries.BLOCKS, "betterend");
    private static final DeferredRegister<Item> BETTEREND_ITEMS =
        DeferredRegister.create(ForgeRegistries.ITEMS, "betterend");

    public static final List<RegistryObject<Block>> HYDRALUX_PETAL_BLOCKS =
        new ArrayList<>();

    static {
        if (BETTEREND_LOADED) {
            registerHydraluxPetalBlocks();
        }
    }

    private static void registerHydraluxPetalBlocks() {
        for (FDyeColor color : FDyeColors.COLORS) {
            String name = color.getSerializedName() + "_hydralux_petal_block";

            RegistryObject<Block> blockRO = BETTEREND_BLOCKS.register(name,
                () -> createHydraluxPetalBlock(color));

            BETTEREND_ITEMS.register(name, () ->
                new net.minecraft.world.item.BlockItem(
                    blockRO.get(),
                    new net.minecraft.world.item.Item.Properties()
                )
            );

            HYDRALUX_PETAL_BLOCKS.add(blockRO);
        }
    }

    private static Block createHydraluxPetalBlock(FDyeColor color) {
        try {
            Class<?> cls = Class.forName(
                "org.betterx.betterend.blocks.HydraluxPetalColoredBlock");
            java.lang.reflect.Constructor<?> ctor =
                cls.getDeclaredConstructor(
                    net.minecraft.world.level.block.state.BlockBehaviour.Properties.class);
            ctor.setAccessible(true);
            net.minecraft.world.level.block.state.BlockBehaviour.Properties props =
                net.minecraft.world.level.block.state.BlockBehaviour.Properties
                    .of()
                    .mapColor(color.getMapColor())
                    .strength(0.2F)
                    .sound(net.minecraft.world.level.block.SoundType.MOSS)
                    .noOcclusion();
            return (Block) ctor.newInstance(props);
        } catch (Exception e) {
            return new Block(
                net.minecraft.world.level.block.state.BlockBehaviour.Properties
                    .of()
                    .mapColor(color.getMapColor())
                    .strength(0.2F)
            );
        }
    }

    /** Call this from the mod constructor to wire up the DeferredRegisters. */
    public static void register(IEventBus modEventBus) {
        if (BETTEREND_LOADED) {
            BETTEREND_BLOCKS.register(modEventBus);
            BETTEREND_ITEMS.register(modEventBus);
        }
    }
}
