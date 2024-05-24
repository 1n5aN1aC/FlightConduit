package net.jojosolos.flightconduit.datagen;

import net.jojosolos.flightconduit.FlightConduit;
import net.jojosolos.flightconduit.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProviders extends BlockStateProvider {
    public ModBlockStateProviders(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, FlightConduit.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(ModBlocks.FLIGHT_CONDUIT_BLOCK.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/flight_conduit_block")));
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}
