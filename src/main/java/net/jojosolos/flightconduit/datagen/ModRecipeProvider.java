package net.jojosolos.flightconduit.datagen;

import net.jojosolos.flightconduit.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;


public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {

    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.FLIGHT_CONDUIT_BLOCK.get())
                .pattern("ZXZ")
                .pattern("XAX")
                .pattern("ZXZ")
                .define('Z', Items.FEATHER)
                .define('X', Items.ECHO_SHARD)
                .define('A', Blocks.LODESTONE)
                .unlockedBy(getHasName(ModBlocks.FLIGHT_CONDUIT_BLOCK.get()), has(ModBlocks.FLIGHT_CONDUIT_BLOCK.get()))
                .save(pWriter);
    }
}
