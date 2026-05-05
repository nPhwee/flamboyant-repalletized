package co.eltrut.flamboyant.common.color;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.MapColor;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class FDyeColor implements StringRepresentable {

    private static final ArrayList<FDyeColor> VALUES = new ArrayList<>();

    private final int id;
    private final String translationKey;
    private final MapColor mapColor;
    private final float[] colorComponentValues;
    private final int fireworkColor;
    private final TagKey<Item> tag;
    private final int textColor;
    private final DyeColor nearestVanilla;

    public FDyeColor(int idIn, String translationKeyIn, int colorValueIn,
                     MapColor mapColorIn, int fireworkColorIn, int textColorIn,
                     DyeColor nearestVanillaIn) {

        this.id = idIn;
        this.translationKey = translationKeyIn;
        this.mapColor = mapColorIn;
        this.textColor = textColorIn;
        this.nearestVanilla = nearestVanillaIn;

        int r = (colorValueIn & 0xFF0000) >> 16;
        int g = (colorValueIn & 0x00FF00) >> 8;
        int b = (colorValueIn & 0x0000FF);

        this.colorComponentValues = new float[]{
                r / 255.0F,
                g / 255.0F,
                b / 255.0F
        };

        this.fireworkColor = fireworkColorIn;
        this.tag = ItemTags.create(new ResourceLocation("forge", "dyes/" + translationKeyIn));

        VALUES.add(this);
    }

    
    
    

    public int getId() { return id; }
    public String getTranslationKey() { return translationKey; }
    public float[] getColorComponentValues() { return colorComponentValues; }
    public MapColor getMapColor() { return mapColor; }
    public int getFireworkColor() { return fireworkColor; }
    public int getTextColor() { return textColor; }
    public TagKey<Item> getTag() { return tag; }

    
    
    
    
    
    

    public DyeColor toVanilla() {
        return nearestVanilla;
    }

    
    
    

    public static FDyeColor byId(int colorId) {
        if (colorId < 0 || colorId >= VALUES.size()) colorId = 0;
        return VALUES.get(colorId);
    }

    public static FDyeColor byTranslationKey(String key, FDyeColor fallback) {
        for (FDyeColor c : VALUES) {
            if (c.translationKey.equals(key)) return c;
        }
        return fallback;
    }

    @Nullable
    public static FDyeColor getColor(ItemStack stack) {
        for (FDyeColor color : VALUES) {
            if (stack.is(color.getTag())) return color;
        }
        return null;
    }

    public static ArrayList<FDyeColor> getValues() {
        return VALUES;
    }

    @Override
    public String getSerializedName() {
        return translationKey;
    }

    @Override
    public String toString() {
        return translationKey;
    }
}
