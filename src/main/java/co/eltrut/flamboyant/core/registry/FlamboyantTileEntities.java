package co.eltrut.flamboyant.core.registry;

import co.eltrut.flamboyant.common.tileentities.FBedTileEntity;
import co.eltrut.flamboyant.core.Flamboyant;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FlamboyantTileEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
        DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Flamboyant.MOD_ID);

    public static final RegistryObject<BlockEntityType<FBedTileEntity>> BED =
        BLOCK_ENTITIES.register("bed", () ->
            BlockEntityType.Builder.of(FBedTileEntity::new, FlamboyantBlocks.toBlockArray(FlamboyantBlocks.BEDS))
                .build(null)
        );
}
