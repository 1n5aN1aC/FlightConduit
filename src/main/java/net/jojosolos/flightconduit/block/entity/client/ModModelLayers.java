package net.jojosolos.flightconduit.block.entity.client;

import net.jojosolos.flightconduit.FlightConduit;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class ModModelLayers {
        public static final ModelLayerLocation FLIGHT_CONDUIT_CAGE = new ModelLayerLocation(
            new ResourceLocation(FlightConduit.MODID, "flight_conduit/cage"), "main");

        public static final ModelLayerLocation FLIGHT_CONDUIT_SHELL = new ModelLayerLocation(
            new ResourceLocation(FlightConduit.MODID, "flight_conduit/shell"), "main");

        public static final ModelLayerLocation FLIGHT_CONDUIT_EYE = new ModelLayerLocation(
            new ResourceLocation(FlightConduit.MODID, "flight_conduit/eye"), "main");

        public static final ModelLayerLocation FLIGHT_CONDUIT_WIND = new ModelLayerLocation(
            new ResourceLocation(FlightConduit.MODID, "flight_conduit/wind"), "main");

}
