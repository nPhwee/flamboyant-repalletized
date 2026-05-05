package co.eltrut.flamboyant.core.datagen;

import co.eltrut.flamboyant.core.registry.FlamboyantBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Consumer;

public class FlamboyantRecipes extends RecipeProvider {

    public FlamboyantRecipes(PackOutput output) {
        super(output);
    }

    @Override
    public void buildRecipes(Consumer<FinishedRecipe> consumer) {
        for (int i = 0; i < FlamboyantBlocks.CONCRETE_POWDERS.size(); i++) {
            final int idx = i;
            SimpleCookingRecipeBuilder.smelting(
                Ingredient.of(FlamboyantBlocks.CONCRETE_POWDERS.get(idx).get()),
                RecipeCategory.BUILDING_BLOCKS,
                FlamboyantBlocks.STAINED_GLASS.get(idx).get(),
                0.1F, 200
            ).unlockedBy("has_concrete_powder",
                has(FlamboyantBlocks.CONCRETE_POWDERS.get(idx).get()))
             .save(consumer);
        }
    }
}
