package net.jojosolos.flightconduit.block.entity;

import net.jojosolos.flightconduit.FlightConduit;
import net.jojosolos.flightconduit.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, FlightConduit.MODID);

    public static final RegistryObject<BlockEntityType<FlightConduitBlockEntity>> FLIGHT_CONDUIT_BE =
            BLOCK_ENTITIES.register("flight_conduit_be",
                    () -> BlockEntityType.Builder.of(FlightConduitBlockEntity::new, ModBlocks.FLIGHT_CONDUIT_BLOCK.get()).build(null));


    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
