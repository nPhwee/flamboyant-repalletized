package co.eltrut.flamboyant.core.registrator;

import co.eltrut.flamboyant.common.color.FDyeColor;
import co.eltrut.flamboyant.common.color.FDyeColors;
import co.eltrut.flamboyant.common.items.FDyeItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

public class FItemHelper {

    private final DeferredRegister<Item> itemRegister;

    public FItemHelper(DeferredRegister<Item> itemRegister) {
        this.itemRegister = itemRegister;
    }

    public List<RegistryObject<Item>> createDyeItems(String suffix) {
        List<RegistryObject<Item>> list = new ArrayList<>();
        for (FDyeColor color : FDyeColors.COLORS) {
            String name = color.getSerializedName() + suffix;
            
            final FDyeColor captured = color;
            list.add(itemRegister.register(name, () -> new FDyeItem(captured, new Item.Properties())));
        }
        return list;
    }
}
