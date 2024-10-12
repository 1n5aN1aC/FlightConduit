package net.jojosolos.flightconduit.event;

import net.jojosolos.flightconduit.FlightConduit;
import net.jojosolos.flightconduit.block.entity.ModBlockEntities;
import net.jojosolos.flightconduit.block.entity.client.ModModelLayers;
import net.jojosolos.flightconduit.block.entity.renderer.FlightConduitBlockEntityRenderer;
import net.jojosolos.flightconduit.particle.ModParticles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EnchantmentTableParticle;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FlightConduit.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModClientEventBusEvents {

    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.FLIGHT_CONDUIT_CAGE, FlightConduitBlockEntityRenderer::createCageLayer);
        event.registerLayerDefinition(ModModelLayers.FLIGHT_CONDUIT_WIND, FlightConduitBlockEntityRenderer::createWindLayer);
        event.registerLayerDefinition(ModModelLayers.FLIGHT_CONDUIT_EYE, FlightConduitBlockEntityRenderer::createEyeLayer);
        event.registerLayerDefinition(ModModelLayers.FLIGHT_CONDUIT_SHELL, FlightConduitBlockEntityRenderer::createShellLayer);
    }

    @SubscribeEvent
    public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.FLIGHT_CONDUIT_BE.get(), FlightConduitBlockEntityRenderer::new);
    }

    @SubscribeEvent
    public static void registerParticles(final RegisterParticleProvidersEvent event) {
        Minecraft.getInstance().particleEngine.register(ModParticles.FLIGHT_CONDUIT_PARTICLE.get(),
                EnchantmentTableParticle.NautilusProvider::new);
    }

}
