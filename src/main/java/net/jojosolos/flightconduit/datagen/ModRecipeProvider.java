package net.jojosolos.flightconduit.datagen;

import net.jojosolos.flightconduit.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;


public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {

    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.FLIGHT_CONDUIT_BLOCK.get(), 9)
                .requires(ModBlocks.FLIGHT_CONDUIT_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.FLIGHT_CONDUIT_BLOCK.get()), has(ModBlocks.FLIGHT_CONDUIT_BLOCK.get()))
                .save(pWriter);
    }
}
