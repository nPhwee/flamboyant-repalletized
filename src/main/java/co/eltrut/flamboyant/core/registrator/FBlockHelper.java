package co.eltrut.flamboyant.core.registrator;

import co.eltrut.flamboyant.common.blocks.*;
import co.eltrut.flamboyant.common.color.FDyeColor;
import co.eltrut.flamboyant.common.color.FDyeColors;
import co.eltrut.flamboyant.common.items.FBedItem;
import co.eltrut.flamboyant.core.other.FlamboyantAtlas;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class FBlockHelper {

    private final DeferredRegister<Block> blockRegister;
    private final DeferredRegister<Item> itemRegister;

    public FBlockHelper(DeferredRegister<Block> blockRegister, DeferredRegister<Item> itemRegister) {
        this.blockRegister = blockRegister;
        this.itemRegister = itemRegister;
    }

    public List<RegistryObject<Block>> createDyeBlocks(String suffix, Supplier<Block> blockSupplier) {
        List<RegistryObject<Block>> list = new ArrayList<>();
        for (FDyeColor color : FDyeColors.COLORS) {
            String name = color.getSerializedName() + suffix;
            // Wrap in a fresh lambda per registration so each entry gets its own
            // distinct Supplier instance — required for Forge's intrusive holder
            // tracking to bind a separate holder for every registered block.
            RegistryObject<Block> ro = blockRegister.register(name, () -> blockSupplier.get());
            itemRegister.register(name, () -> new BlockItem(ro.get(), new Item.Properties()));
            list.add(ro);
        }
        return list;
    }

    public List<RegistryObject<Block>> createBedBlocks() {
        List<RegistryObject<Block>> list = new ArrayList<>();
        for (FDyeColor color : FDyeColors.COLORS) {
            String name = color.getSerializedName() + "_bed";
            BlockBehaviour.Properties props = BlockBehaviour.Properties
                .of()
                .mapColor(s -> s.getValue(FBedBlock.PART) == BedPart.FOOT ? color.getMapColor() : MapColor.WOOL)
                .sound(SoundType.WOOD)
                .strength(0.2F)
                .noOcclusion();
            RegistryObject<Block> ro = blockRegister.register(name, () -> new FBedBlock(props));
            itemRegister.register(name, () -> new FBedItem(ro.get(), new Item.Properties().stacksTo(1)));
            FlamboyantAtlas.addBedInfo(color.getTranslationKey());
            list.add(ro);
        }
        return list;
    }

    public List<RegistryObject<Block>> createConcretePowderBlocks(List<RegistryObject<Block>> concreteBlocks) {
        List<RegistryObject<Block>> list = new ArrayList<>();
        for (FDyeColor color : FDyeColors.COLORS) {
            String name = color.getSerializedName() + "_concrete_powder";
            RegistryObject<Block> concreteRef = concreteBlocks.get(color.getId());
            RegistryObject<Block> ro = blockRegister.register(name, () ->
                new FConcretePowderBlock(concreteRef.get(), BlockBehaviour.Properties
                    .of()
                    .mapColor(MapColor.SAND)
                    .strength(0.5F)
                    .sound(SoundType.SAND))
            );
            itemRegister.register(name, () -> new BlockItem(ro.get(), new Item.Properties()));
            list.add(ro);
        }
        return list;
    }
}
