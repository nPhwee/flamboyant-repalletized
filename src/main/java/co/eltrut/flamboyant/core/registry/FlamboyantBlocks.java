package co.eltrut.flamboyant.core.registry;

import co.eltrut.flamboyant.common.blocks.*;
import co.eltrut.flamboyant.core.Flamboyant;
import co.eltrut.flamboyant.core.registrator.FBlockHelper;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class FlamboyantBlocks {

    public static final DeferredRegister<Block> BLOCKS =
        DeferredRegister.create(ForgeRegistries.BLOCKS, Flamboyant.MOD_ID);
    public static final DeferredRegister<Item> BLOCK_ITEMS =
        DeferredRegister.create(ForgeRegistries.ITEMS, Flamboyant.MOD_ID);

    private static final FBlockHelper HELPER = new FBlockHelper(BLOCKS, BLOCK_ITEMS);

    
    private static BlockBehaviour.Properties woolProps() {
        return BlockBehaviour.Properties.of().mapColor(MapColor.WOOL).strength(0.8F).sound(SoundType.WOOL);
    }
    private static BlockBehaviour.Properties carpetProps() {
        return BlockBehaviour.Properties.of().mapColor(MapColor.WOOL).strength(0.1F).sound(SoundType.WOOL);
    }
    private static BlockBehaviour.Properties terracottaProps() {
        return BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).requiresCorrectToolForDrops().strength(1.25F, 4.2F);
    }
    private static BlockBehaviour.Properties glazedTerracottaProps() {
        return BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).requiresCorrectToolForDrops().strength(1.4F);
    }
    private static BlockBehaviour.Properties concreteProps() {
        return BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(1.8F);
    }
    private static BlockBehaviour.Properties stainedGlassProps() {
        return BlockBehaviour.Properties.of().mapColor(MapColor.NONE).strength(0.3F).sound(SoundType.GLASS).noOcclusion();
    }

    public static final List<RegistryObject<Block>> WOOLS = HELPER.createDyeBlocks("_wool", () -> new FWoolBlock(woolProps()));
    public static final List<RegistryObject<Block>> QUILTED_WOOLS = net.minecraftforge.fml.ModList.get().isLoaded("quarkqs")
        ? HELPER.createDyeBlocks("_quilted_wool", () -> new FQuiltedWoolBlock(woolProps()))
        : java.util.Collections.emptyList();
    public static final List<RegistryObject<Block>> QUILTED_WOOL_CARPETS = net.minecraftforge.fml.ModList.get().isLoaded("quarkqs")
        ? HELPER.createDyeBlocks("_quilted_wool_carpet", () -> new FCarpetBlock(carpetProps()))
        : java.util.Collections.emptyList();
    public static final List<RegistryObject<Block>> CARPETS = HELPER.createDyeBlocks("_carpet", () -> new FCarpetBlock(carpetProps()));
    public static final List<RegistryObject<Block>> TERRACOTTAS = HELPER.createDyeBlocks("_terracotta", () -> new Block(terracottaProps()));
    public static final List<RegistryObject<Block>> GLAZED_TERRACOTTAS = HELPER.createDyeBlocks("_glazed_terracotta", () -> new FGlazedTerracottaBlock(glazedTerracottaProps()));
    public static final List<RegistryObject<Block>> CONCRETES = HELPER.createDyeBlocks("_concrete", () -> new Block(concreteProps()));
    public static final List<RegistryObject<Block>> CONCRETE_POWDERS = HELPER.createConcretePowderBlocks(CONCRETES);
    public static final List<RegistryObject<Block>> STAINED_GLASS = HELPER.createDyeBlocks("_stained_glass", () -> new FStainedGlassBlock(stainedGlassProps()));
    public static final List<RegistryObject<Block>> STAINED_GLASS_PANES = HELPER.createDyeBlocks("_stained_glass_pane", () -> new FStainedGlassPaneBlock(stainedGlassProps()));
    public static final List<RegistryObject<Block>> BEDS = HELPER.createBedBlocks();

    public static Block[] toBlockArray(List<RegistryObject<Block>> list) {
        return list.stream().map(RegistryObject::get).toArray(Block[]::new);
    }
}
