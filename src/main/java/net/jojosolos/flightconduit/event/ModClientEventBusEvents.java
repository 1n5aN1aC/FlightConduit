package net.jojosolos.flightconduit.event;

import net.jojosolos.flightconduit.FlightConduit;
import net.jojosolos.flightconduit.block.entity.ModBlockEntities;
import net.jojosolos.flightconduit.block.entity.client.ModModelLayers;
import net.jojosolos.flightconduit.block.entity.renderer.FlightConduitBlockEntityRenderer;
import net.jojosolos.flightconduit.particle.ModParticles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EnchantmentTableParticle;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Set;

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

    @SubscribeEvent
    public static void modelEvent(ModelEvent.RegisterAdditional event){
        for(Field f : ModelBakery.class.getDeclaredFields()){
            if(Modifier.isStatic(f.getModifiers()) && f.getType() == Set.class){
                try {
                    f.setAccessible(true);
                    Set<Material> ob = (Set<Material>) f.get(null);
                    ob.add(FlightConduitBlockEntityRenderer.SHELL_TEXTURE);
                    ob.add(FlightConduitBlockEntityRenderer.WIND_TEXTURE);
                    ob.add(FlightConduitBlockEntityRenderer.ACTIVE_SHELL_TEXTURE);
                    ob.add(FlightConduitBlockEntityRenderer.CLOSED_EYE_TEXTURE);
                    ob.add(FlightConduitBlockEntityRenderer.OPEN_EYE_TEXTURE);
                    ob.add(FlightConduitBlockEntityRenderer.VERTICAL_WIND_TEXTURE);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}
