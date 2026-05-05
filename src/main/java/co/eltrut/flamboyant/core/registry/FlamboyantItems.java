package co.eltrut.flamboyant.core.registry;

import co.eltrut.flamboyant.core.Flamboyant;
import co.eltrut.flamboyant.core.registrator.FItemHelper;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class FlamboyantItems {

    public static final DeferredRegister<Item> ITEMS =
        DeferredRegister.create(ForgeRegistries.ITEMS, Flamboyant.MOD_ID);

    private static final FItemHelper HELPER = new FItemHelper(ITEMS);

    public static final List<RegistryObject<Item>> DYES = HELPER.createDyeItems("_dye");
}
