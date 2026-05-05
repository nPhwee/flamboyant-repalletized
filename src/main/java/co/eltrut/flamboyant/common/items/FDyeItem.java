package co.eltrut.flamboyant.common.items;

import co.eltrut.flamboyant.common.color.FDyeColor;
import net.minecraft.world.item.Item;

public class FDyeItem extends Item {

    private final FDyeColor dyeColor;

    public FDyeItem(FDyeColor dyeColor, Properties properties) {
        super(properties);
        this.dyeColor = dyeColor;
    }

    public FDyeColor getDyeColor() {
        return this.dyeColor;
    }
}
